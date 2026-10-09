package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.IdeBorder
import com.example.ui.theme.IdeCodeBg
import com.example.ui.theme.IdeSurfaceVariant
import com.example.ui.theme.PythonBlue
import com.example.ui.theme.PythonGold
import com.example.ui.theme.SuccessEmerald
import com.example.ui.theme.SyntaxComment
import com.example.ui.theme.SyntaxDecorator
import com.example.ui.theme.SyntaxFunction
import com.example.ui.theme.SyntaxKeyword
import com.example.ui.theme.SyntaxNumber
import com.example.ui.theme.SyntaxString
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextWhite

@Composable
fun CodeBlockView(
    code: String,
    title: String = "script.py",
    expectedOutput: String? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(IdeCodeBg)
            .border(1.dp, IdeBorder, RoundedCornerShape(12.dp))
    ) {
        // Top window bar with terminal dots & copy button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(IdeSurfaceVariant)
                .padding(horizontal = 12.dp, vertical = 6.dp),
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
                Icon(
                    imageVector = Icons.Default.Terminal,
                    contentDescription = null,
                    tint = PythonGold,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = title,
                    color = TextMutedGray,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Python Code", code.trim())
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Code copié dans le presse-papier ! 📋", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copier le code",
                    tint = TextMutedGray,
                    modifier = Modifier.size(15.dp)
                )
            }
        }

        // Code Area with syntax coloration
        Box(modifier = Modifier.padding(14.dp)) {
            Text(
                text = highlightPythonSyntax(code.trim()),
                fontSize = 12.5.sp,
                fontFamily = FontFamily.Monospace,
                lineHeight = 18.sp
            )
        }

        // Expected output if provided
        expectedOutput?.let { output ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF07090D))
                    .border(1.dp, IdeBorder.copy(alpha = 0.5f))
                    .padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("❯", color = SuccessEmerald, fontWeight = FontWeight.Black, fontSize = 12.sp)
                    Text("Console Output :", color = SuccessEmerald, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = output.trim(),
                    color = TextWhite.copy(alpha = 0.85f),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.5.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

fun highlightPythonSyntax(rawCode: String) = buildAnnotatedString {
    val keywords = setOf(
        "def", "class", "return", "if", "elif", "else", "for", "while", "in", "not",
        "and", "or", "is", "import", "from", "as", "try", "except", "finally",
        "raise", "with", "yield", "async", "await", "lambda", "global", "nonlocal",
        "pass", "break", "continue", "True", "False", "None"
    )

    val lines = rawCode.lines()
    lines.forEachIndexed { lineIdx, line ->
        var i = 0
        while (i < line.length) {
            // Comments
            if (line[i] == '#') {
                withStyle(SpanStyle(color = SyntaxComment)) {
                    append(line.substring(i))
                }
                break
            }

            // Strings (single or double quotes)
            if (line[i] == '"' || line[i] == '\'') {
                val quote = line[i]
                val endQuote = line.indexOf(quote, i + 1)
                if (endQuote != -1) {
                    withStyle(SpanStyle(color = SyntaxString)) {
                        append(line.substring(i, endQuote + 1))
                    }
                    i = endQuote + 1
                    continue
                }
            }

            // Decorators (@property, @dataclass)
            if (line[i] == '@' && (i == 0 || line[i - 1].isWhitespace())) {
                val end = line.indexOfAny(charArrayOf(' ', '(', '\n'), i).let { if (it == -1) line.length else it }
                withStyle(SpanStyle(color = SyntaxDecorator, fontWeight = FontWeight.Bold)) {
                    append(line.substring(i, end))
                }
                i = end
                continue
            }

            // Identifiers / Words
            if (line[i].isLetter() || line[i] == '_') {
                val start = i
                while (i < line.length && (line[i].isLetterOrDigit() || line[i] == '_')) {
                    i++
                }
                val word = line.substring(start, i)
                when {
                    word in keywords -> {
                        withStyle(SpanStyle(color = SyntaxKeyword, fontWeight = FontWeight.Bold)) {
                            append(word)
                        }
                    }
                    word in listOf("print", "len", "range", "type", "int", "str", "float", "bool", "list", "dict", "set", "tuple", "min", "max", "sum", "sorted", "enumerate", "zip", "isinstance") -> {
                        withStyle(SpanStyle(color = SyntaxFunction)) {
                            append(word)
                        }
                    }
                    else -> {
                        withStyle(SpanStyle(color = TextWhite)) {
                            append(word)
                        }
                    }
                }
                continue
            }

            // Numbers
            if (line[i].isDigit()) {
                val start = i
                while (i < line.length && (line[i].isDigit() || line[i] == '.' || line[i] == '_')) {
                    i++
                }
                withStyle(SpanStyle(color = SyntaxNumber)) {
                    append(line.substring(start, i))
                }
                continue
            }

            // Default character
            withStyle(SpanStyle(color = TextMutedGray)) {
                append(line[i].toString())
            }
            i++
        }

        if (lineIdx < lines.size - 1) {
            append("\n")
        }
    }
}
