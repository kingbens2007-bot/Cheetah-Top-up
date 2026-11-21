package com.example.model

import java.time.LocalDate
import kotlin.math.roundToInt

enum class IdRequirement {
    NONE,
    PLAYER_ONLY,
    PLAYER_AND_ZONE
}

data class PackItem(
    val id: String,
    val numericAmount: Int?,
    val labelAmount: String,
    val unit: String,
    val basePriceHtg: Int,
    val promoEligible: Boolean = true,
    val badgeLabel: String? = null
) {
    val displayAmount: String
        get() = if (numericAmount != null) PriceFormatter.formatNumber(numericAmount) else labelAmount

    val orderPackName: String
        get() = when {
            numericAmount != null -> "${PriceFormatter.formatNumber(numericAmount)} $unit"
            unit == "Abònman" -> "Abònman ${labelAmount.lowercase()}"
            else -> labelAmount
        }
}

data class PackGroup(
    val title: String,
    val packs: List<PackItem>
)

data class GameCatalogItem(
    val id: String,
    val name: String,
    val inStock: Boolean,
    val idRequirement: IdRequirement,
    val groups: List<PackGroup>
)

data class PaymentMethod(
    val id: String,
    val name: String,
    val displayNumber: String,
    val rawCopyNumber: String,
    val holderName: String
)

data class PromoConfig(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val discountPercent: Int,
    val force: Boolean? = null
) {
    fun effectivePrice(pack: PackItem, isPromoOn: Boolean): Int {
        if (!isPromoOn || !pack.promoEligible) return pack.basePriceHtg
        return (pack.basePriceHtg * (100 - discountPercent) / 100.0).roundToInt()
    }
}

object PriceFormatter {
    fun formatNumber(value: Int): String {
        val raw = value.toString()
        val sb = StringBuilder()
        var count = 0
        for (i in raw.length - 1 downTo 0) {
            sb.append(raw[i])
            count++
            if (count % 3 == 0 && i != 0) {
                sb.append(' ')
            }
        }
        return sb.reverse().toString()
    }

    fun formatHtg(value: Int): String = "${formatNumber(value)} HTG"
}
