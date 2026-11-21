package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.TopUpRepository
import com.example.model.GameCatalogItem
import com.example.model.IdRequirement
import com.example.model.PackItem
import com.example.model.PaymentMethod
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TopUpUiState(
    val games: List<GameCatalogItem> = TopUpRepository.games,
    val selectedGameIndex: Int = 0,
    val selectedPackId: String? = null,
    val playerId: String = "",
    val zoneId: String = "",
    val showIdError: Boolean = false,
    val paymentMethods: List<PaymentMethod> = TopUpRepository.paymentMethods,
    val selectedPaymentId: String = TopUpRepository.paymentMethods.first().id,
    val copiedPaymentId: String? = null,
    val isPromoActive: Boolean = TopUpRepository.isPromoActive(),
    val promoPercent: Int = TopUpRepository.promoConfig.discountPercent
) {
    val selectedGame: GameCatalogItem
        get() = games.getOrElse(selectedGameIndex) { games.first() }

    val selectedPack: PackItem?
        get() = selectedPackId?.let { id ->
            selectedGame.groups.asSequence()
                .flatMap { it.packs.asSequence() }
                .firstOrNull { it.id == id }
        }

    val selectedPayment: PaymentMethod
        get() = paymentMethods.firstOrNull { it.id == selectedPaymentId } ?: paymentMethods.first()

    val isIdsValid: Boolean
        get() = when (selectedGame.idRequirement) {
            IdRequirement.NONE -> true
            IdRequirement.PLAYER_ONLY -> playerId.length >= 6
            IdRequirement.PLAYER_AND_ZONE -> playerId.length >= 6 && zoneId.length >= 3
        }

    val canOrder: Boolean
        get() = selectedPack != null && selectedGame.inStock && isIdsValid

    fun effectivePrice(pack: PackItem): Int =
        TopUpRepository.promoConfig.effectivePrice(pack, isPromoActive)
}

class TopUpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TopUpUiState())
    val uiState: StateFlow<TopUpUiState> = _uiState.asStateFlow()

    private var copyFeedbackJob: Job? = null

    fun selectGame(index: Int) {
        if (index !in _uiState.value.games.indices || index == _uiState.value.selectedGameIndex) {
            return
        }
        _uiState.update {
            it.copy(
                selectedGameIndex = index,
                selectedPackId = null,
                playerId = "",
                zoneId = "",
                showIdError = false
            )
        }
    }

    fun selectPack(packId: String) {
        val state = _uiState.value
        if (!state.selectedGame.inStock) return
        _uiState.update { it.copy(selectedPackId = packId) }
    }

    fun onPlayerIdChanged(raw: String) {
        val digitsOnly = raw.filter { it.isDigit() }.take(14)
        _uiState.update { current ->
            val updated = current.copy(playerId = digitsOnly)
            updated.copy(showIdError = if (updated.isIdsValid) false else current.showIdError)
        }
    }

    fun onZoneIdChanged(raw: String) {
        val digitsOnly = raw.filter { it.isDigit() }.take(6)
        _uiState.update { current ->
            val updated = current.copy(zoneId = digitsOnly)
            updated.copy(showIdError = if (updated.isIdsValid) false else current.showIdError)
        }
    }

    fun onIdFieldFocusLost() {
        _uiState.update { current ->
            val hasAnyInput = current.playerId.isNotEmpty() || current.zoneId.isNotEmpty()
            current.copy(showIdError = hasAnyInput && !current.isIdsValid)
        }
    }

    fun selectPaymentMethod(paymentId: String) {
        _uiState.update { it.copy(selectedPaymentId = paymentId) }
    }

    fun markNumberCopied(paymentId: String) {
        copyFeedbackJob?.cancel()
        _uiState.update { it.copy(copiedPaymentId = paymentId) }
        copyFeedbackJob = viewModelScope.launch {
            delay(1500L)
            _uiState.update { current ->
                if (current.copiedPaymentId == paymentId) current.copy(copiedPaymentId = null) else current
            }
        }
    }

    fun prepareOrderWhatsAppUri(): String? {
        val state = _uiState.value
        val pack = state.selectedPack ?: return null
        if (!state.selectedGame.inStock) return null
        if (!state.isIdsValid) {
            _uiState.update { it.copy(showIdError = true) }
            return null
        }
        val effectivePrice = state.effectivePrice(pack)
        return TopUpRepository.buildWhatsAppOrderUri(
            game = state.selectedGame,
            pack = pack,
            effectivePrice = effectivePrice,
            isDiscounted = effectivePrice != pack.basePriceHtg,
            playerId = state.playerId,
            zoneId = state.zoneId,
            paymentMethod = state.selectedPayment
        )
    }
}
