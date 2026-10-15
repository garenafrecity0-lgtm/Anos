package com.example.ui.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.generator.FreeFireDatabase
import com.example.data.generator.FreeFireGuidesData
import com.example.data.model.DeviceBrand
import com.example.data.model.FreeFireWeaponGuide
import com.example.data.model.HeadshotTutorial
import com.example.data.model.PhoneModel
import com.example.data.model.SavedSensi
import com.example.data.model.SensiConfiguration
import com.example.data.model.SensiPreset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AnosAuraViewModel(application: Application) : AndroidViewModel(application) {

    // Navigation Tab (0: Sensi FF 0-200, 1: Bouton & HUD, 2: Armes FF, 3: Guides Pro, 4: Mes Sensis)
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    // Brand and Model Selection
    private val _selectedBrand = MutableStateFlow<DeviceBrand>(DeviceBrand.SAMSUNG)
    val selectedBrand: StateFlow<DeviceBrand> = _selectedBrand.asStateFlow()

    val deviceManufacturer: String = Build.MANUFACTURER.replaceFirstChar { it.uppercase() }
    val deviceModelName: String = Build.MODEL

    val allTutorials: List<HeadshotTutorial> = FreeFireGuidesData.tutorials
    val allWeaponGuides: List<FreeFireWeaponGuide> = FreeFireGuidesData.weaponGuides

    private val _selectedModel = MutableStateFlow<PhoneModel>(
        FreeFireDatabase.modelsByBrand[DeviceBrand.SAMSUNG]?.first()
            ?: FreeFireDatabase.getAllModels().first()
    )
    val selectedModel: StateFlow<PhoneModel> = _selectedModel.asStateFlow()

    private val _selectedPreset = MutableStateFlow<SensiPreset>(SensiPreset.HEADSHOT_100)
    val selectedPreset: StateFlow<SensiPreset> = _selectedPreset.asStateFlow()

    private val _searchModelQuery = MutableStateFlow("")
    val searchModelQuery: StateFlow<String> = _searchModelQuery.asStateFlow()

    // Custom Overrides (clamped between 0 and 200)
    private val _customGeneral = MutableStateFlow<Int?>(null)
    private val _customRedDot = MutableStateFlow<Int?>(null)
    private val _customScope2x = MutableStateFlow<Int?>(null)
    private val _customScope4x = MutableStateFlow<Int?>(null)
    private val _customSniper = MutableStateFlow<Int?>(null)
    private val _customFreeLook = MutableStateFlow<Int?>(null)
    private val _customButtonSize = MutableStateFlow<Int?>(null)
    private val _customDpi = MutableStateFlow<Int?>(null)

    // Active Configuration
    val currentConfig: StateFlow<SensiConfiguration> = combine(
        _selectedModel,
        _selectedPreset,
        _customGeneral,
        _customRedDot,
        _customScope2x,
        _customScope4x,
        _customSniper,
        _customFreeLook,
        _customButtonSize,
        _customDpi
    ) { params ->
        val model = params[0] as PhoneModel
        val preset = params[1] as SensiPreset
        val cGen = params[2] as? Int
        val cRed = params[3] as? Int
        val c2x = params[4] as? Int
        val c4x = params[5] as? Int
        val cSnip = params[6] as? Int
        val cFree = params[7] as? Int
        val cBtn = params[8] as? Int
        val cDpi = params[9] as? Int

        val baseConfig = FreeFireDatabase.buildConfiguration(model, preset)
        val hasCustomOverrides = cGen != null || cRed != null || c2x != null || c4x != null ||
                cSnip != null || cFree != null || cBtn != null || cDpi != null

        baseConfig.copy(
            general = (cGen ?: baseConfig.general).coerceIn(0, 200),
            redDot = (cRed ?: baseConfig.redDot).coerceIn(0, 200),
            scope2x = (c2x ?: baseConfig.scope2x).coerceIn(0, 200),
            scope4x = (c4x ?: baseConfig.scope4x).coerceIn(0, 200),
            sniper = (cSnip ?: baseConfig.sniper).coerceIn(0, 200),
            freeLook = (cFree ?: baseConfig.freeLook).coerceIn(0, 200),
            fireButtonSize = (cBtn ?: baseConfig.fireButtonSize).coerceIn(30, 80),
            dpi = cDpi ?: baseConfig.dpi,
            isCustom = hasCustomOverrides
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        FreeFireDatabase.buildConfiguration(
            FreeFireDatabase.modelsByBrand[DeviceBrand.SAMSUNG]?.first()
                ?: FreeFireDatabase.getAllModels().first(),
            SensiPreset.HEADSHOT_100
        )
    )

    // Saved Favorites List
    private val _savedSensis = MutableStateFlow<List<SavedSensi>>(
        listOf(
            SavedSensi(
                id = "preset_default_tournoi",
                title = "Configuration Tournoi Clash Squad",
                configuration = FreeFireDatabase.buildConfiguration(
                    FreeFireDatabase.getAllModels().first(),
                    SensiPreset.TOURNAMENT_PRO
                ),
                notes = "Ultra stable 0-200 avec MP40 et Desert Eagle"
            )
        )
    )
    val savedSensis: StateFlow<List<SavedSensi>> = _savedSensis.asStateFlow()

    // Custom universal model creation dialog state
    private val _showCustomModelDialog = MutableStateFlow(false)
    val showCustomModelDialog: StateFlow<Boolean> = _showCustomModelDialog.asStateFlow()

    // Selected tutorial dialog / sheet
    private val _selectedTutorial = MutableStateFlow<HeadshotTutorial?>(null)
    val selectedTutorial: StateFlow<HeadshotTutorial?> = _selectedTutorial.asStateFlow()

    // Global toast notifications
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    init {
        // Auto-détection réelle du matériel Android de l'utilisateur
        detectAndApplyUserDevice(silent = true)
    }

    fun selectTab(index: Int) {
        _selectedTab.value = index
    }

    fun selectBrand(brand: DeviceBrand) {
        _selectedBrand.value = brand
        val firstInBrand = FreeFireDatabase.getModelsForBrand(brand).firstOrNull()
        if (firstInBrand != null) {
            _selectedModel.value = firstInBrand
            resetCustomOverrides()
        }
    }

    fun setSearchModelQuery(query: String) {
        _searchModelQuery.value = query
    }

    fun setModelSearchQuery(query: String) {
        setSearchModelQuery(query)
    }

    fun selectModel(model: PhoneModel) {
        _selectedModel.value = model
        _selectedBrand.value = model.brand
        resetCustomOverrides()
    }

    fun selectPreset(preset: SensiPreset) {
        _selectedPreset.value = preset
        resetCustomOverrides()
    }

    // Setters avec validation de la plage officielle 0 à 200
    fun setCustomGeneral(value: Int) { _customGeneral.value = value.coerceIn(0, 200) }
    fun setCustomRedDot(value: Int) { _customRedDot.value = value.coerceIn(0, 200) }
    fun setCustomScope2x(value: Int) { _customScope2x.value = value.coerceIn(0, 200) }
    fun setCustomScope4x(value: Int) { _customScope4x.value = value.coerceIn(0, 200) }
    fun setCustomSniper(value: Int) { _customSniper.value = value.coerceIn(0, 200) }
    fun setCustomFreeLook(value: Int) { _customFreeLook.value = value.coerceIn(0, 200) }
    fun setCustomButtonSize(value: Int) { _customButtonSize.value = value.coerceIn(30, 80) }
    fun setCustomDpi(value: Int) { _customDpi.value = value.coerceIn(100, 1000) }

    fun resetCustomOverrides() {
        _customGeneral.value = null
        _customRedDot.value = null
        _customScope2x.value = null
        _customScope4x.value = null
        _customSniper.value = null
        _customFreeLook.value = null
        _customButtonSize.value = null
        _customDpi.value = null
    }

    fun detectAndApplyUserDevice(silent: Boolean = false) {
        val detected = FreeFireDatabase.detectDevice(
            manufacturer = Build.MANUFACTURER,
            model = Build.MODEL,
            screenDensityDpi = 420
        )
        _selectedBrand.value = detected.brand
        _selectedModel.value = detected
        resetCustomOverrides()
        if (!silent) {
            _toastMessage.value = "📱 Appareil réel détecté : ${detected.brand.displayName} - ${detected.name}"
        }
    }

    fun createAndApplyUniversalCustomModel(brandName: String, modelName: String) {
        if (brandName.isBlank() && modelName.isBlank()) {
            _toastMessage.value = "Veuillez entrer une marque ou un modèle."
            return
        }
        val customModel = FreeFireDatabase.generateCustomUniversalModel(brandName, modelName)
        _selectedBrand.value = customModel.brand
        _selectedModel.value = customModel
        resetCustomOverrides()
        _showCustomModelDialog.value = false
        _toastMessage.value = "✨ Sensi réelle (0-200) calculée pour ${customModel.name} !"
    }

    fun openCustomModelDialog() {
        _showCustomModelDialog.value = true
    }

    fun closeCustomModelDialog() {
        _showCustomModelDialog.value = false
    }

    fun copyConfigurationToClipboard(context: Context) {
        val config = currentConfig.value
        val text = config.toFormattedShareText()
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Anos Aura Sensi Free Fire", text)
        clipboard.setPrimaryClip(clip)
        _toastMessage.value = "📋 Sensibilité réelle (0-200) copiée avec succès !"
    }

    fun saveCurrentConfiguration(title: String, notes: String = "") {
        val config = currentConfig.value
        val displayTitle = title.ifBlank { "${config.phoneModel.name} - ${config.preset.badge}" }
        val newSaved = SavedSensi(
            id = "saved_${System.currentTimeMillis()}",
            title = displayTitle,
            configuration = config,
            notes = notes
        )
        _savedSensis.value = listOf(newSaved) + _savedSensis.value
        _toastMessage.value = "⭐ Configuration sauvegardée dans 'Mes Sensis' !"
    }

    fun deleteSavedConfiguration(id: String) {
        _savedSensis.value = _savedSensis.value.filterNot { it.id == id }
        _toastMessage.value = "🗑️ Configuration supprimée"
    }

    fun loadSavedConfiguration(saved: SavedSensi) {
        _selectedModel.value = saved.configuration.phoneModel
        _selectedBrand.value = saved.configuration.phoneModel.brand
        _selectedPreset.value = saved.configuration.preset
        _customGeneral.value = saved.configuration.general
        _customRedDot.value = saved.configuration.redDot
        _customScope2x.value = saved.configuration.scope2x
        _customScope4x.value = saved.configuration.scope4x
        _customSniper.value = saved.configuration.sniper
        _customFreeLook.value = saved.configuration.freeLook
        _customButtonSize.value = saved.configuration.fireButtonSize
        _customDpi.value = saved.configuration.dpi
        _selectedTab.value = 0
        _toastMessage.value = "⚡ Configuration '${saved.title}' chargée !"
    }

    fun openTutorial(tutorial: HeadshotTutorial) {
        _selectedTutorial.value = tutorial
    }

    fun closeTutorial() {
        _selectedTutorial.value = null
    }

    fun applyWeaponSensitivity(weapon: FreeFireWeaponGuide) {
        setCustomGeneral(weapon.idealSensiGeneral)
        setCustomRedDot(weapon.idealRedDot)
        setCustomScope2x(weapon.idealScope2x)
        setCustomScope4x(weapon.idealScope4x)
        setCustomSniper(weapon.idealSniper)
        setCustomButtonSize(weapon.idealButtonSize)
        _selectedTab.value = 0
        _toastMessage.value = "🎯 Sensi ${weapon.name} appliquée au générateur !"
    }

    fun copyWeaponText(context: Context, weapon: FreeFireWeaponGuide) {
        val text = """
            🔥 ANOS AURA - CONFIGURATION ARME FF (0-200) 🔥
            🔫 Arme : ${weapon.emoji} ${weapon.name} (${weapon.category})
            
            🎯 SENSIBILITÉS CALIBRÉES :
            • Général : ${weapon.idealSensiGeneral} / 200
            • Point Rouge : ${weapon.idealRedDot} / 200
            • Mire 2X : ${weapon.idealScope2x} / 200
            • Mire 4X : ${weapon.idealScope4x} / 200
            • Mire Sniper : ${weapon.idealSniper} / 200
            • Bouton de tir : ${weapon.idealButtonSize}%
            
            ⚡ Technique : ${weapon.buttonDragStyle}
            💡 Astuce : ${weapon.tips}
            
            🚀 Calibré avec Anos Aura (Toutes marques & modèles)
        """.trimIndent()

        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Anos Aura Weapon Sensi", text)
        clipboard.setPrimaryClip(clip)
        _toastMessage.value = "📋 Sensi pour ${weapon.name} copiée !"
    }

    fun openWhatsAppContact(context: Context) {
        openWhatsAppVipContact(context)
    }

    fun openWhatsAppVipContact(context: Context) {
        try {
            val phone = "23407071776576"
            val message = "Bonjour Anos Aura, je souhaite calibrer ma sensibilité Free Fire pour mon modèle !"
            val encodedMessage = Uri.encode(message)
            val uri = Uri.parse("https://wa.me/$phone?text=$encodedMessage")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            _toastMessage.value = "WhatsApp non installé sur l'appareil."
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}
