package com.kseniabl.dictionarywithexamples.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kseniabl.dictionarywithexamples.domain.model.DefinitionEntity
import com.kseniabl.dictionarywithexamples.domain.model.TranslationEntity
import com.kseniabl.dictionarywithexamples.domain.model.WordEntity
import com.kseniabl.dictionarywithexamples.presentation.text_selecton.DefinitionsListView
import com.kseniabl.dictionarywithexamples.presentation.text_selecton.SynonymsList

@Composable
fun WordItem(
    text: String,
    definitions: List<DefinitionEntity>,
    synonyms: List<WordEntity>,
    translations: TranslationEntity
) {
    Column {
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        DefinitionsListView(definitions)
        Spacer(modifier = Modifier.height(10.dp))
        SynonymsList(synonyms)
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = translations.text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}