package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.TopUpRepository
import com.example.model.GameCatalogItem
import com.example.model.IdRequirement
import com.example.model.PackGroup
import com.example.model.PackItem
import com.example.model.PaymentMethod
import com.example.model.PriceFormatter
import com.example.ui.theme.CheetahColors

@Composable
fun StepSectionHeader(
    stepNumber: Int,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(CheetahColors.GoldBrush),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber.toString(),
                color = CheetahColors.GoldInk,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = CheetahColors.TextPrimary
        )
    }
}

@Composable
fun GameChipRow(
    games: List<GameCatalogItem>,
    selectedIndex: Int,
    onSelectGame: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp)
    ) {
        itemsIndexed(games, key = { _, g -> g.id }) { index, game ->
            val selected = index == selectedIndex
            val label = if (game.inStock) {
                game.name
            } else {
                "${game.name} ${stringResource(R.string.game_soon_suffix)}"
            }
            val shape = RoundedCornerShape(99.dp)
            Box(
                modifier = Modifier
                    .defaultMinSize(minHeight = 48.dp)
                    .clip(shape)
                    .background(
                        brush = if (selected) CheetahColors.SelectedChipBrush else SolidColor(CheetahColors.GlassSurface),
                        shape = shape
                    )
                    .border(
                        width = if (selected) 1.5.dp else 1.dp,
                        color = if (selected) CheetahColors.Gold else CheetahColors.GlassLine,
                        shape = shape
                    )
                    .clickable { onSelectGame(index) }
                    .testTag("game_chip_${game.id}")
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (game.inStock || selected) CheetahColors.TextPrimary else CheetahColors.TextMuted,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun NoteCallout(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(CheetahColors.GlassSurface)
            .drawBehind {
                drawLine(
                    color = CheetahColors.Gold,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 3.dp.toPx()
                )
            }
            .padding(start = 16.dp, top = 12.dp, end = 14.dp, bottom = 12.dp)
    ) {
        content()
    }
}

@Composable
fun PromoNoteBanner(
    promoPercent: Int,
    modifier: Modifier = Modifier
) {
    NoteCallout(
        modifier = modifier,
        content = {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = CheetahColors.TextPrimary, fontWeight = FontWeight.Bold)) {
                        append(stringResource(R.string.promo_note_prefix, promoPercent))
                    }
                    withStyle(SpanStyle(color = CheetahColors.TextMuted)) {
                        append(stringResource(R.string.promo_note_body))
                    }
                },
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    )
}

@Composable
fun ComingSoonNoteBanner(
    onOpenWhatsApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    NoteCallout(
        modifier = modifier
            .clickable(onClick = onOpenWhatsApp)
            .testTag("coming_soon_whatsapp_note"),
        content = {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = CheetahColors.TextPrimary, fontWeight = FontWeight.Bold)) {
                        append(stringResource(R.string.coming_soon_prefix))
                    }
                    withStyle(SpanStyle(color = CheetahColors.TextMuted)) {
                        append(stringResource(R.string.coming_soon_body))
                    }
                    withStyle(
                        SpanStyle(
                            color = CheetahColors.Gold,
                            fontWeight = FontWeight.SemiBold,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(stringResource(R.string.coming_soon_whatsapp))
                    }
                    withStyle(SpanStyle(color = CheetahColors.TextMuted)) {
                        append(stringResource(R.string.coming_soon_tail))
                    }
                },
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    )
}

