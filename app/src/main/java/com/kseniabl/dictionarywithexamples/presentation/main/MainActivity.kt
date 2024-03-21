package com.kseniabl.dictionarywithexamples.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.kseniabl.dictionarywithexamples.R
import com.kseniabl.dictionarywithexamples.presentation.navigation.DictionaryNavHost
import com.kseniabl.dictionarywithexamples.ui.theme.DictionaryWithExamplesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryWithExamplesTheme(darkTheme = true) {
                val navHost = rememberNavController()
                var topBarState by remember {
                    mutableStateOf(TopBarState(true, ""))
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.background,
                        topBar = { DictionaryTopBar(topBarState) }
                    ) {
                        DictionaryNavHost(navController = navHost, padding = it,
                            topBarChanged = { topBarState = it }
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun DictionaryTopBar(
    topBarState: TopBarState
) {
    Row(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!topBarState.isMain) {
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = topBarState.title,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        else {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = R.drawable.language_white),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(18.dp))
            Text(
                text = "Dictionary",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}