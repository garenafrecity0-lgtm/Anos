package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AnosAuraTopBar
import com.example.ui.screens.GuidesScreen
import com.example.ui.screens.HudCalibrationScreen
import com.example.ui.screens.SavedSensisScreen
import com.example.ui.screens.SensiGeneratorScreen
import com.example.ui.screens.WeaponsScreen
import com.example.ui.theme.AnosAuraTheme
import com.example.ui.theme.AuraBackground
import com.example.ui.theme.AuraBorder
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraRed
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.TextMutedGray
import com.example.ui.viewmodel.AnosAuraViewModel

enum class AnosAuraTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    GENERATOR("Sensi 0-200", Icons.Filled.FlashOn, Icons.Outlined.FlashOn, "tab_generator"),
    HUD("Bouton & HUD", Icons.Filled.RadioButtonChecked, Icons.Outlined.RadioButtonUnchecked, "tab_hud"),
    WEAPONS("Armes FF", Icons.Filled.LocalFireDepartment, Icons.Outlined.LocalFireDepartment, "tab_weapons"),
    GUIDES("Guides Pro", Icons.AutoMirrored.Filled.MenuBook, Icons.AutoMirrored.Outlined.MenuBook, "tab_guides"),
    SAVED("Mes Sensis", Icons.Filled.Star, Icons.Outlined.Star, "tab_saved")
}

class MainActivity : ComponentActivity() {
    private val viewModel: AnosAuraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnosAuraTheme {
                val context = LocalContext.current
                val toastMessage by viewModel.toastMessage.collectAsState()
                val selectedTab by viewModel.selectedTab.collectAsState()

                LaunchedEffect(toastMessage) {
                    toastMessage?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        viewModel.clearToastMessage()
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = AuraBackground,
                    topBar = {
                        AnosAuraTopBar(viewModel = viewModel)
                    },
                    bottomBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(AuraBackground)
                                .windowInsetsPadding(WindowInsets.navigationBars)
                                .padding(horizontal = 6.dp, vertical = 4.dp)
                        ) {
                            NavigationBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(20.dp))
                                    .border(1.dp, AuraBorder, RoundedCornerShape(20.dp)),
                                containerColor = AuraSurface,
                                tonalElevation = 4.dp
                            ) {
                                AnosAuraTab.entries.forEachIndexed { index, tab ->
                                    val isSelected = selectedTab == index
                                    NavigationBarItem(
                                        selected = isSelected,
                                        onClick = { viewModel.selectTab(index) },
                                        icon = {
                                            Icon(
                                                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                                contentDescription = tab.title,
                                                tint = if (isSelected) AuraRed else TextMutedGray
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = tab.title,
                                                fontSize = 9.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                color = if (isSelected) AuraGold else TextMutedGray,
                                                maxLines = 1
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            indicatorColor = AuraRed.copy(alpha = 0.15f)
                                        ),
                                        modifier = Modifier.testTag(tab.testTag)
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Crossfade(
                            targetState = selectedTab,
                            label = "tab_navigation"
                        ) { tabIndex ->
                            when (tabIndex) {
                                0 -> SensiGeneratorScreen(viewModel = viewModel)
                                1 -> HudCalibrationScreen(viewModel = viewModel)
                                2 -> WeaponsScreen(viewModel = viewModel)
                                3 -> GuidesScreen(viewModel = viewModel)
                                4 -> SavedSensisScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
