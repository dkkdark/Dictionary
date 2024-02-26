package com.kseniabl.dictionarywithexamples.presentation.word_creaton

import android.graphics.Shader
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kseniabl.dictionarywithexamples.ui.theme.DictionaryWithExamplesTheme
import com.kseniabl.dictionarywithexamples.ui.theme.Pink40
import com.kseniabl.dictionarywithexamples.ui.theme.Pink80

@Composable
fun CreateWord() {
    var text by remember {
        mutableStateOf("")
    }

    Column {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(shape = RoundedCornerShape(12.dp), color = Pink80),
            value = text,
            onValueChange = { text = it },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black
            ),
            cursorBrush = Brush.linearGradient(listOf(MaterialTheme.colorScheme.onBackground, MaterialTheme.colorScheme.onBackground))
        ) { innerTextField ->
            Box(
                modifier = Modifier.padding(12.dp),
            ) {
                if (text.isEmpty()) {
                    Text(
                        text = "Слово",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black
                        )
                    )
                }
                innerTextField()
            }
        }
    }
}

@Preview
@Composable
fun CreateWordPreview() {
    DictionaryWithExamplesTheme(darkTheme = true) {
        CreateWord()
    }
}