package com.example.ui.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.db.SavedPresetEntity
import com.example.data.generator.SensitivityEngine
import com.example.data.model.AuthConstants
import com.example.data.model.DeviceCatalog
import com.example.data.model.DeviceSpec
import com.example.data.model.Playstyle
import com.example.data.model.SensitivityConfig
import com.example.data.model.UserRole
import com.example.ui.screens.SmoothingProfile
import com.example.ui.theme.AppThemeMode
import com.example.util.DeviceDetector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SensiViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs: SharedPreferences = application.getSharedPreferences("sensifire_auth_prefs", Context.MODE_PRIVATE)

    private val db = AppDatabase.getDatabase(application)
    private val presetDao = db.presetDao()

    val detectedInfo = DeviceDetector.detect(application)

    // Dynamic Theme selection state (6 high-end gaming themes)
    private val _currentAppTheme = MutableStateFlow(
        try {
            AppThemeMode.valueOf(prefs.getString("selected_app_theme", AppThemeMode.NEON_INFERNO.name) ?: AppThemeMode.NEON_INFERNO.name)
        } catch (_: Exception) {
            AppThemeMode.NEON_INFERNO
        }
    )
    val currentAppTheme: StateFlow<AppThemeMode> = _currentAppTheme.asStateFlow()

    fun setAppTheme(theme: AppThemeMode) {
        _currentAppTheme.value = theme
        prefs.edit().putString("selected_app_theme", theme.name).apply()
        _copySuccessMessage.value = "Thème appliqué : ${theme.icon} ${theme.title}"
    }

    // Touch Smoothing & Gesture Accelerator API State
    private val _isTouchSmoothingEnabled = MutableStateFlow(
        prefs.getBoolean("touch_smoothing_enabled", true)
    )
    val isTouchSmoothingEnabled: StateFlow<Boolean> = _isTouchSmoothingEnabled.asStateFlow()

    private val _selectedSmoothingProfile = MutableStateFlow(
        try {
            SmoothingProfile.valueOf(prefs.getString("touch_smoothing_profile", SmoothingProfile.BALANCED_ONE_TAP.name) ?: SmoothingProfile.BALANCED_ONE_TAP.name)
        } catch (_: Exception) {
            SmoothingProfile.BALANCED_ONE_TAP
        }
    )
    val selectedSmoothingProfile: StateFlow<SmoothingProfile> = _selectedSmoothingProfile.asStateFlow()

    fun setTouchSmoothing(enabled: Boolean) {
        _isTouchSmoothingEnabled.value = enabled
        prefs.edit().putBoolean("touch_smoothing_enabled", enabled).apply()
        _copySuccessMessage.value = if (enabled) "✨ Lissage Tactile & Anti-Jitter Activé !" else "Lissage tactile désactivé."
    }

    fun setSmoothingProfile(profile: SmoothingProfile) {
        _selectedSmoothingProfile.value = profile
        prefs.edit().putString("touch_smoothing_profile", profile.name).apply()
        _copySuccessMessage.value = "Profil Tactile : ${profile.title}"
    }

    // Authentication State
    private val _isAuthenticated = MutableStateFlow(prefs.getBoolean("is_logged_in", false))
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _userRole = MutableStateFlow(
        try {
            UserRole.valueOf(prefs.getString("user_role", UserRole.CLIENT.name) ?: UserRole.CLIENT.name)
        } catch (_: Exception) {
            UserRole.CLIENT
        }
    )
    val userRole: StateFlow<UserRole> = _userRole.asStateFlow()

    private val _selectedBrand = MutableStateFlow(
        DeviceCatalog.BRANDS.firstOrNull { it.contains(detectedInfo.matchedSpec.brand, ignoreCase = true) }
            ?: DeviceCatalog.BRANDS.first()
    )
    val selectedBrand: StateFlow<String> = _selectedBrand.asStateFlow()

    private val _selectedModel = MutableStateFlow(detectedInfo.matchedSpec)
    val selectedModel: StateFlow<DeviceSpec> = _selectedModel.asStateFlow()

    private val _selectedPlaystyle = MutableStateFlow(Playstyle.PRECISION_HEADSHOT)
    val selectedPlaystyle: StateFlow<Playstyle> = _selectedPlaystyle.asStateFlow()

    private val _currentConfig = MutableStateFlow(
        SensitivityEngine.calculate(detectedInfo.matchedSpec, Playstyle.PRECISION_HEADSHOT)
    )
    val currentConfig: StateFlow<SensitivityConfig> = _currentConfig.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val savedPresets: StateFlow<List<SavedPresetEntity>> = presetDao.getAllPresets()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _copySuccessMessage = MutableStateFlow<String?>(null)
    val copySuccessMessage: StateFlow<String?> = _copySuccessMessage.asStateFlow()

    private var variationCounter = 0

    private val _useDpi = MutableStateFlow(true)
    val useDpi: StateFlow<Boolean> = _useDpi.asStateFlow()

    // Authentication Functions
    private fun isAdminKey(key: String): Boolean {
        val clean = key.trim()
        return clean.equals("Zax11", ignoreCase = true) ||
                clean.equals("Anos", ignoreCase = true) ||
                clean == AuthConstants.ADMIN_SECRET_KEY
    }

    fun authenticate(key: String): Boolean {
        val cleanKey = key.trim()
        if (cleanKey.isEmpty()) return false

        val determinedRole = when {
            isAdminKey(cleanKey) -> UserRole.ADMIN
            cleanKey.startsWith("VIP", ignoreCase = true) || cleanKey.contains("VIP", ignoreCase = true) -> UserRole.VIP
            else -> UserRole.CLIENT
        }

        _userRole.value = determinedRole
        _isAuthenticated.value = true

        prefs.edit()
            .putBoolean("is_logged_in", true)
            .putString("user_role", determinedRole.name)
            .putString("access_key", cleanKey)
            .apply()

        if (determinedRole == UserRole.CLIENT) {
            resetToAutoDetected()
        }

        return true
    }

    fun upgradeToVip(vipKey: String): Boolean {
        val cleanKey = vipKey.trim()
        val isSuccessful = isAdminKey(cleanKey) ||
                cleanKey.startsWith("VIP", ignoreCase = true) ||
                cleanKey.contains("VIP", ignoreCase = true)

        if (isSuccessful) {
            val newRole = if (isAdminKey(cleanKey)) UserRole.ADMIN else UserRole.VIP
            _userRole.value = newRole
            prefs.edit()
                .putString("user_role", newRole.name)
                .putString("access_key", cleanKey)
                .apply()
            _copySuccessMessage.value = "Félicitations ! Vous êtes désormais en ${newRole.title} ⭐"
            return true
        }
        return false
    }

    fun logout() {
        _isAuthenticated.value = false
        _userRole.value = UserRole.CLIENT
        prefs.edit().clear().apply()
        resetToAutoDetected()
    }

    // Device & Model Selection
    fun selectBrand(brand: String) {
        if (!_userRole.value.isVipOrAdmin) {
            _copySuccessMessage.value = "🔒 Mode VIP Requis : Débloquez toutes les marques et modèles mondiaux !"
            return
        }
        _selectedBrand.value = brand
        val models = DeviceCatalog.getModelsForBrand(brand)
        val defaultModel = models.firstOrNull() ?: detectedInfo.matchedSpec
        _selectedModel.value = defaultModel
        if (defaultModel.isApple) {
            _useDpi.value = false
        }
        recalculate()
    }

    fun selectModel(model: DeviceSpec) {
        if (!_userRole.value.isVipOrAdmin) {
            _copySuccessMessage.value = "🔒 Mode VIP Requis : Débloquez tous les modèles mondiaux !"
            return
        }
        _selectedModel.value = model
        if (model.isApple) {
            _useDpi.value = false
        }
        recalculate()
    }

    fun setUseDpi(enabled: Boolean) {
        if (_selectedModel.value.isApple && enabled) {
            _copySuccessMessage.value = "🍎 Les iPhones n'utilisent pas de DPI (spécifique à Android)."
            _useDpi.value = false
            return
        }
        _useDpi.value = enabled
        recalculate()
        _copySuccessMessage.value = if (enabled) "⚙️ Mode DPI Optimisé activé !" else "🛡️ Mode Sans DPI (DPI d'origine) activé !"
    }

    fun selectPlaystyle(playstyle: Playstyle) {
        _selectedPlaystyle.value = playstyle
        recalculate()
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun resetToAutoDetected() {
        val detectedSpec = detectedInfo.matchedSpec
        val matchingBrand = DeviceCatalog.BRANDS.firstOrNull {
            it.contains(detectedSpec.brand, ignoreCase = true)
        } ?: "Autre / Modèle Personnalisé"

        _selectedBrand.value = matchingBrand
        _selectedModel.value = detectedSpec
        _selectedPlaystyle.value = Playstyle.PRECISION_HEADSHOT
        if (detectedSpec.isApple) {
            _useDpi.value = false
        }
        recalculate()
    }

    fun recalculate() {
        _currentConfig.value = SensitivityEngine.calculate(
            _selectedModel.value,
            _selectedPlaystyle.value,
            _useDpi.value,
            variationCounter
        )
    }

    fun setMaxSensi200() {
        _selectedPlaystyle.value = Playstyle.MAX_SENSI_200
        val baseConfig = SensitivityEngine.calculate(
            _selectedModel.value,
            Playstyle.MAX_SENSI_200,
            _useDpi.value,
            variationCounter
        )
        _currentConfig.value = baseConfig.copy(
            general = 200,
            redDot = 200,
            scope2x = 198,
            scope4x = 195,
            sniper = 60,
            freeLook = 68
        )
        _copySuccessMessage.value = "🔥 Mode Boost 200 Activé ! Viseur: 200 • Sniper: 60 • Regard Libre: 68"
    }

    fun generateNewSensitivity() {
        variationCounter++
        val oldDpi = _currentConfig.value.dpi
        val newConfig = SensitivityEngine.calculate(
            _selectedModel.value,
            _selectedPlaystyle.value,
            _useDpi.value,
            variationCounter
        )
        _currentConfig.value = newConfig
        val dpiInfo = if (newConfig.isAppleDevice) "Réglages iOS Glisse 120" else if (!newConfig.useDpi) "Sans DPI (Stock: ${newConfig.stockDpi})" else "Nouveau DPI: ${newConfig.dpi} (précédent: $oldDpi)"
        _copySuccessMessage.value = "🎲 Sensi Calibrée ! Général: ${newConfig.general} • Point Rouge: ${newConfig.redDot} • $dpiInfo"
    }

    fun updateGeneral(value: Int) {
        _currentConfig.value = _currentConfig.value.copy(general = value.coerceIn(0, 200))
    }

    fun updateRedDot(value: Int) {
        _currentConfig.value = _currentConfig.value.copy(redDot = value.coerceIn(0, 200))
    }

    fun updateScope2x(value: Int) {
        _currentConfig.value = _currentConfig.value.copy(scope2x = value.coerceIn(0, 200))
    }

    fun updateScope4x(value: Int) {
        _currentConfig.value = _currentConfig.value.copy(scope4x = value.coerceIn(0, 200))
    }

    fun updateSniper(value: Int) {
        _currentConfig.value = _currentConfig.value.copy(sniper = value.coerceIn(0, 200))
    }

    fun updateFreeLook(value: Int) {
        _currentConfig.value = _currentConfig.value.copy(freeLook = value.coerceIn(0, 200))
    }

    fun updateDpi(value: Int) {
        _currentConfig.value = _currentConfig.value.copy(dpi = value.coerceIn(320, 1200))
    }

    fun updateFireButtonSize(value: Int) {
        _currentConfig.value = _currentConfig.value.copy(fireButtonSize = value.coerceIn(25, 80))
    }

    fun saveCurrentPreset(customTitle: String? = null) {
        viewModelScope.launch {
            val title = if (!customTitle.isNullOrBlank()) {
                customTitle.trim()
            } else {
                "${_selectedModel.value.model} - ${_selectedPlaystyle.value.title}"
            }
            val config = _currentConfig.value
            val entity = SavedPresetEntity(
                title = title,
                brand = _selectedModel.value.brand,
                model = _selectedModel.value.model,
                playstyleKey = _selectedPlaystyle.value.name,
                general = config.general,
                redDot = config.redDot,
                scope2x = config.scope2x,
                scope4x = config.scope4x,
                sniper = config.sniper,
                freeLook = config.freeLook,
                dpi = config.dpi,
                fireButtonSize = config.fireButtonSize
            )
            presetDao.insertPreset(entity)
            _copySuccessMessage.value = "Preset sauvegardé dans vos favoris !"
        }
    }

    fun deletePreset(preset: SavedPresetEntity) {
        viewModelScope.launch {
            presetDao.deletePreset(preset)
        }
    }

    fun loadPreset(preset: SavedPresetEntity) {
        val playstyle = try {
            Playstyle.valueOf(preset.playstyleKey)
        } catch (_: Exception) {
            Playstyle.PRECISION_HEADSHOT
        }
        _selectedPlaystyle.value = playstyle
        _currentConfig.value = _currentConfig.value.copy(
            general = preset.general,
            redDot = preset.redDot,
            scope2x = preset.scope2x,
            scope4x = preset.scope4x,
            sniper = preset.sniper,
            freeLook = preset.freeLook,
            dpi = preset.dpi,
            fireButtonSize = preset.fireButtonSize
        )
        _copySuccessMessage.value = "Preset chargé : ${preset.title}"
    }

    fun clearToastMessage() {
        _copySuccessMessage.value = null
    }

    fun copyConfigurationToClipboard(context: Context) {
        val config = _currentConfig.value
        val model = _selectedModel.value
        val style = _selectedPlaystyle.value

        val text = buildString {
            appendLine("🔥 [ANOS SENSI V2 - CONFIGURATION OFFICIELLE] 🔥")
            appendLine("📱 Appareil : ${model.brand} ${model.model} (${model.refreshRateHz}Hz)")
            appendLine("🎮 Style : ${style.title} (${style.badge})")
            appendLine("━━━━━━━━━━━━━━━━━━━")
            appendLine("🎯 Général : ${config.general} / 200")
            appendLine("🔴 Point Rouge : ${config.redDot} / 200")
            appendLine("🔍 Mire 2X : ${config.scope2x} / 200")
            appendLine("🔭 Mire 4X : ${config.scope4x} / 200")
            appendLine("🎯 Viseur AWM / Sniper : ${config.sniper} / 200")
            appendLine("👁️ Regard Libre : ${config.freeLook} / 200")
            appendLine("━━━━━━━━━━━━━━━━━━━")
            if (config.isAppleDevice) {
                appendLine("🍎 iOS : Glisse 120 / AssistiveTouch 100%")
            } else if (!config.useDpi) {
                appendLine("🛡️ Mode Sans DPI (Stock: ${config.stockDpi})")
            } else {
                appendLine("⚙️ DPI Recommandé : ${config.dpi} (D'origine: ${config.stockDpi})")
            }
            appendLine("🔘 Bouton de Tir : ${config.fireButtonSize}%")
            appendLine("📍 Placement : ${config.fireButtonPosition}")
            appendLine("💥 Technique Drag : ${config.dragTechnique}")
            appendLine("📈 Taux Headshot Estimé : ${config.estimatedHeadshotRate}%")
            appendLine("━━━━━━━━━━━━━━━━━━━")
            appendLine("⚡ Calibré avec Anos Sensi V2")
        }

        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Sensi Free Fire", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Sensi copiée dans le presse-papier ! ✅", Toast.LENGTH_SHORT).show()
    }
}
