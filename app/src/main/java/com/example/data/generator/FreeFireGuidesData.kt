package com.example.data.generator

import com.example.data.model.FreeFireWeaponGuide
import com.example.data.model.HeadshotTutorial

object FreeFireGuidesData {

    val tutorials: List<HeadshotTutorial> = listOf(
        HeadshotTutorial(
            id = "tuto_sensi_200",
            title = "Comprendre l'Échelle de Sensibilité 0 à 200",
            category = "Mise à Jour Free Fire",
            emoji = "⚡",
            durationMin = 2,
            summary = "Garena a étendu la sensibilité de 100 à 200 dans les mises à jour récentes pour offrir un contrôle ultra précis sur les écrans modernes.",
            detailedSteps = listOf(
                "Pourquoi le passage à 200 : Les smartphones actuels possèdent des écrans 90Hz, 120Hz et 144Hz avec des taux d'échantillonnage tactile très élevés (jusqu'à 2000Hz). L'ancienne échelle 0-100 bridait la réactivité.",
                "La plage One-Tap (185 - 200) : Pour les armes comme le M1887 et Desert Eagle, régler le Général et Point Rouge entre 190 et 200 garantit que le viseur monte instantanément vers la tête sans forcer.",
                "La plage Tournoi / SMG (170 - 188) : Pour les MP40, Thompson et UMP, une sensibilité autour de 180-188 stabilise le recul et évite que les balles ne s'envolent au-dessus du casque.",
                "Les mires de précision (110 - 150) : La mire Sniper ne doit JAMAIS être mise à 200, sinon le viseur glisse trop vite hors de la cible en plein zoom."
            ),
            proSecret = "Avec Anos Aura, toutes les sensibilités générées exploitent la pleine plage 0 - 200 pour coller 100% de têtes rouges."
        ),
        HeadshotTutorial(
            id = "tuto_j_drag",
            title = "La Technique Ultime du J-Drag (0-200)",
            category = "Mouvement & Tir",
            emoji = "↪️",
            durationMin = 3,
            summary = "La technique n°1 utilisée par les joueurs pros et streamers pour coller des têtes rouges à bout portant.",
            detailedSteps = listOf(
                "Position de départ : Ne visez pas directement le torse de l'ennemi. Gardez votre réticule blanc légèrement à côté de sa silhouette.",
                "Amorce du geste : Appuyez sur le bouton de tir et effectuez une très légère courbe vers le bas (comme la queue de la lettre J).",
                "Flick violent vers la tête : Remontez d'un coup sec et rapide vers le coin supérieur droit (ou gauche si l'ennemi se déplace à gauche).",
                "Relâchement instantané : Dès que la balle part, relâchez pour réinitialiser la dispersion des balles."
            ),
            proSecret = "Le J-Drag casse l'aim-lock (visée automatique) qui a tendance à rester bloqué sur le gilet pare-balles de l'adversaire."
        ),
        HeadshotTutorial(
            id = "tuto_white_line",
            title = "Le Secret du Réticule Blanc (White Crosshair)",
            category = "Visée & Précision",
            emoji = "⚪",
            durationMin = 2,
            summary = "Pourquoi tirer avec un viseur rouge vous fait rater vos One-Taps au Desert Eagle et M1887.",
            detailedSteps = listOf(
                "Le problème du viseur rouge : Quand le cercle devient rouge, l'assistance de visée s'enclenche et verrouille le torse.",
                "La règle d'or : Tirez quand le réticule est encore BLANC, au niveau de la ligne d'épaule ou du cou de l'adversaire.",
                "Le timing parfait : En courant ou sautant, déclenchez le drag shot à l'instant où votre viseur passe près de la tête sans s'être aimanté au corps.",
                "Résultat : La balle file directement dans la tête en infligeant les célèbres chiffres rouges (Red Numbers) !"
            ),
            proSecret = "Sur YouTube et TikTok, tous les créateurs de highlights utilisent cette méthode."
        ),
        HeadshotTutorial(
            id = "tuto_dpi_safe",
            title = "Guide du DPI sans Risque d'Écran Noir",
            category = "Optimisation Téléphone",
            emoji = "⚙️",
            durationMin = 4,
            summary = "Comment configurer la largeur minimale dans les options pour développeurs en toute sécurité.",
            detailedSteps = listOf(
                "Étape 1 : Allez dans Paramètres > À propos du téléphone > Tapez 7 fois d'affilée sur 'Numéro de build' pour déverrouiller le mode développeur.",
                "Étape 2 : Entrez dans 'Options pour développeurs' et faites défiler jusqu'à la section Tracé / Dessin > 'Largeur minimale' (Smallest width).",
                "Étape 3 : Notez OBLIGATOIREMENT votre valeur par défaut (souvent 360, 384 ou 392) avant d'y toucher !",
                "Étape 4 : Augmentez par paliers de +40 ou +50 maximum. N'appliquez jamais un DPI de 1000 d'un coup !",
                "Étape 5 : Consultez la valeur recommandée dans le générateur Anos Aura spécifique à votre marque."
            ),
            proSecret = "Sur Infinix, Tecno et téléphones d'entrée de gamme, ne dépassez jamais 600 DPI, sinon le système peut nécessiter une réinitialisation d'usine."
        ),
        HeadshotTutorial(
            id = "tuto_fire_button",
            title = "Taille & Emplacement Idéal du Bouton de Tir",
            category = "Configuration HUD",
            emoji = "🔴",
            durationMin = 3,
            summary = "Pourquoi la taille et la position de votre bouton de tir changent tout à votre taux de headshot.",
            detailedSteps = listOf(
                "La règle de l'espace de drag : Plus votre bouton de tir est placé BAS sur l'écran, plus vous avez de surface tactile pour glisser votre pouce vers le haut.",
                "La taille idéale : Entre 42% et 48% pour les écrans de plus de 6.5 pouces ; entre 48% et 54% pour les écrans plus petits.",
                "Attention aux boutons trop gros : Un bouton supérieur à 60% réduit la course du pouce et force le tir à s'arrêter sur le torse.",
                "Attention aux boutons trop petits : En dessous de 38%, vous risquez de rater le bouton en plein combat intense."
            ),
            proSecret = "Activez le bouton de tir gauche en 'Toujours' pour pouvoir poser vos murs de glace (Gloo Walls) instantanément !"
        ),
        HeadshotTutorial(
            id = "tuto_fps_boost",
            title = "Débloquer 90 FPS / 120 FPS Réels",
            category = "Fluidité & Réseau",
            emoji = "🚀",
            durationMin = 3,
            summary = "Comment supprimer le lag, les chutes de FPS et avoir un écran ultra rapide dans Free Fire.",
            detailedSteps = listOf(
                "Paramètres Free Fire : Allez dans Affichage > Graphismes : 'Fluide' ou 'Standard' > FPS Élevé : 'ÉLEVÉ' (High FPS).",
                "Paramètres d'affichage du téléphone : Forcez le taux de rafraîchissement à 120Hz ou 90Hz dans Paramètres > Écran.",
                "Effets visuels : Désactivez les ombres et les résolutions ultra qui font chauffer le processeur et provoquent du thermal throttling.",
                "Nettoyage : Fermez toutes les applications en arrière-plan avant de lancer Free Fire."
            ),
            proSecret = "La fluidité prime sur la beauté visuelle. À 90/120 FPS, vos tirs sont enregistrés deux fois plus vite par le serveur !"
        )
    )

