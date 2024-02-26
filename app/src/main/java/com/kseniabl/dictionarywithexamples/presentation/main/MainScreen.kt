package com.kseniabl.dictionarywithexamples.presentation.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kseniabl.dictionarywithexamples.R
import com.kseniabl.dictionarywithexamples.domain.model.WordListModel
import com.kseniabl.dictionarywithexamples.ui.theme.DictionaryWithExamplesTheme

@Composable
fun MainScreen(
    paddingValues: PaddingValues
) {
    val list = listOf<WordListModel>(WordListModel(id = 1, name = "Слова", words = listOf()), WordListModel(id = 1, name = "Слова", words = listOf()))

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn() {
            items(list) {
                ListOfWordsItem(
                    item = it
                )
            }
        }
        Spacer(modifier = Modifier.weight(1F))
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.End),
            onClick = { /*TODO*/ },
            containerColor = MaterialTheme.colorScheme.secondary
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.word).uppercase(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black
                    )
                )
            }
        }
    }
}

@Composable
fun ListOfWordsItem(
    item: WordListModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = R.drawable.language_white),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = item.name,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.words, item.words.size),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp,
                    )
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainPreview() {
    DictionaryWithExamplesTheme(darkTheme = true) {
        MainScreen(
            PaddingValues(0.dp)
        )
    }
}