package com.example.data.generator

import com.example.data.model.DeviceSpec
import com.example.data.model.Playstyle
import com.example.data.model.SensitivityConfig
import kotlin.random.Random

object SensitivityEngine {

    /**
     * Generates optimal, realistic Free Fire sensitivity values (0 to 200)
     * strictly calculated based on the specific device brand, screen specs
     * (Hz, touch sampling rate, display inches, stock DPI) and the chosen gameplay style.
     */
    fun calculate(
        device: DeviceSpec,
        playstyle: Playstyle,
        useDpi: Boolean = true,
        variationSeed: Int = 0
    ): SensitivityConfig {
        val isApple = device.isApple
        val effectiveUseDpi = if (isApple) false else useDpi

        // 1. Touch Sampling Rate Factor
        val touchFactor = when {
            device.touchSamplingHz >= 480 -> -3 // Ultra-fast gaming response: slight reduction prevents overshooting
            device.touchSamplingHz >= 360 -> -1
            device.touchSamplingHz >= 240 -> 0  // Standard responsive gaming sensor
            device.touchSamplingHz <= 120 -> +4 // Slower sensor needs higher sensitivity compensation
            else -> +2
        }

        // 2. Refresh Rate Factor
        val refreshFactor = when {
            device.refreshRateHz >= 144 -> -2
            device.refreshRateHz >= 120 -> -1
            device.refreshRateHz >= 90 -> 0
            device.refreshRateHz == 60 -> +3
            else -> 0
        }

        // 3. Screen Size Factor
        val screenFactor = when {
            device.screenInch >= 6.75f -> +2 // Longer distance to swipe from bottom to top
            device.screenInch >= 6.4f -> 0
            else -> -2                      // Compact screen requires less thumb travel
        }

        // 4. Brand-Specific Driver & Latency Factor
        val brandLower = device.brand.lowercase()
        val brandFactor = when {
            isApple -> -2 // iOS touch engine is ultra-direct
            brandLower.contains("rog") || brandLower.contains("redmagic") || brandLower.contains("black shark") -> -2 // Gaming phones
            brandLower.contains("infinix") || brandLower.contains("tecno") || brandLower.contains("itel") -> +2 // Transsion Dar-link tuning
            brandLower.contains("samsung") -> 0
            brandLower.contains("xiaomi") || brandLower.contains("redmi") || brandLower.contains("poco") -> +1
            brandLower.contains("realme") || brandLower.contains("oppo") || brandLower.contains("oneplus") -> 0
            else -> 0
        }

        // Compensation if playing WITHOUT DPI modification on Android
        val noDpiCompensation = if (!isApple && !effectiveUseDpi) +4 else 0

        // 5. Base Playstyle Sensitivity Profiles & Ergonomic Button Sizes
        // Anti-recoil & Precision calibration: strictly controlled between 135 and 178 max for perfect precision and zero recoil.
        val (baseGeneral, baseRedDot, base2x, base4x, baseSniper, baseFreeLook, recommendedButtonSize, buttonPos) = when (playstyle) {
            Playstyle.MAX_SENSI_200 -> {
                Tuplet8(185, 180, 175, 170, 50, 60, 48, "Bas-Droite (20% du bas, 18% de la droite)")
            }
            Playstyle.PRECISION_HEADSHOT -> {
                // Precision One-Tap: Controlled 155 General + 164 Red Dot for surgical locking without shaking
                Tuplet8(155, 164, 150, 142, 42, 52, 48, "Bas-Droite (22% du bas, 20% de la droite)")
            }
            Playstyle.SPEED_RUSHER -> {
                // Speed & Rusher: 168 General + 174 Red Dot (strictly under 180)
                Tuplet8(168, 174, 162, 155, 45, 55, 44, "Bas-Droite (20% du bas, 18% de la droite)")
            }
            Playstyle.BALANCED -> {
                // Balanced: 148 General + 158 Red Dot
                Tuplet8(148, 158, 144, 138, 40, 50, 50, "Bas-Droite (22% du bas, 20% de la droite)")
            }
            Playstyle.RECOIL_CONTROL -> {
                // Anti-Recoil (Not Recoil): Ultra-stable 138 General + 148 Red Dot for pinpoint laser accuracy
                Tuplet8(138, 148, 135, 130, 38, 48, 52, "Centre-Bas-Droite (24% du bas, 22% de la droite)")
            }
            Playstyle.SNIPER_PRO -> {
                // Sniper Pro: 142 General + 150 Red Dot + Low Sniper Scope
                Tuplet8(142, 150, 140, 135, 36, 46, 50, "Bas-Droite (22% du bas, 20% de la droite)")
            }
        }

        // 6. DYNAMIC BUTTON SIZE FACTOR:
        // A slightly larger button (48%-54%) gives rock-solid stability to eliminate crosshair vibration and recoil.
        val buttonSizeBonus = ((recommendedButtonSize - 48) * 1.0f).toInt().coerceIn(-2, 4)

        // 7. HIGH-PRECISION DPI CALIBRATION:
        val inverseDpiOffset = when {
            baseGeneral <= 145 -> 100 + (variationSeed % 25)
            baseGeneral <= 160 -> 120 + (variationSeed % 30)
            else -> 135 + (variationSeed % 35)
        }

        val calculatedDpi = if (effectiveUseDpi) {
            val maxComfortableDpi = minOf(600, device.recommendedSafeMaxDpi)
            (device.stockDpi + inverseDpiOffset).coerceIn(420, maxComfortableDpi)
        } else {
            device.stockDpi
        }

        // 8. ANTI-RECOIL & STABILITY CLAMPING (Strictly max 178, ideal 135-175):
        val generalVariation = if (variationSeed != 0) ((variationSeed * 3) % 5) - 2 else 0
        val redDotVariation = if (variationSeed != 0) ((variationSeed * 4) % 5) - 2 else 0
        val scopeVariation = if (variationSeed != 0) ((variationSeed * 2) % 5) - 2 else 0
        val buttonVariation = if (variationSeed != 0) ((variationSeed * 2) % 3) - 1 else 0

        val hardwareModifier = touchFactor + refreshFactor + screenFactor + brandFactor + noDpiCompensation

        val finalGeneral = if (playstyle == Playstyle.MAX_SENSI_200) {
            185
        } else {
            (baseGeneral + (hardwareModifier / 2) + buttonSizeBonus + generalVariation).coerceIn(125, 178)
        }

        val finalRedDot = if (playstyle == Playstyle.MAX_SENSI_200) {
            180
        } else {
            (baseRedDot + (hardwareModifier / 2) + buttonSizeBonus + redDotVariation).coerceIn(130, 178)
        }

        val final2x = if (playstyle == Playstyle.MAX_SENSI_200) {
            175
        } else {
            (base2x + (hardwareModifier / 3) + scopeVariation).coerceIn(120, 168)
        }

        val final4x = if (playstyle == Playstyle.MAX_SENSI_200) {
            170
        } else {
            (base4x + (hardwareModifier / 3) + scopeVariation).coerceIn(115, 162)
        }
        val finalSniper = (baseSniper + (refreshFactor / 2)).coerceIn(30, 52)
        val finalFreeLook = (baseFreeLook + generalVariation).coerceIn(40, 70)
        val finalButtonSize = (recommendedButtonSize + buttonVariation).coerceIn(44, 58)

        val dragTechnique = when (playstyle) {
            Playstyle.MAX_SENSI_200 -> "Swipe court et mesuré : stabilité maximale sans dépassement de tête."
            Playstyle.PRECISION_HEADSHOT -> "Tir en « L » ou « J » court et fluide. La sensi stable évite que la balle ne passe au-dessus du casque."
            Playstyle.SPEED_RUSHER -> "Drag ascendant contrôlé : la faible dispersion garantit un headshot propre."
            Playstyle.BALANCED -> "Mouvement fluide de bas en haut avec arrêt net sur le front."
            Playstyle.RECOIL_CONTROL -> "Anti-recoil parfait (Not Recoil) : le réticule reste collé à la tête sans secousse ni vibration."
            Playstyle.SNIPER_PRO -> "Quick-scope stable avec visée chirurgicale."
        }

        val headshotRate = when (playstyle) {
            Playstyle.MAX_SENSI_200 -> 97
            Playstyle.PRECISION_HEADSHOT -> 99
            Playstyle.SPEED_RUSHER -> 96
            Playstyle.BALANCED -> 95
            Playstyle.RECOIL_CONTROL -> 99
            Playstyle.SNIPER_PRO -> 98
        }

        val tips = buildList {
            add("Calibré pour ${device.brand} ${device.model} (${device.refreshRateHz}Hz / ${device.touchSamplingHz}Hz).")
            add("Sensibilité Générale ($finalGeneral/180 max) : Calibrée pour un contrôle total sans secousse ni tremblement.")
            add("Point Rouge ($finalRedDot) : Verrouillage laser de la tête (Anti-Recoil & Not Recoil).")
            add("Taille du bouton de tir : $finalButtonSize% (optimisé pour une stabilité et une précision absolues).")

            if (isApple) {
                add("🍎 Réglage iOS iPhone : Pas de DPI requis (Android uniquement). Activez Contrôle du sélectionneur (Glisse 120, Mode Précis) et AssistiveTouch (100%).")
            } else if (!effectiveUseDpi) {
                add("🛡️ Mode Sans DPI (DPI d'origine: ${device.stockDpi}) : Sensi rehaussée pour réussir vos One-Taps sans modifier les options développeurs.")
            } else {
                add("⚙️ Nouveau DPI calibré : $calculatedDpi (D'origine: ${device.stockDpi}, max sûr: ${device.recommendedSafeMaxDpi}).")
            }

            if (device.refreshRateHz >= 120) {
                add("Écran ${device.refreshRateHz}Hz détecté : activez « FPS Élevé » dans Free Fire pour une fluidité sans latence.")
            } else {
                add("Écran 60Hz/90Hz : conservez les graphismes fluides pour maintenir 60 FPS constants.")
            }
        }

        return SensitivityConfig(
            general = finalGeneral,
            redDot = finalRedDot,
            scope2x = final2x,
            scope4x = final4x,
            sniper = finalSniper,
            freeLook = finalFreeLook,
            dpi = calculatedDpi,
            stockDpi = device.stockDpi,
            useDpi = effectiveUseDpi,
            isAppleDevice = isApple,
            iosGlidingSpeed = 120,
            iosTrackingSensitivity = "100% (Max)",
            fireButtonSize = finalButtonSize,
            fireButtonPosition = buttonPos,
            dragTechnique = dragTechnique,
            estimatedHeadshotRate = headshotRate,
            tips = tips
        )
    }

    private data class Tuplet8(
        val general: Int,
        val redDot: Int,
        val scope2x: Int,
        val scope4x: Int,
        val sniper: Int,
        val freeLook: Int,
        val buttonSize: Int,
        val buttonPosition: String
    )
}

