package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.outlined.TouchApp
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.DpiGuideScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.FireButtonSimulatorScreen
import com.example.ui.screens.GeneratorScreen
import com.example.ui.screens.TouchSmoothingScreen
import com.example.ui.theme.AppThemeMode
import com.example.ui.theme.FireCrimson
import com.example.ui.theme.FireGold
import com.example.ui.theme.FireOrange
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.SensiViewModel

enum class SensiNavTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    GENERATOR("Générateur", Icons.Filled.Tune, Icons.Outlined.Tune, "tab_generator"),
    SMOOTHING("Lissage Tactile", Icons.Filled.TouchApp, Icons.Outlined.TouchApp, "tab_smoothing"),
    SIMULATOR("Bouton & Drag", Icons.Filled.SportsEsports, Icons.Outlined.SportsEsports, "tab_simulator"),
    FAVORITES("Favoris", Icons.Filled.Bookmark, Icons.Outlined.BookmarkBorder, "tab_favorites"),
    GUIDE("Tuto DPI", Icons.AutoMirrored.Filled.MenuBook, Icons.AutoMirrored.Outlined.MenuBook, "tab_guide")
}

class MainActivity : ComponentActivity() {
    private val viewModel: SensiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val currentAppTheme by viewModel.currentAppTheme.collectAsState()

            MyApplicationTheme(appThemeMode = currentAppTheme) {
                val context = LocalContext.current
                val toastMessage by viewModel.copySuccessMessage.collectAsState()
                val isAuthenticated by viewModel.isAuthenticated.collectAsState()

                LaunchedEffect(toastMessage) {
                    toastMessage?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        viewModel.clearToastMessage()
                    }
                }

                if (!isAuthenticated) {
                    AuthScreen(viewModel = viewModel)
                } else {
                    MainSensiApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainSensiApp(viewModel: SensiViewModel) {
    var selectedTab by rememberSaveable { mutableStateOf(SensiNavTab.GENERATOR) }
    val userRole by viewModel.userRole.collectAsState()
    val currentTheme by viewModel.currentAppTheme.collectAsState()
    var showLogoutConfirm by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.outline)
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Text(
                        text = "Anos Sensi V2",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black
                    )

                    // Role Badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                when (userRole) {
                                    UserRole.ADMIN -> FireCrimson.copy(alpha = 0.25f)
                                    UserRole.VIP -> FireGold.copy(alpha = 0.25f)
                                    UserRole.CLIENT -> MaterialTheme.colorScheme.surfaceVariant
                                }
                            )
                            .border(
                                1.dp,
                                when (userRole) {
                                    UserRole.ADMIN -> FireCrimson
                                    UserRole.VIP -> FireGold
                                    UserRole.CLIENT -> MaterialTheme.colorScheme.outline
                                },
                                RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = userRole.badge,
                            color = when (userRole) {
                                UserRole.ADMIN -> FireCrimson
                                UserRole.VIP -> FireGold
                                UserRole.CLIENT -> TextSecondary
                            },
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Theme picker button
                    IconButton(
                        onClick = { showThemeDialog = true },
                        modifier = Modifier.size(32.dp).testTag("btn_theme_action")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Palette,
                            contentDescription = "Changer de Thème",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(
                        onClick = { showLogoutConfirm = true },
                        modifier = Modifier.size(32.dp).testTag("btn_logout_action")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Changer de clé / Déconnexion",
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp)),
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 0.dp
                ) {
                    SensiNavTab.entries.forEach { tab ->
                        val isSelected = selectedTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedTab = tab },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                    contentDescription = tab.title,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = tab.title,
                                    fontSize = 9.sp,
                                    maxLines = 1,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
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
            Crossfade(targetState = selectedTab, label = "tabScreenTransition") { tab ->
                when (tab) {
                    SensiNavTab.GENERATOR -> GeneratorScreen(
                        viewModel = viewModel,
                        onNavigateToSimulator = { selectedTab = SensiNavTab.SIMULATOR }
                    )
                    SensiNavTab.SMOOTHING -> TouchSmoothingScreen(
                        viewModel = viewModel
                    )
                    SensiNavTab.SIMULATOR -> FireButtonSimulatorScreen(
                        viewModel = viewModel,
                        onBack = { selectedTab = SensiNavTab.GENERATOR }
                    )
                    SensiNavTab.FAVORITES -> FavoritesScreen(
                        viewModel = viewModel,
                        onNavigateToGenerator = { selectedTab = SensiNavTab.GENERATOR }
                    )
                    SensiNavTab.GUIDE -> DpiGuideScreen()
                }
            }
        }
    }

    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            containerColor = MaterialTheme.colorScheme.surface,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Palette, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Text("Thèmes de l'Application", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Choisissez l'ambiance visuelle d'Anos Sensi V2 :",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )

                    AppThemeMode.entries.forEach { theme ->
                        val isSelected = currentTheme == theme
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant)
                                .border(
                                    1.dp,
                                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    viewModel.setAppTheme(theme)
                                    showThemeDialog = false
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(theme.icon, fontSize = 20.sp)
                                Column {
                                    Text(theme.title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(theme.subtitle, color = TextSecondary, fontSize = 10.sp)
                                }
                            }
                            if (isSelected) {
                                Icon(Icons.Default.Check, contentDescription = "Sélectionné", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showThemeDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Fermer")
                }
            }
        )
    }

    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            containerColor = MaterialTheme.colorScheme.surface,
            title = {
                Text("Changer de Clé d'Accès", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "Voulez-vous vous déconnecter pour entrer une autre clé d'accès (VIP, Admin com.dts ou Client) ?",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutConfirm = false
                        viewModel.logout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FireOrange),
                    modifier = Modifier.testTag("btn_confirm_logout")
                ) {
                    Text("Se Déconnecter")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) {
                    Text("Annuler", color = TextMuted)
                }
            }
        )
    }
}
