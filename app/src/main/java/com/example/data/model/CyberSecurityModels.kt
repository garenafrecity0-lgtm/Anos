package com.example.data.model

import androidx.compose.ui.graphics.Color

enum class CyberLevel(
    val title: String,
    val badge: String,
    val description: String,
    val iconEmoji: String,
    val primaryColor: Color,
    val order: Int
) {
    FONDATIONS(
        title = "Niveau 1 : Fondations & Réseaux",
        badge = "FONDATIONS",
        description = "Modèle OSI, TCP/IP, ports, DNS, cryptographie symétrique/asymétrique et hash.",
        iconEmoji = "🌐",
        primaryColor = Color(0xFF00E5FF),
        order = 1
    ),
    WEB_OWASP(
        title = "Niveau 2 : Sécurité Web & OWASP",
        badge = "WEB OWASP",
        description = "Injections SQL, XSS, CSRF, IDOR, vol de cookies et sécurisation des applications.",
        iconEmoji = "🕷️",
        primaryColor = Color(0xFFFF5252),
        order = 2
    ),
    RECON_SCAN(
        title = "Niveau 3 : Hacking Éthique & Scan",
        badge = "RECO & SCAN",
        description = "OSINT, Google Dorks, cartographie réseau, scan Nmap et analyse Wireshark.",
        iconEmoji = "🎯",
        primaryColor = Color(0xFFFFD438),
        order = 3
    ),
    EXPLOITATION(
        title = "Niveau 4 : Exploitation & PrivEsc",
        badge = "EXPLOITATION",
        description = "CVEs, Reverse Shells, Netcat, élévation de privilèges Linux/Windows et brute-force.",
        iconEmoji = "⚡",
        primaryColor = Color(0xFFA855F7),
        order = 4
    ),
    DEFENSE_SOC(
        title = "Niveau 5 : Blue Team, SOC & Défense",
        badge = "BLUE TEAM",
        description = "Pare-feu, IDS/IPS, SIEM, détection d'intrusions, forensic et durcissement (hardening).",
        iconEmoji = "🛡️",
        primaryColor = Color(0xFF10B981),
        order = 5
    )
}

data class CyberLesson(
    val id: String,
    val number: Int,
    val title: String,
    val subtitle: String,
    val level: CyberLevel,
    val durationMinutes: Int,
    val xpReward: Int,
    val summary: String,
    val sections: List<LessonSection>,
    val quiz: List<QuizQuestion>
)