    val weaponGuides: List<FreeFireWeaponGuide> = listOf(
        FreeFireWeaponGuide(
            id = "wp_m1887",
            name = "M1887 (Double Pompe)",
            category = "Fusil à Pompe One-Tap",
            emoji = "💥",
            idealSensiGeneral = 198,
            idealRedDot = 196,
            idealScope2x = 185,
            idealScope4x = 175,
            idealSniper = 120,
            idealButtonSize = 44,
            buttonDragStyle = "J-Drag explosif vers le haut",
            tips = "Tirez la première cartouche en sautant, visez le cou et glissez immédiatement en J vers le haut."
        ),
        FreeFireWeaponGuide(
            id = "wp_deagle",
            name = "Desert Eagle (L'Arme des Rois)",
            category = "Pistolet One-Tap",
            emoji = "🦅",
            idealSensiGeneral = 196,
            idealRedDot = 200,
            idealScope2x = 182,
            idealScope4x = 178,
            idealSniper = 125,
            idealButtonSize = 45,
            buttonDragStyle = "Flick vertical droit (Straight Drag)",
            tips = "Attendez une fraction de seconde que votre personnage s'arrête de courir avant d'appuyer pour une précision à 100%."
        ),
        FreeFireWeaponGuide(
            id = "wp_mp40",
            name = "MP40 / UMP / Thompson",
            category = "Mitraillette (SMG)",
            emoji = "⚡",
            idealSensiGeneral = 197,
            idealRedDot = 192,
            idealScope2x = 186,
            idealScope4x = 180,
            idealSniper = 120,
            idealButtonSize = 46,
            buttonDragStyle = "Glissé continu et stable vers le haut",
            tips = "Maintenez le bouton de tir enfoncé en l'accompagnant doucement vers le haut pendant les 5 premières balles."
        ),
        FreeFireWeaponGuide(
            id = "wp_woodpecker",
            name = "Woodpecker / SVD / SKS",
            category = "Fusil Tireur d'Élite",
            emoji = "🎯",
            idealSensiGeneral = 192,
            idealRedDot = 196,
            idealScope2x = 188,
            idealScope4x = 184,
            idealSniper = 125,
            idealButtonSize = 45,
            buttonDragStyle = "Drag sec et ultra court",
            tips = "Pénétration d'armure maximale : une seule balle dans la tête couche n'importe quel adversaire casque 3 !"
        ),
        FreeFireWeaponGuide(
            id = "wp_m1014",
            name = "M1014 (Fusil 6 Coups)",
            category = "Fusil à Pompe",
            emoji = "🔥",
            idealSensiGeneral = 200,
            idealRedDot = 198,
            idealScope2x = 180,
            idealScope4x = 175,
            idealSniper = 115,
            idealButtonSize = 42,
            buttonDragStyle = "Drag rapide au corps-à-corps",
            tips = "Arme dévastatrice avec puce d'amélioration : garder le bouton de tir à 42% pour un flick éclair."
        ),
        FreeFireWeaponGuide(
            id = "wp_ac80",
            name = "AC80 / Groza / SCAR",
            category = "Fusil d'Assaut (AR)",
            emoji = "⚔️",
            idealSensiGeneral = 194,
            idealRedDot = 194,
            idealScope2x = 188,
            idealScope4x = 182,
            idealSniper = 120,
            idealButtonSize = 46,
            buttonDragStyle = "Tir en rafales contrôlées",
            tips = "L'AC80 inflige des dégâts critiques sur la 2ème balle consécutive : viser la poitrine puis lever le doigt."
        ),
        FreeFireWeaponGuide(
            id = "wp_awm",
            name = "AWM / Kar98k / M82B",
            category = "Fusil de Sniper",
            emoji = "🔭",
            idealSensiGeneral = 188,
            idealRedDot = 180,
            idealScope2x = 175,
            idealScope4x = 170,
            idealSniper = 125,
            idealButtonSize = 48,
            buttonDragStyle = "Switch rapide tir + arme secondaire",
            tips = "Réglez la sensibilité Sniper entre 115 et 135 sur l'échelle 200 pour un tracking précis sans déraper."
        )
    )
}
