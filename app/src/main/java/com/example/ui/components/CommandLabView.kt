package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.generator.CommandExplanation
import com.example.data.generator.LabMission
import com.example.data.generator.TerminalLine
import com.example.data.generator.TerminalLineType
import com.example.ui.theme.IdeBackground
import com.example.ui.theme.IdeBorder
import com.example.ui.theme.IdeCodeBg
import com.example.ui.theme.IdeSurface
import com.example.ui.theme.IdeSurfaceVariant
import com.example.ui.theme.PythonBlue
import com.example.ui.theme.PythonGold
import com.example.ui.theme.SuccessEmerald
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.PythonAppViewModel

private val CyberNeonGreen = Color(0xFF00FF66)
private val CyberNeonCyan = Color(0xFF00E5FF)
private val CyberWarningOrange = Color(0xFFFF9100)
private val CyberAlertRed = Color(0xFFFF1744)

@Composable
fun CommandLabView(
    viewModel: PythonAppViewModel,
    modifier: Modifier = Modifier
) {
    val terminalLines by viewModel.commandLabLines.collectAsState()
    val inputText by viewModel.commandLabInput.collectAsState()
    val activeMission by viewModel.activeLabMission.collectAsState()
    val explanation by viewModel.lastCommandExplanation.collectAsState()
    val isExecuting by viewModel.isCommandExecuting.collectAsState()
    val listState = rememberLazyListState()

    // Auto-scroll terminal to bottom when new lines appear
    LaunchedEffect(terminalLines.size) {
        if (terminalLines.isNotEmpty()) {
            listState.animateScrollToItem(terminalLines.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Mission Banner (if active)
        AnimatedVisibility(visible = activeMission != null) {
            activeMission?.let { mission ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberNeonCyan.copy(alpha = 0.12f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CyberNeonCyan.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(CyberNeonCyan.copy(alpha = 0.25f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = mission.badge,
                                        color = CyberNeonCyan,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "+${mission.rewardXp} XP",
                                    color = PythonGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = mission.title,
                                color = TextWhite,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = mission.hint,
                                color = TextMutedGray,
                                fontSize = 11.sp
                            )
                        }

                        IconButton(onClick = { viewModel.clearLabMission() }) {
                            Icon(Icons.Default.Close, contentDescription = "Fermer", tint = TextMutedGray)
                        }
                    }
                }
            }
        }

        // Lab Missions Carousel
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "🎯 Missions Pratiques & Scénarios Lab :",
                    color = TextWhite,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${viewModel.labMissions.size} Labs",
                    color = PythonGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                viewModel.labMissions.forEach { mission ->
                    val isSelected = activeMission?.id == mission.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) CyberNeonCyan.copy(alpha = 0.2f) else IdeSurface)
                            .border(1.dp, if (isSelected) CyberNeonCyan else IdeBorder, RoundedCornerShape(12.dp))
                            .clickable { viewModel.selectLabMission(mission) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = mission.badge,
                                    color = if (isSelected) CyberNeonCyan else PythonGold,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "+${mission.rewardXp} XP",
                                    color = SuccessEmerald,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = mission.title,
                                color = TextWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // Quick Command Chips
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "⚡ Commandes Rapides (Cliquez pour exécuter) :",
                color = TextWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                viewModel.quickLabCommands.forEach { cmd ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(IdeCodeBg)
                            .border(1.dp, IdeBorder, RoundedCornerShape(8.dp))
                            .clickable { viewModel.executeLabCommand(cmd) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cmd,
                            color = CyberNeonGreen,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Interactive Terminal Box
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF090D14)),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CyberNeonGreen.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
        ) {
            Column {
                // Terminal Header Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F172A))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFFF5F56)))
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFFFBD2E)))
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF27C93F)))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "kali@anos-cyber-lab:~#",
                            color = CyberNeonGreen,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { viewModel.executeLabCommand("help") },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.HelpOutline, contentDescription = "Aide", tint = TextMutedGray, modifier = Modifier.size(16.dp))
                        }
                        IconButton(
                            onClick = { viewModel.clearTerminal() },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.DeleteSweep, contentDescription = "Effacer", tint = TextMutedGray, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                // Terminal Lines Output
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp, max = 340.dp)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(terminalLines) { line ->
                        TerminalLineItem(line = line)
                    }

                    if (isExecuting) {
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(12.dp),
                                    strokeWidth = 2.dp,
                                    color = CyberNeonGreen
                                )
                                Text(
                                    text = "Exécution de la commande en cours...",
                                    color = CyberNeonGreen,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.5.sp
                                )
                            }
                        }
                    }
                }

                // Interactive Command Prompt Input
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F172A))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "kali#",
                        color = CyberNeonGreen,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )

                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { viewModel.setCommandLabInput(it) },
                        placeholder = {
                            Text(
                                text = "Entrez une commande (ex: nmap, ps aux, strings)...",
                                color = TextMutedGray,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = TextWhite,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                        keyboardActions = KeyboardActions(onGo = { viewModel.executeLabCommand() }),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedBorderColor = CyberNeonGreen,
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = CyberNeonGreen
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("command_lab_input")
                    )

                    Button(
                        onClick = { viewModel.executeLabCommand() },
                        colors = ButtonDefaults.buttonColors(containerColor = CyberNeonGreen),
                        shape = RoundedCornerShape(8.dp),
                        enabled = !isExecuting,
                        modifier = Modifier.testTag("command_lab_run_btn")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Exécuter", tint = Color.Black, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("RUN", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }
        }

        // Command Explanation & Educational Card
        explanation?.let { exp ->
            Card(
                colors = CardDefaults.cardColors(containerColor = IdeSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PythonBlue.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(PythonBlue.copy(alpha = 0.2f))
                                .padding(6.dp)
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = PythonBlue, modifier = Modifier.size(18.dp))
                        }
                        Column {
                            Text(
                                text = "Décryptage : ${exp.commandName}",
                                color = TextWhite,
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = exp.shortSummary,
                                color = PythonBlue,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Text(
                        text = exp.detailedPurpose,
                        color = TextMutedGray,
                        fontSize = 11.5.sp,
                        lineHeight = 16.sp
                    )

                    if (exp.commonFlags.isNotEmpty()) {
                        Text(
                            text = "Paramètres et Options Clés :",
                            color = TextWhite,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            exp.commonFlags.forEach { (flag, desc) ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = flag,
                                        color = CyberNeonGreen,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.width(90.dp)
                                    )
                                    Text(
                                        text = desc,
                                        color = TextMutedGray,
                                        fontSize = 11.sp,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF2E1A1A))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "⚖️ Cadre Légal : ${exp.ethicsNotice}",
                            color = CyberAlertRed,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TerminalLineItem(line: TerminalLine) {
    val color = when (line.type) {
        TerminalLineType.PROMPT -> CyberNeonGreen
        TerminalLineType.STDOUT -> TextWhite
        TerminalLineType.SUCCESS -> Color(0xFF4ADE80)
        TerminalLineType.WARNING -> CyberWarningOrange
        TerminalLineType.ERROR -> CyberAlertRed
        TerminalLineType.HEADER -> CyberNeonCyan
        TerminalLineType.INFO -> Color(0xFF60A5FA)
    }

    Text(
        text = line.text,
        color = color,
        fontFamily = FontFamily.Monospace,
        fontSize = 11.sp,
        fontWeight = if (line.type == TerminalLineType.HEADER || line.type == TerminalLineType.PROMPT) FontWeight.Bold else FontWeight.Normal,
        lineHeight = 15.sp
    )
}
