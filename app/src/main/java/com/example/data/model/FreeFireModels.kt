package com.example.data.model

enum class DeviceBrand(
    val brandId: String,
    val displayName: String,
    val emoji: String,
    val defaultDpiRange: String,
    val safeDpiLimit: Int
) {
    APPLE("apple", "Apple / iPhone", "🍎", "DPI géré par iOS (3x)", 0),
    SAMSUNG("samsung", "Samsung", "📱", "360 - 411 DPI", 800),
    XIAOMI("xiaomi", "Xiaomi / Redmi / POCO", "⚡", "392 - 440 DPI", 790),
    INFINIX("infinix", "Infinix", "🔥", "360 - 400 DPI", 680),
    TECNO("tecno", "Tecno", "🎯", "360 - 400 DPI", 680),
    REALME("realme", "Realme", "🚀", "360 - 411 DPI", 750),
    OPPO("oppo", "Oppo", "💎", "360 - 411 DPI", 720),
    VIVO("vivo", "Vivo / iQOO", "🎮", "380 - 420 DPI", 720),
    HONOR("honor", "Honor / Huawei", "🛡️", "360 - 420 DPI", 700),
    ONEPLUS("oneplus", "OnePlus", "⚡", "380 - 420 DPI", 780),
    MOTOROLA("motorola", "Motorola", "🏍️", "360 - 411 DPI", 700),
    PIXEL("pixel", "Google Pixel", "🌐", "380 - 440 DPI", 720),
    ASUS_ROG("asus_rog", "Asus ROG Phone", "👾", "390 - 450 DPI", 900),
    REDMAGIC("redmagic", "RedMagic / Nubia", "🕹️", "400 - 480 DPI", 900),
    ITEL("itel", "Itel", "📲", "320 - 360 DPI", 550),
    SONY("sony", "Sony Xperia", "📷", "380 - 420 DPI", 750),
    UNIVERSAL_OTHER("other", "Toutes Autres Marques", "🌍", "360 - 400 DPI", 650);

    companion object {
        fun fromString(name: String): DeviceBrand {
            val lower = name.lowercase().trim()
            return entries.firstOrNull { 
                lower.contains(it.brandId) || lower.contains(it.name.lowercase()) || it.displayName.lowercase().contains(lower) 
            } ?: UNIVERSAL_OTHER
        }
    }
}

enum class SensiPreset(
    val title: String,
    val subtitle: String,
    val iconEmoji: String,
    val badge: String,
    val generalMultiplier: Float,
    val redDotMultiplier: Float,
    val buttonDelta: Int,
    val dpiDelta: Int
) {
    HEADSHOT_100(
        title = "100% Headshot (One-Tap)",
        subtitle = "Calibré 0-200 pour Desert Eagle, M1887, Woodpecker & Shotguns",
        iconEmoji = "🎯",
        badge = "ONE-TAP (0-200)",
        generalMultiplier = 1.0f,
        redDotMultiplier = 1.02f,
        buttonDelta = -3,
        dpiDelta = 60
    ),
    TOURNAMENT_PRO(
        title = "Tournoi E-Sport Pro",
        subtitle = "Stabilité chirurgicale 0-200, recul maîtrisé en Clash Squad",
        iconEmoji = "🏆",
        badge = "PRO TOURNAMENT",
        generalMultiplier = 0.95f,
        redDotMultiplier = 0.96f,
        buttonDelta = +2,
        dpiDelta = 0
    ),
    RUSH_360(
        title = "Rush Ultra-Rapide (360°)",
        subtitle = "Vitesse max 200 au corps-à-corps pour SMG MP40, UMP & saut",
        iconEmoji = "🌪️",
        badge = "RUSH 360°",
        generalMultiplier = 1.05f,
        redDotMultiplier = 1.04f,
        buttonDelta = -5,
        dpiDelta = 90
    ),
    BALANCED_NO_DPI(
        title = "Classique Sans DPI",
        subtitle = "Réglage réel 0-200 sans toucher aux options développeur (0 risque)",
        iconEmoji = "🛡️",
        badge = "SANS DPI",
        generalMultiplier = 0.98f,
        redDotMultiplier = 0.98f,
        buttonDelta = 0,
        dpiDelta = -100
    )
}

