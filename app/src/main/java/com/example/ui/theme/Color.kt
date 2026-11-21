package com.example.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object CheetahColors {
    val BgMidnight = Color(0xFF0A1030)
    val LogoInnerBg = Color(0xFF0D1538)
    val GlassSurface = Color(0x0FFFFFFF) // rgba(255, 255, 255, 0.06)
    val GlassSurfaceStrong = Color(0x1CFFFFFF) // rgba(255, 255, 255, 0.11)
    val GlassLine = Color(0x21FFFFFF) // rgba(255, 255, 255, 0.13)
    val CopyButtonBg = Color(0x2E000000) // rgba(0, 0, 0, 0.18)
    val BottomBarBg = Color(0xEB0A1030)

    val TextPrimary = Color(0xFFF4F6FB)
    val TextMuted = Color(0xFFA9B2CC)
    val TextSubtle = Color(0xFF7480A0)

    val Gold = Color(0xFFFFC94A)
    val GoldLight = Color(0xFFFFE07A)
    val GoldMid = Color(0xFFFFB23E)
    val GoldDeep = Color(0xFFFF8A3D)
    val GoldInk = Color(0xFF231400)

    val BrandBlue = Color(0xFF3B82F6)
    val BrandViolet = Color(0xFF8B5CF6)
    val BrandPink = Color(0xFFEC4899)
    val BrandAmber = Color(0xFFFFA032)

    val WhatsAppGreen = Color(0xFF25D366)
    val WhatsAppGreenBright = Color(0xFF2BDE73)
    val WhatsAppTeal = Color(0xFF12B886)
    val WhatsAppInk = Color(0xFF03200F)

    val Danger = Color(0xFFFF8080)

    val GoldBrush = Brush.linearGradient(
        colors = listOf(GoldLight, GoldMid, GoldDeep)
    )

    val BrandBrush = Brush.linearGradient(
        colors = listOf(BrandBlue, BrandViolet, BrandPink)
    )

    val SelectedPackBrush = Brush.linearGradient(
        colors = listOf(
            BrandViolet.copy(alpha = 0.35f),
            BrandPink.copy(alpha = 0.22f)
        )
    )

    val SelectedChipBrush = Brush.linearGradient(
        colors = listOf(
            BrandBlue.copy(alpha = 0.30f),
            BrandViolet.copy(alpha = 0.28f)
        )
    )

    val OrderButtonBrush = Brush.linearGradient(
        colors = listOf(WhatsAppGreenBright, WhatsAppTeal)
    )
}

val CheetahDarkColorScheme = darkColorScheme(
    primary = CheetahColors.Gold,
    onPrimary = CheetahColors.GoldInk,
    primaryContainer = Color(0xFF3B2B00),
    onPrimaryContainer = CheetahColors.GoldLight,
    secondary = CheetahColors.BrandViolet,
    onSecondary = CheetahColors.TextPrimary,
    secondaryContainer = Color(0xFF271E54),
    onSecondaryContainer = CheetahColors.TextPrimary,
    tertiary = CheetahColors.WhatsAppGreen,
    onTertiary = CheetahColors.WhatsAppInk,
    background = CheetahColors.BgMidnight,
    onBackground = CheetahColors.TextPrimary,
    surface = CheetahColors.BgMidnight,
    onSurface = CheetahColors.TextPrimary,
    surfaceVariant = Color(0xFF141C47),
    onSurfaceVariant = CheetahColors.TextMuted,
    outline = CheetahColors.GlassLine,
    error = CheetahColors.Danger,
    onError = Color(0xFF3B0808)
)
