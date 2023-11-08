package com.kseniabl.dictionarywithexamples.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import com.kseniabl.dictionarywithexamples.R
import com.kseniabl.dictionarywithexamples.domain.model.ResultModel
import com.kseniabl.dictionarywithexamples.presentation.viewmodel.TextSelectionEvent
import com.kseniabl.dictionarywithexamples.presentation.viewmodel.TextSelectionStates
import com.kseniabl.dictionarywithexamples.presentation.viewmodel.TextSelectionViewModel
import com.kseniabl.dictionarywithexamples.ui.theme.DictionaryWithExamplesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TextSelectionActivity : ComponentActivity() {

    private val viewModel by viewModels<TextSelectionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val selectedText = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT) ?: ""
        Log.e("qqq", "selectedText $selectedText")
        viewModel.processEvents(TextSelectionEvent.GetInfoForWord(selectedText))

        setContent {
            DictionaryWithExamplesTheme {
                WindowContent(viewModel)
            }
        }
    }
}

@Composable
fun WindowContent(viewModel: TextSelectionViewModel) {
    val definition by viewModel.definition.collectAsState()
    val synonym by viewModel.synonym.collectAsState()
    val translation by viewModel.translation.collectAsState()

    Log.e("qqq", "definition $definition")
    Log.e("qqq", "synonym $synonym")
    Log.e("qqq", "translation $translation")

    LaunchedEffect(true) {
        viewModel.states.collect {
            when(it) {
                is TextSelectionStates.Error -> {
                    Log.e("qqq", "Error ${it.error}")
                }
                is TextSelectionStates.Loading -> {
                    Log.e("qqq", "Loading")
                }
                is TextSelectionStates.SuccessSaving -> {
                    Log.e("qqq", "Success")
                }
                is TextSelectionStates.NotCorrectDate -> {
                    Log.e("qqq", "NotCorrectDate")
                }
            }
        }
    }

    Card(
        Modifier.safeDrawingPadding(),
        shape = ShapeDefaults.Medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row() {
                Image(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.language_white),
                    contentDescription = "app's logo"
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Language Resolver",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Black
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Example",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Example definition. Smth smth smth smth",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Synonim",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Пример",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn() {
               items(3) {
                   WordsListElement()
               }
            }
        }
    }
}

@Composable
fun WordsListElement() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = ShapeDefaults.Small,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Пример списка",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                Icons.Default.Add,
                "add to list button",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
@Preview
fun PreviewTextSelection() {
    //WindowContent()
}