package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.R
import com.example.data.TopUpRepository
import com.example.model.IdRequirement
import com.example.model.PriceFormatter
import com.example.ui.components.AmbientGradientBackground
import com.example.ui.components.ComingSoonNoteBanner
import com.example.ui.components.ContactAndFooter
import com.example.ui.components.FactsStrip
import com.example.ui.components.GameChipRow
import com.example.ui.components.HeroBannerAndBrand
import com.example.ui.components.PackGroupSection
import com.example.ui.components.PaymentSection
import com.example.ui.components.PlayerIdSection
import com.example.ui.components.PromoNoteBanner
import com.example.ui.components.StepSectionHeader
import com.example.ui.components.StickyOrderBar
import kotlinx.coroutines.launch

@Composable
fun TopUpScreen(
    viewModel: TopUpViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val whatsappErrorMsg = stringResource(R.string.whatsapp_open_error)
    val emailErrorMsg = stringResource(R.string.email_open_error)

    val selectedGame = uiState.selectedGame
    val selectedPack = uiState.selectedPack
    val selectedPackPrice = selectedPack?.let { uiState.effectivePrice(it) }

    fun openExternalUrl(url: String, errorMessage: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        } catch (_: Exception) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(errorMessage)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AmbientGradientBackground()

        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = {
                StickyOrderBar(
                    selectedPackName = selectedPack?.orderPackName,
                    formattedPrice = selectedPackPrice?.let { PriceFormatter.formatHtg(it) },
                    canOrder = uiState.canOrder,
                    onOrderClick = {
                        viewModel.prepareOrderWhatsAppUri()?.let { uri ->
                            openExternalUrl(uri, whatsappErrorMsg)
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .imePadding()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 520.dp)
                ) {
                    HeroBannerAndBrand()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        FactsStrip()

                        Spacer(modifier = Modifier.height(28.dp))

                        // Step 1: Game & Pack
                        StepSectionHeader(
                            stepNumber = 1,
                            title = stringResource(R.string.step_one_title)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        GameChipRow(
                            games = uiState.games,
                            selectedIndex = uiState.selectedGameIndex,
                            onSelectGame = viewModel::selectGame
                        )

                        if (uiState.isPromoActive) {
                            Spacer(modifier = Modifier.height(12.dp))
                            PromoNoteBanner(promoPercent = uiState.promoPercent)
                        }

                        if (!selectedGame.inStock) {
                            Spacer(modifier = Modifier.height(12.dp))
                            ComingSoonNoteBanner(
                                onOpenWhatsApp = {
                                    openExternalUrl(TopUpRepository.DIRECT_WHATSAPP_URL, whatsappErrorMsg)
                                }
                            )
                        }

                        selectedGame.groups.forEach { group ->
                            PackGroupSection(
                                group = group,
                                inStock = selectedGame.inStock,
                                selectedPackId = uiState.selectedPackId,
                                promoPercent = uiState.promoPercent,
                                effectivePrice = { uiState.effectivePrice(it) },
                                onSelectPack = viewModel::selectPack
                            )
                        }

                        // Step 2: Player ID (if required for the game)
                        if (selectedGame.idRequirement != IdRequirement.NONE) {
                            Spacer(modifier = Modifier.height(28.dp))
                            PlayerIdSection(
                                gameName = selectedGame.name,
                                idRequirement = selectedGame.idRequirement,
                                playerId = uiState.playerId,
                                zoneId = uiState.zoneId,
                                showIdError = uiState.showIdError,
                                onPlayerIdChange = viewModel::onPlayerIdChanged,
                                onZoneIdChange = viewModel::onZoneIdChanged,
                                onFocusLost = viewModel::onIdFieldFocusLost
                            )
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // Step 3: Payment Methods
                        PaymentSection(
                            stepNumber = if (selectedGame.idRequirement == IdRequirement.NONE) 2 else 3,
                            paymentMethods = uiState.paymentMethods,
                            selectedPaymentId = uiState.selectedPaymentId,
                            copiedPaymentId = uiState.copiedPaymentId,
                            onSelectPayment = viewModel::selectPaymentMethod,
                            onCopyNumber = { method ->
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                clipboard.setPrimaryClip(ClipData.newPlainText(method.name, method.rawCopyNumber))
                                viewModel.markNumberCopied(method.id)
                            }
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        ContactAndFooter(
                            onOpenWhatsApp = {
                                openExternalUrl(TopUpRepository.DIRECT_WHATSAPP_URL, whatsappErrorMsg)
                            },
                            onOpenEmail = {
                                openExternalUrl("mailto:${TopUpRepository.CONTACT_EMAIL}", emailErrorMsg)
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
