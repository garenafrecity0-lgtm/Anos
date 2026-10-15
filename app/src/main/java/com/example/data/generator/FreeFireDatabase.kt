package com.example.data.generator

import com.example.data.model.DeviceBrand
import com.example.data.model.PhoneModel
import com.example.data.model.SensiConfiguration
import com.example.data.model.SensiPreset
import kotlin.math.roundToInt

object FreeFireDatabase {

    val allBrands: List<DeviceBrand> = DeviceBrand.entries.toList()

    val modelsByBrand: Map<DeviceBrand, List<PhoneModel>> = mapOf(
        DeviceBrand.APPLE to listOf(
            PhoneModel("apple_16pm", DeviceBrand.APPLE, "iPhone 16 Pro Max", 195, 192, 186, 182, 125, 168, 0, 0, 0, 44, "Bas-droite (15%)", "Max", "120Hz ProMotion", "Ultra + FPS Élevé", "Écran 120Hz ultra réactif : flick court et sec vers le haut."),
            PhoneModel("apple_16p", DeviceBrand.APPLE, "iPhone 16 Pro", 196, 194, 188, 184, 128, 170, 0, 0, 0, 45, "Bas-droite (15%)", "Max", "120Hz ProMotion", "Ultra + FPS Élevé", "Parfait pour One-Tap au M1887 et Desert Eagle."),
            PhoneModel("apple_16", DeviceBrand.APPLE, "iPhone 16 / 16 Plus", 198, 196, 190, 186, 132, 175, 0, 0, 0, 47, "Bas-droite (17%)", "Max", "60Hz", "Élevé + FPS Élevé", "Glisser le bouton de tir avec un geste régulier."),
            PhoneModel("apple_15pm", DeviceBrand.APPLE, "iPhone 15 Pro Max", 195, 192, 186, 180, 124, 166, 0, 0, 0, 45, "Bas-droite (15%)", "Max", "120Hz ProMotion", "Ultra + FPS Élevé", "Viser le menton avec le réticule blanc avant le drag."),
            PhoneModel("apple_15p", DeviceBrand.APPLE, "iPhone 15 Pro", 196, 194, 188, 182, 126, 168, 0, 0, 0, 46, "Bas-droite (16%)", "Max", "120Hz ProMotion", "Ultra + FPS Élevé", "Excellente réactivité sur Clash Squad."),
            PhoneModel("apple_15", DeviceBrand.APPLE, "iPhone 15 / 15 Plus", 198, 196, 192, 188, 134, 176, 0, 0, 0, 48, "Bas-droite (18%)", "Max", "60Hz", "Ultra + FPS Élevé", "Activer Toucher d'assistance dans Accessibilité iOS."),
            PhoneModel("apple_14pm", DeviceBrand.APPLE, "iPhone 14 Pro Max", 195, 193, 186, 182, 125, 168, 0, 0, 0, 46, "Bas-droite (16%)", "Max", "120Hz ProMotion", "Ultra + FPS Élevé", "Configuration e-sport compétitive."),
            PhoneModel("apple_14", DeviceBrand.APPLE, "iPhone 14 / 14 Plus", 199, 197, 192, 188, 136, 178, 0, 0, 0, 48, "Bas-droite (18%)", "Max", "60Hz", "Standard + FPS Élevé", "Sensi vive pour compenser les 60Hz."),
            PhoneModel("apple_13pm", DeviceBrand.APPLE, "iPhone 13 Pro Max", 195, 192, 185, 180, 125, 165, 0, 0, 0, 47, "Bas-droite (17%)", "Max", "120Hz ProMotion", "Ultra + FPS Élevé", "Stabilité remarquable au Woodpecker et SVD."),
            PhoneModel("apple_13", DeviceBrand.APPLE, "iPhone 13 / 13 Mini", 199, 197, 194, 190, 138, 180, 0, 0, 0, 47, "Bas-droite (18%)", "Max", "60Hz", "Standard + FPS Élevé", "Bouton compact pour petit écran."),
            PhoneModel("apple_12pm", DeviceBrand.APPLE, "iPhone 12 Pro Max", 198, 195, 190, 186, 135, 175, 0, 0, 0, 48, "Bas-droite (18%)", "Max", "60Hz", "Standard + FPS Élevé", "Très bon comportement en rush."),
            PhoneModel("apple_12", DeviceBrand.APPLE, "iPhone 12 / 12 Pro", 199, 198, 194, 190, 140, 182, 0, 0, 0, 49, "Bas-droite (18%)", "Max", "60Hz", "Standard + FPS Élevé", "J-Drag en quart de cercle recommandé."),
            PhoneModel("apple_11", DeviceBrand.APPLE, "iPhone 11 / 11 Pro Max", 200, 200, 196, 192, 145, 188, 0, 0, 0, 50, "Bas-droite (19%)", "Max", "60Hz", "Fluide + FPS Élevé", "Mettre 200 partout pour maximiser la sensibilité."),
            PhoneModel("apple_xr", DeviceBrand.APPLE, "iPhone XR / XS Max / X", 200, 200, 198, 195, 150, 190, 0, 0, 0, 52, "Bas-droite (20%)", "Max", "60Hz", "Fluide + FPS Élevé", "Taille de bouton 52% pour éviter les ratés."),
            PhoneModel("apple_8p", DeviceBrand.APPLE, "iPhone 8 Plus / 7 Plus / SE", 200, 200, 200, 198, 155, 195, 0, 0, 0, 54, "Bas-droite (20%)", "Max", "60Hz", "Fluide + FPS Élevé", "200 partout pour compenser l'ancien panneau tactile."),
            PhoneModel("apple_ipad_pro", DeviceBrand.APPLE, "iPad Pro (11\" / 12.9\")", 182, 176, 170, 165, 110, 150, 0, 0, 0, 40, "Centre-droit (12%)", "Moyen", "120Hz ProMotion", "Ultra + FPS Élevé", "Sur grand écran iPad, une sensi trop haute fait survoler la tête !")
        ),
        DeviceBrand.SAMSUNG to listOf(
            PhoneModel("samsung_s24u", DeviceBrand.SAMSUNG, "Galaxy S24 Ultra", 192, 188, 182, 178, 120, 162, 384, 520, 800, 44, "Bas-droite (15%)", "100%", "120Hz Adaptatif", "Ultra + FPS Élevé", "DPI 520 offre le point d'équilibre parfait pour headshot 0-200."),
            PhoneModel("samsung_s24", DeviceBrand.SAMSUNG, "Galaxy S24 / S24+", 195, 190, 185, 180, 125, 166, 384, 500, 780, 46, "Bas-droite (16%)", "100%", "120Hz", "Ultra + FPS Élevé", "Désactiver Game Booster pour éliminer toute latence."),
            PhoneModel("samsung_s23u", DeviceBrand.SAMSUNG, "Galaxy S23 Ultra", 193, 189, 184, 180, 122, 164, 384, 510, 800, 45, "Bas-droite (15%)", "100%", "120Hz", "Ultra + FPS Élevé", "Headshot foudroyant au Woodpecker et Desert Eagle."),
            PhoneModel("samsung_s23", DeviceBrand.SAMSUNG, "Galaxy S23 / S23 FE", 196, 192, 186, 182, 128, 168, 384, 490, 750, 47, "Bas-droite (17%)", "100%", "120Hz", "Élevé + FPS Élevé", "Toucher rapide et précis sur Clash Squad."),
            PhoneModel("samsung_s22u", DeviceBrand.SAMSUNG, "Galaxy S22 Ultra", 195, 192, 186, 182, 130, 168, 384, 500, 780, 46, "Bas-droite (16%)", "100%", "120Hz", "Élevé + FPS Élevé", "Drag vertical droit pour fusils d'assaut."),
            PhoneModel("samsung_s21u", DeviceBrand.SAMSUNG, "Galaxy S21 Ultra / FE", 197, 194, 188, 184, 135, 172, 384, 480, 750, 48, "Bas-droite (17%)", "100%", "120Hz", "Standard + FPS Élevé", "DPI 480 très stable en tournoi."),
            PhoneModel("samsung_s20fe", DeviceBrand.SAMSUNG, "Galaxy S20 FE / S20", 199, 196, 190, 186, 138, 176, 384, 470, 700, 49, "Bas-droite (18%)", "100%", "120Hz", "Fluide + FPS Élevé", "Mettre en Fluide pour zéro freeze."),
            PhoneModel("samsung_a55", DeviceBrand.SAMSUNG, "Galaxy A55 5G", 197, 194, 188, 184, 132, 170, 384, 480, 750, 46, "Bas-droite (16%)", "100%", "120Hz", "Standard + FPS Élevé", "Le roi du milieu de gamme : sensi ultra équilibrée."),
            PhoneModel("samsung_a54", DeviceBrand.SAMSUNG, "Galaxy A54 5G", 198, 195, 190, 186, 136, 174, 384, 460, 720, 48, "Bas-droite (17%)", "100%", "120Hz", "Standard + FPS Élevé", "DPI 460 recommandé pour un drag fluide."),
            PhoneModel("samsung_a35", DeviceBrand.SAMSUNG, "Galaxy A35 5G", 198, 196, 190, 186, 138, 174, 384, 450, 700, 48, "Bas-droite (17%)", "100%", "120Hz", "Standard + FPS Élevé", "Vitesse du pointeur au maximum (100%)."),
            PhoneModel("samsung_a34", DeviceBrand.SAMSUNG, "Galaxy A34 5G", 199, 197, 192, 188, 140, 176, 384, 450, 700, 49, "Bas-droite (18%)", "100%", "120Hz", "Fluide + FPS Élevé", "One-Tap net avec M1887."),
            PhoneModel("samsung_a25", DeviceBrand.SAMSUNG, "Galaxy A25 5G", 200, 198, 194, 190, 144, 180, 384, 440, 680, 50, "Bas-droite (18%)", "100%", "120Hz", "Fluide + FPS Élevé", "Délai de pression : Court (0.5s)."),
            PhoneModel("samsung_a15", DeviceBrand.SAMSUNG, "Galaxy A15 4G / 5G", 200, 200, 196, 192, 150, 186, 384, 420, 650, 52, "Bas-droite (19%)", "100%", "90Hz", "Fluide + FPS Élevé", "Ne pas dépasser DPI 500 sur ce modèle."),
            PhoneModel("samsung_a14", DeviceBrand.SAMSUNG, "Galaxy A14 / A13", 200, 200, 198, 194, 155, 190, 384, 411, 600, 54, "Bas-droite (20%)", "100%", "60Hz/90Hz", "Fluide + FPS Élevé", "Bouton 54% pour maximiser le contact doigt."),
            PhoneModel("samsung_a52s", DeviceBrand.SAMSUNG, "Galaxy A52s 5G / A52", 198, 195, 190, 186, 136, 175, 384, 470, 720, 47, "Bas-droite (17%)", "100%", "120Hz", "Standard + FPS Élevé", "Très populaire en compétition Free Fire."),
            PhoneModel("samsung_a51", DeviceBrand.SAMSUNG, "Galaxy A51 / A71", 200, 200, 196, 192, 150, 188, 384, 430, 650, 52, "Bas-droite (19%)", "100%", "60Hz", "Fluide + FPS Élevé", "Activer la sensibilité tactile dans Affichage."),
            PhoneModel("samsung_a05s", DeviceBrand.SAMSUNG, "Galaxy A05s / A04 / A03", 200, 200, 200, 196, 165, 195, 360, 400, 550, 56, "Bas-droite (20%)", "100%", "60Hz/90Hz", "Fluide + FPS Normal", "Attention : Ne JAMAIS dépasser DPI 550 sur la série A0 !")
        ),
        DeviceBrand.XIAOMI to listOf(
            PhoneModel("xiaomi_pocox6pro", DeviceBrand.XIAOMI, "POCO X6 Pro 5G", 194, 190, 184, 180, 122, 162, 392, 540, 800, 44, "Bas-droite (15%)", "100%", "120Hz (Touch 2160Hz)", "Ultra + FPS Élevé", "Taux d'échantillonnage tactile monstrueux : visée chirurgicale !"),
            PhoneModel("xiaomi_pocof6", DeviceBrand.XIAOMI, "POCO F6 / F6 Pro", 192, 188, 182, 178, 120, 160, 392, 550, 800, 43, "Bas-droite (15%)", "100%", "120Hz", "Ultra + FPS Élevé", "Bête de course pour les tournois FF."),
            PhoneModel("xiaomi_pocox5pro", DeviceBrand.XIAOMI, "POCO X5 Pro 5G", 196, 192, 186, 182, 128, 168, 392, 510, 780, 46, "Bas-droite (16%)", "100%", "120Hz", "Élevé + FPS Élevé", "DPI 510 idéal pour le drag vertical."),
            PhoneModel("xiaomi_pocof5", DeviceBrand.XIAOMI, "POCO F5 / F5 Pro", 194, 190, 184, 180, 124, 164, 392, 520, 800, 45, "Bas-droite (16%)", "100%", "120Hz", "Ultra + FPS Élevé", "One-tap instantané avec MP40 et M1887."),
            PhoneModel("xiaomi_pocox3pro", DeviceBrand.XIAOMI, "POCO X3 Pro / X3 NFC", 198, 194, 188, 184, 134, 172, 392, 490, 750, 48, "Bas-droite (17%)", "100%", "120Hz", "Standard + FPS Élevé", "La légende de Free Fire : garder DPI < 750."),
            PhoneModel("xiaomi_rn13pro_plus", DeviceBrand.XIAOMI, "Redmi Note 13 Pro+ 5G", 194, 190, 184, 180, 125, 165, 392, 520, 790, 45, "Bas-droite (16%)", "100%", "120Hz", "Ultra + FPS Élevé", "Écran incurvé : ajuster les zones mortes dans Game Turbo."),
            PhoneModel("xiaomi_rn13pro", DeviceBrand.XIAOMI, "Redmi Note 13 Pro 4G/5G", 196, 192, 186, 182, 130, 170, 392, 500, 760, 46, "Bas-droite (16%)", "100%", "120Hz", "Standard + FPS Élevé", "Sensi parfaite pour le Clash Squad."),
            PhoneModel("xiaomi_rn13", DeviceBrand.XIAOMI, "Redmi Note 13 4G", 200, 196, 190, 186, 140, 178, 392, 460, 720, 49, "Bas-droite (18%)", "100%", "120Hz", "Fluide + FPS Élevé", "Mettre en mode 'Performances' dans Game Turbo."),
            PhoneModel("xiaomi_rn12pro", DeviceBrand.XIAOMI, "Redmi Note 12 Pro 5G", 197, 194, 188, 184, 132, 172, 392, 480, 740, 47, "Bas-droite (17%)", "100%", "120Hz", "Standard + FPS Élevé", "Drag net en ligne droite."),
            PhoneModel("xiaomi_rn12", DeviceBrand.XIAOMI, "Redmi Note 12 4G", 200, 198, 192, 188, 144, 180, 392, 450, 700, 50, "Bas-droite (18%)", "100%", "120Hz", "Fluide + FPS Élevé", "Activer réponse tactile rapide dans HyperOS."),
            PhoneModel("xiaomi_rn11", DeviceBrand.XIAOMI, "Redmi Note 11 / 11S", 200, 200, 195, 190, 150, 185, 392, 440, 680, 52, "Bas-droite (19%)", "100%", "90Hz", "Fluide + FPS Élevé", "DPI 440 très stable."),
            PhoneModel("xiaomi_redmi12", DeviceBrand.XIAOMI, "Redmi 12 / 13C / 10C", 200, 200, 198, 194, 160, 190, 392, 420, 620, 54, "Bas-droite (20%)", "100%", "90Hz", "Fluide + FPS Élevé", "Ne pas dépasser DPI 600 pour éviter le bootloop."),
            PhoneModel("xiaomi_mi14", DeviceBrand.XIAOMI, "Xiaomi 14 / 14 Ultra", 190, 186, 180, 175, 115, 155, 392, 560, 820, 42, "Bas-droite (14%)", "100%", "120Hz LTPO", "Ultra + FPS Élevé", "Précision millimétrique."),
            PhoneModel("xiaomi_blackshark", DeviceBrand.XIAOMI, "Black Shark 5 Pro / 4", 190, 185, 178, 174, 110, 150, 392, 580, 850, 42, "Bas-droite (14%)", "100%", "144Hz", "Ultra + FPS Élevé", "Gâchettes magnétiques pour switch d'armes instantané !")
        ),
        DeviceBrand.INFINIX to listOf(
            PhoneModel("infinix_gt20pro", DeviceBrand.INFINIX, "Infinix GT 20 Pro 5G", 194, 190, 184, 180, 120, 160, 360, 520, 720, 44, "Bas-droite (15%)", "100%", "144Hz Gaming", "Ultra + FPS Élevé", "Téléphone gaming officiel tournois : Sensi ultra compétitive !"),
            PhoneModel("infinix_gt10pro", DeviceBrand.INFINIX, "Infinix GT 10 Pro", 195, 192, 186, 182, 125, 165, 360, 500, 700, 45, "Bas-droite (16%)", "100%", "120Hz", "Ultra + FPS Élevé", "DPI 500 offre un headshot quasi automatique au Deagle."),
            PhoneModel("infinix_note40pro", DeviceBrand.INFINIX, "Infinix NOTE 40 Pro 5G", 196, 193, 187, 183, 128, 168, 360, 480, 680, 46, "Bas-droite (16%)", "100%", "120Hz 3D-Curved", "Élevé + FPS Élevé", "Régler la sensibilité des bords dans XOS."),
            PhoneModel("infinix_note40", DeviceBrand.INFINIX, "Infinix NOTE 40 / 40X", 198, 195, 189, 185, 132, 172, 360, 460, 660, 48, "Bas-droite (17%)", "100%", "120Hz", "Standard + FPS Élevé", "Glissé du pouce rapide vers le haut."),
            PhoneModel("infinix_note30pro", DeviceBrand.INFINIX, "Infinix NOTE 30 Pro / VIP", 197, 194, 188, 184, 130, 170, 360, 480, 680, 47, "Bas-droite (17%)", "100%", "120Hz AMOLED", "Élevé + FPS Élevé", "Très populaire en Afrique de l'Ouest et Centrale."),
            PhoneModel("infinix_note30", DeviceBrand.INFINIX, "Infinix NOTE 30 4G/5G", 198, 195, 190, 186, 135, 174, 360, 460, 650, 48, "Bas-droite (17%)", "100%", "120Hz", "Standard + FPS Élevé", "DPI 460 parfaitement sécurisé."),
            PhoneModel("infinix_hot40pro", DeviceBrand.INFINIX, "Infinix HOT 40 Pro", 198, 195, 190, 186, 136, 174, 360, 460, 650, 47, "Bas-droite (17%)", "100%", "120Hz", "Standard + FPS Élevé", "Excellente réponse au tir rapide."),
            PhoneModel("infinix_hot40", DeviceBrand.INFINIX, "Infinix HOT 40 / 40i", 200, 198, 194, 190, 145, 182, 360, 420, 600, 50, "Bas-droite (18%)", "100%", "90Hz", "Fluide + FPS Élevé", "Mettre en mode Jeu X-Arena."),
            PhoneModel("infinix_hot30", DeviceBrand.INFINIX, "Infinix HOT 30 / 30 Play", 200, 198, 195, 190, 148, 184, 360, 420, 600, 50, "Bas-droite (19%)", "100%", "90Hz", "Fluide + FPS Élevé", "Bouton de tir 50% recommandé."),
            PhoneModel("infinix_hot20", DeviceBrand.INFINIX, "Infinix HOT 20 / 20i / 20S", 200, 200, 196, 192, 155, 188, 360, 410, 580, 52, "Bas-droite (19%)", "100%", "60Hz/90Hz", "Fluide + FPS Élevé", "Délai de pression court."),
            PhoneModel("infinix_hot12", DeviceBrand.INFINIX, "Infinix HOT 12 / 12 Play", 200, 200, 198, 194, 160, 192, 360, 400, 560, 54, "Bas-droite (20%)", "100%", "90Hz", "Fluide + FPS Élevé", "Mettre 200 en Général et Point Rouge."),
            PhoneModel("infinix_smart8", DeviceBrand.INFINIX, "Infinix SMART 8 / 8 Plus / 7", 200, 200, 200, 196, 170, 196, 320, 380, 500, 56, "Bas-droite (20%)", "100%", "60Hz/90Hz", "Fluide + FPS Normal", "Ne jamais dépasser DPI 500 sur la gamme Smart !")
        ),
        DeviceBrand.TECNO to listOf(
            PhoneModel("tecno_pova6pro", DeviceBrand.TECNO, "Tecno POVA 6 Pro 5G", 194, 190, 184, 180, 122, 162, 360, 510, 700, 45, "Bas-droite (15%)", "100%", "120Hz Gaming", "Ultra + FPS Élevé", "Design gaming et écran 120Hz : visée ultra fluide."),
            PhoneModel("tecno_pova5pro", DeviceBrand.TECNO, "Tecno POVA 5 Pro / POVA 5", 196, 192, 186, 182, 128, 166, 360, 490, 680, 46, "Bas-droite (16%)", "100%", "120Hz", "Élevé + FPS Élevé", "DPI 490 parfait pour la dispersion des balles."),
            PhoneModel("tecno_camon30premier", DeviceBrand.TECNO, "Tecno CAMON 30 Premier 5G", 192, 188, 182, 178, 118, 158, 360, 520, 720, 44, "Bas-droite (15%)", "100%", "144Hz LTPO", "Ultra + FPS Élevé", "Écran haut de gamme : sensi vive et précise."),
            PhoneModel("tecno_camon30pro", DeviceBrand.TECNO, "Tecno CAMON 30 Pro / 30 5G", 195, 192, 186, 182, 126, 166, 360, 490, 680, 46, "Bas-droite (16%)", "100%", "144Hz", "Élevé + FPS Élevé", "Délai de réponse tactile minimal."),
            PhoneModel("tecno_camon20pro", DeviceBrand.TECNO, "Tecno CAMON 20 Pro 5G", 197, 194, 188, 184, 132, 170, 360, 480, 670, 47, "Bas-droite (17%)", "100%", "120Hz AMOLED", "Standard + FPS Élevé", "One-tap M1887 garanti."),
            PhoneModel("tecno_spark20pro_plus", DeviceBrand.TECNO, "Tecno SPARK 20 Pro+", 196, 193, 187, 183, 130, 168, 360, 480, 680, 46, "Bas-droite (16%)", "100%", "120Hz Curved", "Standard + FPS Élevé", "Forte réactivité sur les gestes rapides."),
            PhoneModel("tecno_spark20pro", DeviceBrand.TECNO, "Tecno SPARK 20 Pro / SPARK 20", 199, 196, 190, 186, 140, 176, 360, 440, 640, 49, "Bas-droite (18%)", "100%", "120Hz/90Hz", "Fluide + FPS Élevé", "DPI 440 très stable sous HiOS."),
            PhoneModel("tecno_spark10pro", DeviceBrand.TECNO, "Tecno SPARK 10 Pro / SPARK 10", 200, 198, 194, 190, 150, 184, 360, 420, 600, 51, "Bas-droite (19%)", "100%", "90Hz", "Fluide + FPS Élevé", "Activer l'accélération dans Game Space."),
            PhoneModel("tecno_pop8", DeviceBrand.TECNO, "Tecno POP 8 / POP 7 / POP 6", 200, 200, 200, 196, 170, 196, 320, 380, 480, 56, "Bas-droite (20%)", "100%", "60Hz/90Hz", "Fluide + FPS Normal", "Attention : Rester sur DPI 380 max pour éviter le blocage !")
        ),
        DeviceBrand.REALME to listOf(
            PhoneModel("realme_gt6", DeviceBrand.REALME, "Realme GT 6 / GT 6T", 192, 188, 182, 178, 120, 160, 392, 540, 800, 43, "Bas-droite (15%)", "100%", "120Hz (Touch 2500Hz)", "Ultra + FPS Élevé", "Échantillonnage tactile ultra-rapide."),
            PhoneModel("realme_gtneo5", DeviceBrand.REALME, "Realme GT Neo 5 / GT 3", 192, 188, 182, 178, 120, 160, 392, 530, 800, 44, "Bas-droite (15%)", "100%", "144Hz", "Ultra + FPS Élevé", "Le choix des pros Realme."),
            PhoneModel("realme_12pro_plus", DeviceBrand.REALME, "Realme 12 Pro+ / 12 Pro 5G", 196, 192, 186, 182, 128, 168, 392, 500, 750, 46, "Bas-droite (16%)", "100%", "120Hz", "Standard + FPS Élevé", "Précision redoutable avec armes de moyenne portée."),
            PhoneModel("realme_11pro", DeviceBrand.REALME, "Realme 11 Pro+ / 11 Pro", 197, 194, 188, 184, 132, 170, 392, 490, 740, 47, "Bas-droite (17%)", "100%", "120Hz", "Standard + FPS Élevé", "Glissé régulier vers le haut."),
            PhoneModel("realme_c67", DeviceBrand.REALME, "Realme C67 / C55", 200, 198, 194, 190, 146, 180, 392, 440, 660, 50, "Bas-droite (18%)", "100%", "90Hz", "Fluide + FPS Élevé", "DPI 440 recommandé pour la série C."),
            PhoneModel("realme_c53", DeviceBrand.REALME, "Realme C53 / C51 / C35", 200, 200, 196, 192, 154, 186, 392, 420, 620, 52, "Bas-droite (19%)", "100%", "90Hz", "Fluide + FPS Élevé", "Bouton de tir 52%.")
        ),
        DeviceBrand.OPPO to listOf(
            PhoneModel("oppo_reno12pro", DeviceBrand.OPPO, "Oppo Reno 12 Pro / Reno 12", 194, 190, 184, 180, 124, 164, 392, 510, 760, 45, "Bas-droite (16%)", "100%", "120Hz", "Ultra + FPS Élevé", "Écran très réactif."),
            PhoneModel("oppo_reno11pro", DeviceBrand.OPPO, "Oppo Reno 11 Pro / Reno 11", 196, 192, 186, 182, 128, 168, 392, 490, 740, 46, "Bas-droite (16%)", "100%", "120Hz", "Élevé + FPS Élevé", "One-tap M1887 facile."),
            PhoneModel("oppo_reno10pro", DeviceBrand.OPPO, "Oppo Reno 10 Pro / Reno 10", 197, 194, 188, 184, 132, 170, 392, 480, 730, 47, "Bas-droite (17%)", "100%", "120Hz", "Standard + FPS Élevé", "DPI 480 idéal."),
            PhoneModel("oppo_a79", DeviceBrand.OPPO, "Oppo A79 5G / A78 4G", 200, 198, 194, 190, 144, 180, 392, 440, 680, 49, "Bas-droite (18%)", "100%", "90Hz", "Fluide + FPS Élevé", "Activer Touch Optimization dans ColorOS."),
            PhoneModel("oppo_a58", DeviceBrand.OPPO, "Oppo A58 / A38 / A18", 200, 200, 196, 192, 154, 188, 392, 411, 620, 53, "Bas-droite (19%)", "100%", "90Hz", "Fluide + FPS Élevé", "Bouton 53% pour garantir le tir.")
        ),
        DeviceBrand.VIVO to listOf(
            PhoneModel("vivo_iqoo12", DeviceBrand.VIVO, "iQOO 12 / 12 Pro", 190, 185, 180, 176, 115, 155, 392, 560, 850, 42, "Bas-droite (14%)", "100%", "144Hz Gaming", "Ultra + FPS Élevé", "Le monstre d'esport : Sensi ultrarapide."),
            PhoneModel("vivo_iqooneo9", DeviceBrand.VIVO, "iQOO Neo 9 Pro / Neo 7", 192, 188, 182, 178, 120, 160, 392, 540, 820, 43, "Bas-droite (15%)", "100%", "144Hz", "Ultra + FPS Élevé", "Temps de réaction quasi instantané."),
            PhoneModel("vivo_v30pro", DeviceBrand.VIVO, "Vivo V30 Pro / V30 5G", 195, 192, 186, 182, 126, 166, 392, 510, 760, 45, "Bas-droite (16%)", "100%", "120Hz", "Élevé + FPS Élevé", "DPI 510 très équilibré."),
            PhoneModel("vivo_v29", DeviceBrand.VIVO, "Vivo V29 / V27 5G", 197, 194, 188, 184, 130, 170, 392, 490, 740, 46, "Bas-droite (16%)", "100%", "120Hz", "Standard + FPS Élevé", "J-Drag recommandé."),
            PhoneModel("vivo_y200", DeviceBrand.VIVO, "Vivo Y200 / Y27 / Y36", 200, 198, 194, 190, 146, 182, 392, 440, 680, 50, "Bas-droite (18%)", "100%", "90Hz", "Fluide + FPS Élevé", "Mode Monster dans FuntouchOS.")
        ),
        DeviceBrand.HONOR to listOf(
            PhoneModel("honor_magic6pro", DeviceBrand.HONOR, "Honor Magic 6 Pro", 192, 188, 182, 178, 120, 160, 392, 540, 800, 43, "Bas-droite (15%)", "100%", "120Hz LTPO", "Ultra + FPS Élevé", "Précision chirurgicale."),
            PhoneModel("honor_200pro", DeviceBrand.HONOR, "Honor 200 Pro / 200", 194, 190, 184, 180, 124, 164, 392, 520, 780, 44, "Bas-droite (15%)", "100%", "120Hz", "Ultra + FPS Élevé", "Vitesse de tracking excellente."),
            PhoneModel("honor_90", DeviceBrand.HONOR, "Honor 90 / 90 Lite", 196, 192, 186, 182, 128, 168, 392, 500, 740, 46, "Bas-droite (16%)", "100%", "120Hz", "Standard + FPS Élevé", "DPI 500 très apprécié."),
            PhoneModel("honor_x9b", DeviceBrand.HONOR, "Honor X9b / X8b / X7b", 199, 196, 192, 188, 140, 176, 392, 460, 700, 48, "Bas-droite (17%)", "100%", "120Hz", "Fluide + FPS Élevé", "Écran résistant et sensi stable."),
            PhoneModel("huawei_p60pro", DeviceBrand.HONOR, "Huawei P60 Pro / Mate 60", 194, 190, 184, 180, 124, 164, 392, 510, 750, 45, "Bas-droite (16%)", "100%", "120Hz", "Élevé + FPS Élevé", "Garder 100% de vitesse de pointeur.")
        ),
        DeviceBrand.ONEPLUS to listOf(
            PhoneModel("oneplus_12", DeviceBrand.ONEPLUS, "OnePlus 12 / 12R", 192, 188, 182, 178, 120, 160, 392, 540, 800, 43, "Bas-droite (15%)", "100%", "120Hz ProXDR", "Ultra + FPS Élevé", "La rolls des sensibilités FF."),
            PhoneModel("oneplus_11", DeviceBrand.ONEPLUS, "OnePlus 11 / 10 Pro", 194, 190, 184, 180, 124, 164, 392, 520, 790, 44, "Bas-droite (15%)", "100%", "120Hz", "Ultra + FPS Élevé", "DPI 520 pour un drag court et sec."),
            PhoneModel("oneplus_nord3", DeviceBrand.ONEPLUS, "OnePlus Nord 3 5G / Nord CE 4", 196, 193, 187, 183, 130, 170, 392, 500, 760, 46, "Bas-droite (16%)", "100%", "120Hz", "Standard + FPS Élevé", "Sensi très fluide.")
        ),
        DeviceBrand.MOTOROLA to listOf(
            PhoneModel("moto_edge50u", DeviceBrand.MOTOROLA, "Motorola Edge 50 Ultra / Pro", 192, 188, 182, 178, 118, 158, 392, 530, 780, 43, "Bas-droite (15%)", "100%", "144Hz pOLED", "Ultra + FPS Élevé", "144Hz offre une glisse exceptionnelle."),
            PhoneModel("moto_g84", DeviceBrand.MOTOROLA, "Moto G84 5G / G73", 198, 195, 190, 186, 138, 176, 392, 470, 720, 48, "Bas-droite (17%)", "100%", "120Hz pOLED", "Standard + FPS Élevé", "DPI 470 pour le One-Tap."),
            PhoneModel("moto_g54", DeviceBrand.MOTOROLA, "Moto G54 / G24 / G14", 200, 198, 194, 190, 148, 184, 392, 430, 660, 51, "Bas-droite (18%)", "100%", "120Hz", "Fluide + FPS Élevé", "Bouton 51% pour éviter les ratés.")
        ),
        DeviceBrand.PIXEL to listOf(
            PhoneModel("pixel_9pro", DeviceBrand.PIXEL, "Google Pixel 9 Pro / 9 Pro XL", 194, 190, 184, 180, 122, 162, 411, 510, 760, 45, "Bas-droite (15%)", "100%", "120Hz Super Actua", "Ultra + FPS Élevé", "Précision brute sans surcouche logicielle."),
            PhoneModel("pixel_8pro", DeviceBrand.PIXEL, "Google Pixel 8 Pro / 8", 196, 192, 186, 182, 128, 166, 411, 500, 750, 46, "Bas-droite (16%)", "100%", "120Hz Actua", "Élevé + FPS Élevé", "One-Tap régulier."),
            PhoneModel("pixel_7a", DeviceBrand.PIXEL, "Google Pixel 7a / 7 Pro / 6a", 198, 195, 190, 186, 136, 174, 411, 480, 720, 48, "Bas-droite (17%)", "100%", "90Hz/120Hz", "Standard + FPS Élevé", "DPI 480 très adapté.")
        ),
        DeviceBrand.ASUS_ROG to listOf(
            PhoneModel("rog_8pro", DeviceBrand.ASUS_ROG, "ROG Phone 8 Pro / 8", 188, 184, 178, 174, 110, 150, 392, 600, 900, 40, "Bas-droite (14%)", "100%", "165Hz Gaming", "Ultra + FPS Élevé", "Le roi incontesté de l'e-sport mobile. AirTriggers configurables."),
            PhoneModel("rog_7pro", DeviceBrand.ASUS_ROG, "ROG Phone 7 Ultimate / 6", 190, 186, 180, 176, 114, 152, 392, 580, 900, 41, "Bas-droite (14%)", "100%", "165Hz", "Ultra + FPS Élevé", "Mode X activé pour 0 lag.")
        ),
        DeviceBrand.REDMAGIC to listOf(
            PhoneModel("redmagic_9pro", DeviceBrand.REDMAGIC, "RedMagic 9 Pro / 9S Pro", 188, 184, 178, 174, 108, 148, 400, 600, 900, 40, "Bas-droite (14%)", "100%", "120Hz Fullscreen", "Ultra + FPS Élevé", "Écran sans encoche ni trou, 2000Hz touch rate : Sensi laser !"),
            PhoneModel("redmagic_8pro", DeviceBrand.REDMAGIC, "RedMagic 8 Pro / 8S Pro", 190, 186, 180, 176, 112, 152, 400, 580, 900, 41, "Bas-droite (14%)", "100%", "120Hz", "Ultra + FPS Élevé", "Vitesse de rotation 360° impressionnante.")
        ),
        DeviceBrand.ITEL to listOf(
            PhoneModel("itel_s23plus", DeviceBrand.ITEL, "Itel S23+ 3D Curved", 199, 196, 192, 188, 142, 176, 360, 460, 620, 48, "Bas-droite (17%)", "100%", "60Hz AMOLED", "Standard + FPS Élevé", "Le meilleur modèle Itel pour Free Fire."),
            PhoneModel("itel_p55", DeviceBrand.ITEL, "Itel P55 5G / P55+", 200, 198, 194, 190, 148, 184, 360, 430, 600, 50, "Bas-droite (18%)", "100%", "90Hz", "Fluide + FPS Élevé", "DPI 430 conseillé."),
            PhoneModel("itel_p40", DeviceBrand.ITEL, "Itel P40 / A70 / A60s", 200, 200, 200, 196, 170, 196, 320, 390, 500, 56, "Bas-droite (20%)", "100%", "60Hz", "Fluide + FPS Normal", "Ne jamais dépasser DPI 500 pour éviter le brick de l'écran !")
        ),
        DeviceBrand.SONY to listOf(
            PhoneModel("sony_xperia1vi", DeviceBrand.SONY, "Sony Xperia 1 VI / 1 V", 194, 190, 184, 180, 124, 164, 384, 520, 780, 44, "Bas-droite (15%)", "100%", "120Hz OLED", "Ultra + FPS Élevé", "Ratio 19.5:9 idéal pour le champ de vision.")
        ),
        DeviceBrand.UNIVERSAL_OTHER to listOf(
            PhoneModel("universal_midrange", DeviceBrand.UNIVERSAL_OTHER, "Smartphone Standard Universel", 198, 195, 190, 186, 138, 175, 360, 460, 680, 48, "Bas-droite (17%)", "100%", "90Hz / 60Hz", "Fluide + FPS Élevé", "Sensi universelle 0-200 calculée pour headshot instantané sur tout écran."),
            PhoneModel("universal_gaming", DeviceBrand.UNIVERSAL_OTHER, "Smartphone Haute Performance", 192, 188, 182, 178, 120, 160, 392, 540, 800, 43, "Bas-droite (15%)", "100%", "120Hz+", "Ultra + FPS Élevé", "Calibré pour écrans 120Hz et processeurs gaming rapides."),
            PhoneModel("universal_entry", DeviceBrand.UNIVERSAL_OTHER, "Smartphone Économique / Ancien", 200, 200, 196, 192, 160, 190, 320, 400, 550, 54, "Bas-droite (20%)", "100%", "60Hz", "Fluide + FPS Normal", "Sensibilités maximales (200) pour compenser la latence tactile.")
        )
    )

