package com.example.data

import android.net.Uri
import com.example.model.GameCatalogItem
import com.example.model.IdRequirement
import com.example.model.PackGroup
import com.example.model.PackItem
import com.example.model.PaymentMethod
import com.example.model.PriceFormatter
import com.example.model.PromoConfig
import java.time.LocalDate
import java.time.ZoneId

object TopUpRepository {

    const val WHATSAPP_NUMBER = "50935514145"
    const val ACCOUNT_HOLDER = "John Rubens"
    const val CONTACT_EMAIL = "johnstorebusiness01@gmail.com"
    const val DIRECT_WHATSAPP_URL = "https://wa.me/$WHATSAPP_NUMBER"

    val promoConfig = PromoConfig(
        startDate = LocalDate.of(2026, 12, 1),
        endDate = LocalDate.of(2026, 12, 30),
        discountPercent = 2,
        force = null
    )

    fun isPromoActive(): Boolean {
        promoConfig.force?.let { return it }
        val todayHaiti = try {
            LocalDate.now(ZoneId.of("America/Port-au-Prince"))
        } catch (_: Exception) {
            LocalDate.now()
        }
        return !todayHaiti.isBefore(promoConfig.startDate) && !todayHaiti.isAfter(promoConfig.endDate)
    }

    val paymentMethods: List<PaymentMethod> = listOf(
        PaymentMethod(
            id = "natcash",
            name = "NatCash",
            displayNumber = "+509 3551-4145",
            rawCopyNumber = "35514145",
            holderName = ACCOUNT_HOLDER
        ),
        PaymentMethod(
            id = "moncash",
            name = "MonCash",
            displayNumber = "+509 4490-3997",
            rawCopyNumber = "44903997",
            holderName = ACCOUNT_HOLDER
        )
    )

    val games: List<GameCatalogItem> = listOf(
        GameCatalogItem(
            id = "free_fire",
            name = "Free Fire",
            inStock = true,
            idRequirement = IdRequirement.PLAYER_ONLY,
            groups = listOf(
                PackGroup(
                    title = "Dyaman",
                    packs = listOf(
                        numPack("ff_110", 110, "Dyaman", 150),
                        numPack("ff_341", 341, "Dyaman", 440),
                        numPack("ff_572", 572, "Dyaman", 730),
                        numPack("ff_1166", 1166, "Dyaman", 1450),
                        numPack("ff_2398", 2398, "Dyaman", 2800),
                        numPack("ff_6160", 6160, "Dyaman", 6500, badge = "Pi bon pri")
                    )
                ),
                PackGroup(
                    title = "Abònman",
                    packs = listOf(
                        textPack("ff_weekly", "Semèn", "Abònman", 370, promo = false),
                        textPack("ff_monthly", "Mwa", "Abònman", 1850, promo = false)
                    )
                ),
                PackGroup(
                    title = "Pass",
                    packs = listOf(
                        textPack("ff_booyah", "Booyah Pass", "Free Fire", 640, promo = false)
                    )
                )
            )
        ),
        GameCatalogItem(
            id = "pubg_mobile",
            name = "PUBG Mobile",
            inStock = false,
            idRequirement = IdRequirement.PLAYER_ONLY,
            groups = listOf(
                PackGroup(
                    title = "UC",
                    packs = listOf(
                        numPack("pubg_60", 60, "UC", 185),
                        numPack("pubg_325", 325, "UC", 925),
                        numPack("pubg_660", 660, "UC", 1850),
                        numPack("pubg_1800", 1800, "UC", 4615),
                        numPack("pubg_3850", 3850, "UC", 9225),
                        numPack("pubg_8100", 8100, "UC", 18450)
                    )
                )
            )
        ),
        GameCatalogItem(
            id = "dls",
            name = "Dream League Soccer",
            inStock = false,
            idRequirement = IdRequirement.NONE,
            groups = listOf(
                PackGroup(
                    title = "Kat Visa pou Google Play",
                    packs = listOf(
                        textPack("dls_promo5", "Pack Promo $5", "Kat Visa", 1055),
                        textPack("dls_spec5", "Offre Spéciale $5", "Kat Visa", 1055),
                        textPack("dls_coupe10", "Coupe de pièces $10", "Kat Visa", 2125),
                        textPack("dls_gem15", "Coffre de Gemmes $15", "Kat Visa", 3180)
                    )
                )
            )
        ),
        GameCatalogItem(
            id = "delta_force",
            name = "Delta Force",
            inStock = false,
            idRequirement = IdRequirement.PLAYER_ONLY,
            groups = listOf(
                PackGroup(
                    title = "Coins",
                    packs = listOf(
                        numPack("df_18", 18, "Coins", 45),
                        numPack("df_30", 30, "Coins", 75),
                        numPack("df_60", 60, "Coins", 150),
                        numPack("df_320", 320, "Coins", 735),
                        numPack("df_460", 460, "Coins", 1065),
                        numPack("df_750", 750, "Coins", 1465),
                        numPack("df_1480", 1480, "Coins", 2930),
                        numPack("df_1980", 1980, "Coins", 3660),
                        numPack("df_3950", 3950, "Coins", 7320),
                        numPack("df_8100", 8100, "Coins", 14640),
                        numPack("df_16200", 16200, "Coins", 29270),
                        numPack("df_24300", 24300, "Coins", 43905)
                    )
                ),
                PackGroup(
                    title = "Pass",
                    packs = listOf(
                        textPack("df_warfare", "Season Pass Warfare", "Pass", 795, promo = false),
                        textPack("df_ops", "Season Pass Operations", "Pass", 795, promo = false),
                        textPack("df_deluxe", "Season Pass Deluxe", "Pass", 1100, promo = false)
                    )
                )
            )
        ),
        GameCatalogItem(
            id = "mobile_legends",
            name = "Mobile Legends",
            inStock = false,
            idRequirement = IdRequirement.PLAYER_AND_ZONE,
            groups = listOf(
                PackGroup(
                    title = "Dyaman",
                    packs = listOf(
                        numPack("ml_85", 85, "Dyaman", 275),
                        numPack("ml_170", 170, "Dyaman", 550),
                        numPack("ml_240", 240, "Dyaman", 775),
                        numPack("ml_568", 568, "Dyaman", 1795)
                    )
                ),
                PackGroup(
                    title = "Pass",
                    packs = listOf(
                        textPack("ml_weekly", "Weekly Diamond Pass", "Pass", 345, promo = false),
                        textPack("ml_twilight", "Twilight Pass", "Pass", 1785, promo = false)
                    )
                )
            )
        ),
        GameCatalogItem(
            id = "blood_strike",
            name = "Blood Strike",
            inStock = false,
            idRequirement = IdRequirement.PLAYER_ONLY,
            groups = listOf(
                PackGroup(
                    title = "Gold",
                    packs = listOf(
                        numPack("bs_51", 51, "Gold", 80),
                        numPack("bs_105", 105, "Gold", 160),
                        numPack("bs_320", 320, "Gold", 480),
                        numPack("bs_540", 540, "Gold", 800),
                        numPack("bs_1100", 1100, "Gold", 1600),
                        numPack("bs_2260", 2260, "Gold", 3200),
                        numPack("bs_5800", 5800, "Gold", 8005)
                    )
                ),
                PackGroup(
                    title = "Pass",
                    packs = listOf(
                        textPack("bs_elite", "Strike Pass Elite", "Pass", 640, promo = false),
                        textPack("bs_premium", "Strike Pass Premium", "Pass", 1445, promo = false),
                        textPack("bs_lucky", "Lucky Bag Week", "Pass", 160, promo = false),
                        textPack("bs_levelup", "Level Up Pass", "Pass", 320, promo = false)
                    )
                )
            )
        ),
        GameCatalogItem(
            id = "cod_mobile",
            name = "Call of Duty Mobile",
            inStock = false,
            idRequirement = IdRequirement.PLAYER_ONLY,
            groups = listOf(
                PackGroup(
                    title = "CP",
                    packs = listOf(
                        numPack("cod_88", 88, "CP", 200),
                        numPack("cod_460", 460, "CP", 1105),
                        numPack("cod_960", 960, "CP", 2050),
                        numPack("cod_2600", 2600, "CP", 5520),
                        numPack("cod_5400", 5400, "CP", 11045)
                    )
                )
            )
        )
    )

