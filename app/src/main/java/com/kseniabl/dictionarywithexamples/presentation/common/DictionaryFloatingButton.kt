package com.kseniabl.dictionarywithexamples.presentation.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kseniabl.dictionarywithexamples.R

@Composable
fun ColumnScope.DictionaryFloatingButton(
    text: String
) {
    ExtendedFloatingActionButton(
        modifier = Modifier
            .padding(10.dp)
            .align(Alignment.End),
        onClick = { /*TODO*/ },
        containerColor = MaterialTheme.colorScheme.secondary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = text.uppercase(),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black
                )
            )
        }
    }
}