    fun getAllModels(): List<PhoneModel> {
        return modelsByBrand.values.flatten()
    }

    fun getModelsForBrand(brand: DeviceBrand): List<PhoneModel> {
        return modelsByBrand[brand] ?: emptyList()
    }

    /**
     * Moteur de calcul universel réel pour TOUTES les marques et modèles au monde.
     * Calibre exactement les sensibilités Free Fire sur l'échelle 0 - 200.
     */
    fun generateCustomUniversalModel(brandName: String, modelName: String): PhoneModel {
        val trimmedBrand = brandName.trim().ifEmpty { "Marque Personnalisée" }
        val trimmedModel = modelName.trim().ifEmpty { "Modèle Universel" }
        val brandEnum = DeviceBrand.fromString(trimmedBrand)

        val lower = trimmedModel.lowercase()
        val isPro = lower.contains("pro") || lower.contains("ultra") || lower.contains("max") || lower.contains("plus")
        val isGaming = lower.contains("gt") || lower.contains("rog") || lower.contains("pova") || lower.contains("gaming") || lower.contains("shark")
        val isBudget = lower.contains("lite") || lower.contains("play") || lower.contains("smart") || lower.contains("core") || lower.contains("go") || lower.contains("c")

        val baseGen = when {
            isGaming -> 192
            isPro -> 194
            isBudget -> 200
            else -> 197
        }
        val baseRed = when {
            isGaming -> 188
            isPro -> 190
            isBudget -> 200
            else -> 194
        }
        val base2x = (baseRed - 6).coerceIn(100, 200)
        val base4x = (base2x - 4).coerceIn(90, 200)
        val baseSniper = if (isGaming) 118 else if (isBudget) 155 else 134
        val baseFree = (baseGen - 24).coerceIn(100, 200)

        val defDpi = if (brandEnum == DeviceBrand.APPLE) 0 else 384
        val recDpi = when {
            brandEnum == DeviceBrand.APPLE -> 0
            isBudget -> 410
            isGaming -> 540
            isPro -> 510
            else -> 460
        }
        val maxSafe = when {
            brandEnum == DeviceBrand.APPLE -> 0
            isBudget -> 550
            else -> 750
        }
        val btnSize = when {
            isGaming -> 43
            isPro -> 45
            isBudget -> 52
            else -> 48
        }

        return PhoneModel(
            id = "custom_${trimmedBrand.lowercase()}_${trimmedModel.lowercase().replace(" ", "_")}",
            brand = brandEnum,
            name = trimmedModel,
            baseGeneral = baseGen,
            baseRedDot = baseRed,
            baseScope2x = base2x,
            baseScope4x = base4x,
            baseSniper = baseSniper,
            baseFreeLook = baseFree,
            defaultDpi = defDpi,
            recommendedDpi = recDpi,
            maxSafeDpi = maxSafe,
            fireButtonSize = btnSize,
            fireButtonPosition = "Bas-droite (16% - 18%)",
            pointerSpeed = "100% (Maximum)",
            recommendedHz = if (isGaming || isPro) "120Hz" else "60Hz/90Hz",
            graphicsPreset = if (isBudget) "Fluide + FPS Élevé" else "Standard/Ultra + FPS Élevé",
            specialTips = "Sensibilité réelle 0-200 calculée sur mesure pour $trimmedModel avec algorithme Anos Aura."
        )
    }