data class PhoneModel(
    val id: String,
    val brand: DeviceBrand,
    val name: String,
    val baseGeneral: Int,     // 0 - 200
    val baseRedDot: Int,      // 0 - 200
    val baseScope2x: Int,     // 0 - 200
    val baseScope4x: Int,     // 0 - 200
    val baseSniper: Int,      // 0 - 200
    val baseFreeLook: Int,    // 0 - 200
    val defaultDpi: Int,
    val recommendedDpi: Int,
    val maxSafeDpi: Int,
    val fireButtonSize: Int,  // %
    val fireButtonPosition: String = "Bas-droite (15% - 20%)",
    val pointerSpeed: String = "Maximum (100%)",
    val recommendedHz: String = "90Hz / 120Hz",
    val graphicsPreset: String = "Fluide + FPS Élevé (High FPS)",
    val specialTips: String = "Faire un mouvement 'J-Drag' pour les courtes distances."
)

data class SensiConfiguration(
    val id: String,
    val phoneModel: PhoneModel,
    val preset: SensiPreset,
    val general: Int,       // 0 - 200
    val redDot: Int,        // 0 - 200
    val scope2x: Int,       // 0 - 200
    val scope4x: Int,       // 0 - 200
    val sniper: Int,        // 0 - 200
    val freeLook: Int,      // 0 - 200
    val dpi: Int,
    val fireButtonSize: Int,
    val fireButtonPosition: String,
    val pointerSpeed: String,
    val graphicsSettings: String,
    val advice: String,
    val isCustom: Boolean = false
) {
    fun toFormattedShareText(): String {
        return """
            🔥 ANOS AURA - SENSI FREE FIRE OFFICIELLE (0 - 200) 🔥
            📱 Appareil : ${phoneModel.brand.displayName} - ${phoneModel.name}
            ⚡ Configuration : ${preset.title}
            
            🎯 SENSIBILITÉS EN JEU (Échelle 0 - 200) :
            • Général (Caméra) : $general / 200
            • Point Rouge (Red Dot) : $redDot / 200
            • Mire 2X : $scope2x / 200
            • Mire 4X : $scope4x / 200
            • Mire Fusil de Précision (Sniper) : $sniper / 200
            • Regard Libre : $freeLook / 200
            
            ⚙️ PARAMÈTRES TÉLÉPHONE & HUD RÉELS :
            • 🔴 Taille Bouton de Tir : $fireButtonSize%
            • 📍 Emplacement : $fireButtonPosition
            • 📐 Largeur Minimale (DPI) : ${if (preset == SensiPreset.BALANCED_NO_DPI || dpi <= 0) "DPI par défaut (${phoneModel.defaultDpi})" else "$dpi (DPI Sécurisé)"}
            • ⚡ Vitesse du pointeur : $pointerSpeed
            • 🖥️ Graphismes : $graphicsSettings
            
            💡 Astuce One-Tap Réelle : $advice
            
            🚀 Calibré avec Anos Aura (Toutes marques & modèles du monde)
        """.trimIndent()
    }
}

data class SavedSensi(
    val id: String,
    val title: String,
    val configuration: SensiConfiguration,
    val savedAtTimestamp: Long = System.currentTimeMillis(),
    val notes: String = ""
)

data class FreeFireWeaponGuide(
    val id: String,
    val name: String,
    val category: String, // Shotgun, Pistol, SMG, AR, Sniper
    val emoji: String,
    val idealSensiGeneral: Int, // 0 - 200
    val idealRedDot: Int,       // 0 - 200
    val idealScope2x: Int = 180,
    val idealScope4x: Int = 175,
    val idealSniper: Int = 125,
    val idealButtonSize: Int = 45,
    val buttonDragStyle: String,
    val tips: String
)

data class HeadshotTutorial(
    val id: String,
    val title: String,
    val category: String,
    val emoji: String,
    val durationMin: Int,
    val summary: String,
    val detailedSteps: List<String>,
    val proSecret: String
)