@Composable
fun PackGroupSection(
    group: PackGroup,
    inStock: Boolean,
    selectedPackId: String?,
    promoPercent: Int,
    effectivePrice: (PackItem) -> Int,
    onSelectPack: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = group.title,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = CheetahColors.TextMuted,
            modifier = Modifier.padding(top = 18.dp, bottom = 8.dp)
        )

        if (group.packs.size == 1) {
            val pack = group.packs.first()
            PackCard(
                pack = pack,
                inStock = inStock,
                selected = pack.id == selectedPackId,
                effectivePrice = effectivePrice(pack),
                promoPercent = promoPercent,
                onClick = { onSelectPack(pack.id) },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            val rows = remember(group.packs) { group.packs.chunked(2) }
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                rows.forEach { pair ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        pair.forEach { pack ->
                            PackCard(
                                pack = pack,
                                inStock = inStock,
                                selected = pack.id == selectedPackId,
                                effectivePrice = effectivePrice(pack),
                                promoPercent = promoPercent,
                                onClick = { onSelectPack(pack.id) },
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            )
                        }
                        if (pair.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PackCard(
    pack: PackItem,
    inStock: Boolean,
    selected: Boolean,
    effectivePrice: Int,
    promoPercent: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDiscounted = effectivePrice != pack.basePriceHtg
    val badgeText = if (isDiscounted) "-$promoPercent%" else pack.badgeLabel
    val shape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .defaultMinSize(minHeight = 92.dp)
            .alpha(if (inStock) 1f else 0.45f)
            .clip(shape)
            .background(
                brush = if (selected) CheetahColors.SelectedPackBrush else SolidColor(CheetahColors.GlassSurface),
                shape = shape
            )
            .border(
                width = if (selected) 1.5.dp else 1.dp,
                color = if (selected) CheetahColors.Gold else CheetahColors.GlassLine,
                shape = shape
            )
            .clickable(enabled = inStock, onClick = onClick)
            .testTag("pack_card_${pack.id}")
            .padding(14.dp)
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(CheetahColors.GoldBrush),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.cd_selected_check),
                    tint = CheetahColors.GoldInk,
                    modifier = Modifier.size(13.dp)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                if (badgeText != null) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .clip(RoundedCornerShape(99.dp))
                            .background(CheetahColors.GoldBrush)
                            .padding(horizontal = 9.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = badgeText,
                            style = MaterialTheme.typography.labelSmall,
                            color = CheetahColors.GoldInk
                        )
                    }
                }
                Text(
                    text = pack.displayAmount,
                    style = MaterialTheme.typography.titleLarge,
                    color = CheetahColors.TextPrimary,
                    modifier = Modifier.padding(end = if (selected) 24.dp else 0.dp)
                )
                Text(
                    text = pack.unit,
                    style = MaterialTheme.typography.bodySmall,
                    color = CheetahColors.TextMuted
                )
            }

            Column(modifier = Modifier.padding(top = 10.dp)) {
                if (isDiscounted) {
                    Text(
                        text = PriceFormatter.formatHtg(pack.basePriceHtg),
                        fontSize = 12.sp,
                        color = CheetahColors.TextMuted,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
                Text(
                    text = PriceFormatter.formatHtg(effectivePrice),
                    style = TextStyle(
                        brush = CheetahColors.GoldBrush,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun PlayerIdSection(
    gameName: String,
    idRequirement: IdRequirement,
    playerId: String,
    zoneId: String,
    showIdError: Boolean,
    onPlayerIdChange: (String) -> Unit,
    onZoneIdChange: (String) -> Unit,
    onFocusLost: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (idRequirement == IdRequirement.NONE) return

    val focusManager = LocalFocusManager.current
    val isDual = idRequirement == IdRequirement.PLAYER_AND_ZONE

    Column(modifier = modifier.fillMaxWidth()) {
        StepSectionHeader(
            stepNumber = 2,
            title = stringResource(R.string.step_two_title_format, gameName)
        )
        Text(
            text = stringResource(if (isDual) R.string.id_hint_dual else R.string.id_hint_single),
            style = MaterialTheme.typography.bodySmall,
            color = CheetahColors.TextMuted,
            modifier = Modifier.padding(start = 36.dp, top = 4.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        NumericGlassField(
            value = playerId,
            onValueChange = onPlayerIdChange,
            placeholder = stringResource(if (isDual) R.string.id_placeholder_dual else R.string.id_placeholder_single),
            isInvalid = showIdError,
            imeAction = if (isDual) ImeAction.Next else ImeAction.Done,
            onDone = { focusManager.clearFocus() },
            onFocusLost = onFocusLost,
            testTag = "player_id_input"
        )

        if (isDual) {
            Spacer(modifier = Modifier.height(10.dp))
            NumericGlassField(
                value = zoneId,
                onValueChange = onZoneIdChange,
                placeholder = stringResource(R.string.zone_id_placeholder),
                isInvalid = showIdError,
                imeAction = ImeAction.Done,
                onDone = { focusManager.clearFocus() },
                onFocusLost = onFocusLost,
                testTag = "zone_id_input"
            )
        }

        AnimatedVisibility(visible = showIdError) {
            Text(
                text = stringResource(if (isDual) R.string.id_error_dual else R.string.id_error_single),
                style = MaterialTheme.typography.bodySmall,
                color = CheetahColors.Danger,
                modifier = Modifier.padding(start = 2.dp, top = 6.dp)
            )
        }
    }
}

@Composable
private fun NumericGlassField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isInvalid: Boolean,
    imeAction: ImeAction,
    onDone: () -> Unit,
    onFocusLost: () -> Unit,
    testTag: String
) {
    var isFocused by remember { mutableStateOf(false) }
    var wasFocused by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(16.dp)

    val borderColor = when {
        isInvalid -> CheetahColors.Danger
        isFocused -> CheetahColors.Gold
        else -> CheetahColors.GlassLine
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 54.dp)
            .clip(shape)
            .background(CheetahColors.GlassSurface)
            .border(1.dp, borderColor, shape)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = CheetahColors.TextSubtle,
                fontSize = 16.sp
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = CheetahColors.TextPrimary,
                fontSize = 17.sp,
                letterSpacing = 0.5.sp
            ),
            cursorBrush = SolidColor(CheetahColors.Gold),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(onDone = { onDone() }),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    if (focusState.isFocused) {
                        wasFocused = true
                    } else if (wasFocused) {
                        onFocusLost()
                    }
                }
        )
    }
}

@Composable
fun PaymentSection(
    stepNumber: Int,
    paymentMethods: List<PaymentMethod>,
    selectedPaymentId: String,
    copiedPaymentId: String?,
    onSelectPayment: (String) -> Unit,
    onCopyNumber: (PaymentMethod) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        StepSectionHeader(
            stepNumber = stepNumber,
            title = stringResource(R.string.step_three_title)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            paymentMethods.forEach { method ->
                val selected = method.id == selectedPaymentId
                val copied = method.id == copiedPaymentId
                val shape = RoundedCornerShape(16.dp)

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(shape)
                        .background(
                            brush = if (selected) CheetahColors.SelectedChipBrush else SolidColor(CheetahColors.GlassSurface),
                            shape = shape
                        )
                        .border(
                            width = if (selected) 1.5.dp else 1.dp,
                            color = if (selected) CheetahColors.Gold else CheetahColors.GlassLine,
                            shape = shape
                        )
                        .clickable { onSelectPayment(method.id) }
                        .testTag("payment_card_${method.id}")
                        .padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = method.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = CheetahColors.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = method.displayNumber,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CheetahColors.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = CheetahColors.TextMuted)) {
                                    append(stringResource(R.string.payment_holder_prefix))
                                }
                                withStyle(SpanStyle(color = CheetahColors.Gold, fontWeight = FontWeight.Bold)) {
                                    append(method.holderName)
                                }
                            },
                            fontSize = 12.5.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .minimumInteractiveComponentSize()
                            .clip(RoundedCornerShape(8.dp))
                            .background(CheetahColors.CopyButtonBg)
                            .border(1.dp, CheetahColors.GlassLine, RoundedCornerShape(8.dp))
                            .clickable { onCopyNumber(method) }
                            .testTag("copy_button_${method.id}")
                            .padding(horizontal = 10.dp, vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(if (copied) R.string.copy_number_done else R.string.copy_number_label),
                            style = MaterialTheme.typography.labelMedium,
                            color = CheetahColors.Gold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        NoteCallout(
            content = {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = CheetahColors.TextMuted)) {
                            append(stringResource(R.string.payment_note_part1))
                        }
                        withStyle(SpanStyle(color = CheetahColors.TextPrimary, fontWeight = FontWeight.Bold)) {
                            append(TopUpRepository.ACCOUNT_HOLDER)
                        }
                        withStyle(SpanStyle(color = CheetahColors.TextMuted)) {
                            append(stringResource(R.string.payment_note_part2))
                        }
                    },
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        )
    }
}

