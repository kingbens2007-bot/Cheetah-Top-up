# CheetahXpress (Android)

Native Android (Kotlin & Jetpack Compose) game top-up storefront for **CheetahXpress** — *Rechaj jwèt rapid ak sèvis ki fyab*.

## Features
- **Game Catalog & Packs**: Browse Free Fire (in stock) and upcoming games (PUBG Mobile, Dream League Soccer, Delta Force, Mobile Legends, Blood Strike, Call of Duty Mobile) with HTG pricing, single/dual-column pack grids, and "Pi bon pri" badges.
- **Seasonal Promo Logic**: Automatic -2% December promo calculation in `America/Port-au-Prince` timezone (Dec 1–30, 2026), excluding subscription and pass items.
- **Game ID Validation**: Numeric-only Player ID (min 6 digits) and Zone ID (min 3 digits for Mobile Legends), or hidden when not required (Dream League Soccer).
- **Local Payment Methods**: Select between NatCash (`+509 3551-4145`) and MonCash (`+509 4490-3997`) for account holder John Rubens, with one-tap clipboard copy and confirmation feedback.
- **WhatsApp Order Integration**: Sticky bottom summary bar that launches a pre-filled Haitian Creole order message via WhatsApp (`https://wa.me/50935514145`), plus direct WhatsApp and email contact actions.

## Tech Stack
- Kotlin, Jetpack Compose, Material 3, ViewModel & StateFlow
- Local bundled typography (`Outfit` & `Plus Jakarta Sans`) and custom adaptive launcher icon
