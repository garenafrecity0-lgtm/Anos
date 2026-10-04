package com.example.data.model

enum class UserRole(
    val title: String,
    val badge: String,
    val description: String
) {
    CLIENT(
        title = "Mode Client Gratuit",
        badge = "CLIENT GRATUIT",
        description = "Accès à la sensibilité de votre appareil auto-détecté."
    ),
    VIP(
        title = "Mode VIP Permanent",
        badge = "VIP ⭐",
        description = "Lissage Tactile + Simulateur Drag + Tous modèles mondiaux débloqués."
    ),
    ADMIN(
        title = "Mode Administrateur",
        badge = "ADMIN 👑",
        description = "Accès maître total illimité à tous les modules."
    );

    val isVipOrAdmin: Boolean
        get() = this == VIP || this == ADMIN
}

object AuthConstants {
    const val ADMIN_SECRET_KEY = "Zax11"
    const val CLIENT_DEFAULT_KEY = "CLIENT-FREE-2025"
    const val VIP_KEY_PREFIX = "anos-vip-"
    
    // Sample default official VIP keys
    val OFFICIAL_VIP_KEYS = setOf(
        "anos-vip-2025",
        "anos-vip-pro",
        "anos-vip-master",
        "anos-vip-headshot",
        "anos-vip-fire",
        "anos-vip-777",
        "anos-vip-999",
        "anos-vip-sensi"
    )

    const val WHATSAPP_NUMBER = "+23407071776576"
    const val WHATSAPP_CHANNEL_URL = "https://whatsapp.com/channel/0029Vb8k9tlKWEKq1XAtKm0e"
    const val WHATSAPP_DIRECT_URL = "https://wa.me/23407071776576?text=Bonjour,%20je%20souhaite%20obtenir%20une%20cl%C3%A9%20d'acc%C3%A8s%20VIP%20(format%20anos-vip-xxx)%20pour%20Anos%20Sensi%20V2"
    const val WHATSAPP_URL = WHATSAPP_DIRECT_URL
    const val AUDIOMACK_SONG_URL = "https://audiomack.com/immortalbeing_7/song/tourner-dans-le-vide?share-user-id=155468919"

    /**
     * Strict verification rule:
     * - Admin: Strictly "Zax11"
     * - VIP: Strictly matches pattern "anos-vip-xxx"
     * - Client: "CLIENT-FREE-2025"
     */
    fun isValidAdminKey(key: String): Boolean {
        val clean = key.trim()
        return clean.equals(ADMIN_SECRET_KEY, ignoreCase = true)
    }

    fun isValidVipKey(key: String): Boolean {
        val clean = key.trim().lowercase()
        // Must strictly start with anos-vip- and have at least 3 alphanumeric chars after
        val regex = Regex("^anos-vip-[a-z0-9_-]{3,}$")
        return regex.matches(clean)
    }

    fun isValidClientKey(key: String): Boolean {
        val clean = key.trim()
        return clean.equals(CLIENT_DEFAULT_KEY, ignoreCase = true)
    }
}