@Composable
fun ContactAndFooter(
    onOpenWhatsApp: () -> Unit,
    onOpenEmail: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(color = CheetahColors.GlassLine, thickness = 1.dp)
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.contact_heading),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = CheetahColors.TextPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onOpenWhatsApp)
                .testTag("contact_whatsapp_link")
                .padding(vertical = 8.dp, horizontal = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.contact_whatsapp_label),
                style = MaterialTheme.typography.bodyMedium,
                color = CheetahColors.TextMuted
            )
            Text(
                text = stringResource(R.string.contact_whatsapp_display),
                style = MaterialTheme.typography.bodyMedium,
                color = CheetahColors.Gold,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onOpenEmail)
                .testTag("contact_email_link")
                .padding(vertical = 8.dp, horizontal = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.contact_email_label),
                style = MaterialTheme.typography.bodyMedium,
                color = CheetahColors.TextMuted
            )
            Text(
                text = stringResource(R.string.contact_email_address),
                style = MaterialTheme.typography.bodyMedium,
                color = CheetahColors.Gold,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = stringResource(R.string.footer_copyright),
            fontSize = 12.sp,
            color = CheetahColors.TextSubtle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
    }
}

@Composable
fun StickyOrderBar(
    selectedPackName: String?,
    formattedPrice: String?,
    canOrder: Boolean,
    onOrderClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(CheetahColors.BottomBarBg)
            .drawBehind {
                drawLine(
                    color = CheetahColors.GlassLine,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 520.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = selectedPackName ?: stringResource(R.string.no_pack_selected),
                    fontSize = 12.sp,
                    color = CheetahColors.TextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = formattedPrice ?: stringResource(R.string.empty_price_placeholder),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = CheetahColors.TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            val buttonShape = RoundedCornerShape(12.dp)
            Row(
                modifier = Modifier
                    .defaultMinSize(minHeight = 48.dp)
                    .clip(buttonShape)
                    .background(
                        brush = if (canOrder) CheetahColors.OrderButtonBrush else SolidColor(CheetahColors.GlassSurfaceStrong),
                        shape = buttonShape
                    )
                    .clickable(enabled = canOrder, onClick = onOrderClick)
                    .testTag("order_button")
                    .padding(horizontal = 18.dp, vertical = 13.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = stringResource(R.string.cd_whatsapp_icon),
                    tint = if (canOrder) CheetahColors.WhatsAppInk else CheetahColors.TextSubtle,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = stringResource(R.string.order_button_label),
                    style = MaterialTheme.typography.labelLarge,
                    color = if (canOrder) CheetahColors.WhatsAppInk else CheetahColors.TextSubtle
                )
            }
        }
    }
}
