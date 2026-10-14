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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Terminal
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PythonTopBar
import com.example.ui.screens.CheatSheetScreen
import com.example.ui.screens.CoursesListScreen
import com.example.ui.screens.CyberLessonDetailScreen
import com.example.ui.screens.CyberSecurityScreen
import com.example.ui.screens.LessonDetailScreen
import com.example.ui.screens.PlaygroundScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.theme.AnosPyTheme
import com.example.ui.theme.IdeBackground
import com.example.ui.theme.IdeBorder
import com.example.ui.theme.IdeSurface
import com.example.ui.theme.PythonGold
import com.example.ui.theme.TextMutedGray
import com.example.ui.viewmodel.PythonAppViewModel

enum class AnosPyNavTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    COURSES("Python", Icons.Filled.Code, Icons.Outlined.Code, "tab_courses"),
    CYBER("Cyber 🛡️", Icons.Filled.Security, Icons.Outlined.Security, "tab_cyber"),
    QUIZZES("Quizz", Icons.Filled.Quiz, Icons.Outlined.Quiz, "tab_quizzes"),
    PLAYGROUND("Console", Icons.Filled.Terminal, Icons.Outlined.Terminal, "tab_playground"),
    PROFILE("Profil", Icons.Filled.Person, Icons.Outlined.Person, "tab_profile")
}

class MainActivity : ComponentActivity() {
    private val viewModel: PythonAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnosPyTheme {
                val context = LocalContext.current
                val toastMessage by viewModel.toastMessage.collectAsState()

                LaunchedEffect(toastMessage) {
                    toastMessage?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        viewModel.clearToastMessage()
                    }
                }

                MainAnosPyApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAnosPyApp(viewModel: PythonAppViewModel) {
    val context = LocalContext.current
    var selectedTab by rememberSaveable { mutableStateOf(AnosPyNavTab.COURSES) }
    val progress by viewModel.userProgress.collectAsState()
    val isQuizActive by viewModel.isQuizActive.collectAsState()
    val selectedLesson by viewModel.selectedLesson.collectAsState()
    val selectedCyberLesson by viewModel.selectedCyberLesson.collectAsState()

    val isFullScreen = isQuizActive || selectedLesson != null || selectedCyberLesson != null

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(IdeBackground),
        containerColor = IdeBackground,
        topBar = {
            PythonTopBar(
                progress = progress,
                onWhatsAppClick = { viewModel.openWhatsAppContact(context) }
            )
        },
        bottomBar = {
            // Only show bottom navigation when not viewing a lesson or active quiz
            if (!isFullScreen) {
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
                            .background(IdeSurface)
                            .border(1.dp, IdeBorder, RoundedCornerShape(20.dp)),
                        containerColor = IdeSurface,
                        tonalElevation = 0.dp
                    ) {
                        AnosPyNavTab.entries.forEach { tab ->
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
                                        fontSize = 9.5.sp,
                                        maxLines = 1,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = PythonGold,
                                    selectedTextColor = PythonGold,
                                    unselectedIconColor = TextMutedGray,
                                    unselectedTextColor = TextMutedGray,
                                    indicatorColor = PythonGold.copy(alpha = 0.15f)
                                ),
                                modifier = Modifier.testTag(tab.testTag)
                            )
                        }
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
            when {
                isQuizActive -> {
                    QuizScreen(viewModel = viewModel)
                }
                selectedLesson != null -> {
                    LessonDetailScreen(
                        lesson = selectedLesson!!,
                        viewModel = viewModel
                    )
                }
                selectedCyberLesson != null -> {
                    CyberLessonDetailScreen(
                        lesson = selectedCyberLesson!!,
                        viewModel = viewModel
                    )
                }
                else -> {
                    Crossfade(targetState = selectedTab, label = "tabScreenTransition") { tab ->
                        when (tab) {
                            AnosPyNavTab.COURSES -> CoursesListScreen(viewModel = viewModel)
                            AnosPyNavTab.CYBER -> CyberSecurityScreen(viewModel = viewModel)
                            AnosPyNavTab.QUIZZES -> QuizScreen(viewModel = viewModel)
                            AnosPyNavTab.PLAYGROUND -> PlaygroundScreen(viewModel = viewModel)
                            AnosPyNavTab.PROFILE -> ProfileScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