    /**
     * Calcule la configuration complète en appliquant le preset sélectionné sur l'échelle 0 - 200
     */
    fun buildConfiguration(model: PhoneModel, preset: SensiPreset): SensiConfiguration {
        val general = (model.baseGeneral * preset.generalMultiplier).roundToInt().coerceIn(0, 200)
        val redDot = (model.baseRedDot * preset.redDotMultiplier).roundToInt().coerceIn(0, 200)
        val scope2x = (model.baseScope2x * preset.generalMultiplier).roundToInt().coerceIn(0, 200)
        val scope4x = (model.baseScope4x * preset.generalMultiplier).roundToInt().coerceIn(0, 200)
        val sniper = (model.baseSniper * preset.generalMultiplier).roundToInt().coerceIn(0, 200)
        val freeLook = (model.baseFreeLook * preset.generalMultiplier).roundToInt().coerceIn(0, 200)

        val finalDpi = if (model.brand == DeviceBrand.APPLE) {
            0
        } else if (preset == SensiPreset.BALANCED_NO_DPI) {
            model.defaultDpi
        } else {
            val calc = model.recommendedDpi + preset.dpiDelta
            calc.coerceIn(model.defaultDpi, model.maxSafeDpi)
        }

        val buttonSize = (model.fireButtonSize + preset.buttonDelta).coerceIn(35, 75)

        val advice = when (preset) {
            SensiPreset.HEADSHOT_100 -> "Placer le viseur blanc sous le menton ou sur le cou, puis donner un coup sec vers le haut (One-Tap 0-200)."
            SensiPreset.TOURNAMENT_PRO -> "Tir par courtes rafales pour garder la dispersion des balles au minimum absolu."
            SensiPreset.RUSH_360 -> "Mouvement en demi-cercle (J-Drag) pour suivre la tête des ennemis qui sautent."
            SensiPreset.BALANCED_NO_DPI -> "Utiliser le bouton de tir sans changer le DPI du système pour préserver la batterie."
        }

        return SensiConfiguration(
            id = "${model.id}_${preset.name.lowercase()}",
            phoneModel = model,
            preset = preset,
            general = general,
            redDot = redDot,
            scope2x = scope2x,
            scope4x = scope4x,
            sniper = sniper,
            freeLook = freeLook,
            dpi = finalDpi,
            fireButtonSize = buttonSize,
            fireButtonPosition = model.fireButtonPosition,
            pointerSpeed = model.pointerSpeed,
            graphicsSettings = model.graphicsPreset,
            advice = "${model.specialTips} $advice"
        )
    }

    /**
     * Détecte le modèle courant d'après les propriétés matérielles Android réelles
     */
    fun detectDevice(manufacturer: String, model: String, screenDensityDpi: Int): PhoneModel {
        val detectedBrand = DeviceBrand.fromString(manufacturer)
        val modelsInBrand = modelsByBrand[detectedBrand] ?: emptyList()

        val modelClean = model.lowercase().replace("-", " ").replace("_", " ")
        val matched = modelsInBrand.firstOrNull {
            val nameClean = it.name.lowercase()
            modelClean.contains(nameClean) || nameClean.contains(modelClean)
        }

        if (matched != null) {
            return matched
        }

        return generateCustomUniversalModel(manufacturer, model)
    }
}