    fun buildWhatsAppOrderUri(
        game: GameCatalogItem,
        pack: PackItem,
        effectivePrice: Int,
        isDiscounted: Boolean,
        playerId: String,
        zoneId: String,
        paymentMethod: PaymentMethod
    ): String {
        val promoSuffix = if (isDiscounted) " (pwomo Desanm -${promoConfig.discountPercent}%)" else ""
        val idLine = when (game.idRequirement) {
            IdRequirement.NONE -> ""
            IdRequirement.PLAYER_ONLY -> "ID ${game.name}: $playerId\n"
            IdRequirement.PLAYER_AND_ZONE -> "ID ${game.name}: $playerId (Zone: $zoneId)\n"
        }
        val msg = buildString {
            append("Bonjou CheetahXpress, mwen vle fè yon kòmand.\n\n")
            append("Jwèt: ${game.name}\n")
            append("Pack: ${pack.orderPackName}\n")
            append("Pri: ${PriceFormatter.formatHtg(effectivePrice)}$promoSuffix\n")
            append(idLine)
            append("Peman: ${paymentMethod.name} (${paymentMethod.holderName})\n\n")
            append("M ap voye prèv peman an.")
        }
        return "$DIRECT_WHATSAPP_URL?text=${Uri.encode(msg)}"
    }

    private fun numPack(
        id: String,
        amount: Int,
        unit: String,
        priceHtg: Int,
        promo: Boolean = true,
        badge: String? = null
    ) = PackItem(
        id = id,
        numericAmount = amount,
        labelAmount = amount.toString(),
        unit = unit,
        basePriceHtg = priceHtg,
        promoEligible = promo,
        badgeLabel = badge
    )

    private fun textPack(
        id: String,
        label: String,
        unit: String,
        priceHtg: Int,
        promo: Boolean = true,
        badge: String? = null
    ) = PackItem(
        id = id,
        numericAmount = null,
        labelAmount = label,
        unit = unit,
        basePriceHtg = priceHtg,
        promoEligible = promo,
        badgeLabel = badge
    )
}
