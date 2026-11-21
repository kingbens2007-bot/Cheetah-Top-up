package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

val CheetahShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun CheetahXpressTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CheetahDarkColorScheme,
        typography = CheetahTypography,
        shapes = CheetahShapes,
        content = content
    )
}
