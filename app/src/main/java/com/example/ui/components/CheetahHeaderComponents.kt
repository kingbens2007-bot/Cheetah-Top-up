package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CheetahColors
import kotlin.math.max

@Composable
fun AmbientGradientBackground(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(color = CheetahColors.BgMidnight)

        val minDim = max(size.width, size.height)

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(CheetahColors.BrandViolet.copy(alpha = 0.40f), Color.Transparent),
                center = Offset(0f, 0f),
                radius = minDim * 0.55f
            ),
            radius = minDim * 0.55f,
            center = Offset(0f, 0f)
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(CheetahColors.BrandPink.copy(alpha = 0.24f), Color.Transparent),
                center = Offset(size.width, size.height * 0.28f),
                radius = minDim * 0.48f
            ),
            radius = minDim * 0.48f,
            center = Offset(size.width, size.height * 0.28f)
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(CheetahColors.BrandBlue.copy(alpha = 0.28f), Color.Transparent),
                center = Offset(0f, size.height * 0.78f),
                radius = minDim * 0.52f
            ),
            radius = minDim * 0.52f,
            center = Offset(0f, size.height * 0.78f)
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(CheetahColors.BrandAmber.copy(alpha = 0.22f), Color.Transparent),
                center = Offset(size.width, size.height),
                radius = minDim * 0.48f
            ),
            radius = minDim * 0.48f,
            center = Offset(size.width, size.height)
        )
    }
}

@Composable
fun HeroBannerAndBrand(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val bannerShape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 16.dp, shape = bannerShape)
                .clip(bannerShape)
                .background(Color(0xFF04091D))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.statusBarsPadding())
                Image(
                    painter = painterResource(id = R.drawable.banner_hero),
                    contentDescription = stringResource(R.string.cd_banner),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 34.dp)
                )
            }
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            0.0f to Color.Transparent,
                            0.60f to Color.Transparent,
                            1.0f to CheetahColors.BgMidnight.copy(alpha = 0.70f)
                        )
                    )
            )
        }

        // Overlapping Gold-Bordered Logo Badge
        Box(
            modifier = Modifier
                .offset(y = (-52).dp)
                .size(104.dp)
                .shadow(elevation = 14.dp, shape = RoundedCornerShape(30.dp))
                .border(
                    width = 6.dp,
                    color = CheetahColors.BgMidnight.copy(alpha = 0.65f),
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(4.dp)
                .background(brush = CheetahColors.GoldBrush, shape = RoundedCornerShape(26.dp))
                .padding(3.dp)
                .clip(RoundedCornerShape(23.dp))
                .background(CheetahColors.LogoInnerBg),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.cheetah_logo),
                contentDescription = stringResource(R.string.cd_logo),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            )
        }

        Column(
            modifier = Modifier
                .offset(y = (-40).dp)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = CheetahColors.TextPrimary)) {
                        append(stringResource(R.string.brand_prefix))
                    }
                    withStyle(SpanStyle(brush = CheetahColors.GoldBrush)) {
                        append(stringResource(R.string.brand_suffix))
                    }
                },
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.tagline),
                style = MaterialTheme.typography.bodyMedium,
                color = CheetahColors.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FactsStrip(
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(CheetahColors.GlassSurface)
            .border(1.dp, CheetahColors.GlassLine, shape)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FactItem(
            title = stringResource(R.string.fact_delivery_title),
            subtitle = stringResource(R.string.fact_delivery_sub),
            modifier = Modifier.weight(1f)
        )
        FactItem(
            title = stringResource(R.string.fact_payment_title),
            subtitle = stringResource(R.string.fact_payment_sub),
            modifier = Modifier.weight(1f)
        )
        FactItem(
            title = stringResource(R.string.fact_support_title),
            subtitle = stringResource(R.string.fact_support_sub),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FactItem(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = CheetahColors.TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Text(
            text = subtitle,
            color = CheetahColors.TextMuted,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}
