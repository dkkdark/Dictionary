package com.kseniabl.dictionarywithexamples.presentation.list_creation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.kseniabl.dictionarywithexamples.presentation.common.DictionaryFloatingButton
import com.kseniabl.dictionarywithexamples.ui.theme.DictionaryWithExamplesTheme
import com.kseniabl.dictionarywithexamples.ui.theme.Selected

@Composable
fun ListCreation(
    padding: PaddingValues = PaddingValues(0.dp),
    viewModel: ListCreationViewModel = hiltViewModel()
) {
    val iconsList by viewModel.iconsUrls.collectAsState()
    var chosenIcon by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        CreateListField("Название списка")
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            CreateListField("Поиск иконок") {
                viewModel.processEvents(ListCreationViewModel.ListCreationEvent.SearchIcons(it))
            }
            HorizontalDivider()
            if (iconsList.isEmpty() || iconsList.all { it == null }) {
                NoSearchResults()
            }
            else {
                val configuration = LocalConfiguration.current
                val screenWidth = configuration.screenWidthDp.dp
                val iconSize = 60.dp
                val iconsCount = (screenWidth / iconSize).toInt()

                LazyVerticalGrid(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp),
                    columns = GridCells.Fixed(iconsCount)
                ) {
                    items(iconsList) {
                        if (it != null) IconsList(it, chosenIcon) {
                            chosenIcon = it
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1F))
        DictionaryFloatingButton(text = "Добавить") {}
    }
}

@Composable
fun NoSearchResults() {
    Text(
        modifier = Modifier.padding(18.dp),
        text = "Нет результатов",
        style = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp
        )
    )
}

@Composable
fun CreateListField(
    hint: String,
    valueChanged: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 12.dp,
                    bottomStart = 12.dp
                ), color = MaterialTheme.colorScheme.primaryContainer
            ),
        value = text,
        onValueChange = {
            text = it
            if (text.length >= 3) valueChanged(text)
        },
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black
        ),
        cursorBrush = Brush.linearGradient(listOf(MaterialTheme.colorScheme.onBackground, MaterialTheme.colorScheme.onBackground))
    ) { innerTextField ->
        Box(
            modifier = Modifier.padding(20.dp),
        ) {
            innerTextField()
            if (text.isEmpty()) {
                Text(
                    text = hint,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }

}


@Composable
fun IconsList(
    icon: String,
    chosenIcon: String?,
    iconClicked: () -> Unit
) {
    val request =
        ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data(data = icon)
            .build()

    Card(
        modifier = Modifier
            .padding(3.dp)
            .clickable {
                iconClicked()
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor =
        if (chosenIcon != icon) MaterialTheme.colorScheme.primaryContainer else Selected)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.padding(8.dp),
            model = request,
            contentDescription = null
        ) {
            when(painter.state) {
                is AsyncImagePainter.State.Error -> {
                }
                is AsyncImagePainter.State.Loading -> {
                }
                is AsyncImagePainter.State.Success -> {
                    Image(
                        modifier = Modifier.size(38.dp),
                        painter = painter,
                        contentDescription = null)
                }
                else -> {
                    Log.e("qqq", "else")
                }
            }
        }
    }

}


@Composable
@Preview
fun ListCreationPreview() {
    DictionaryWithExamplesTheme(darkTheme = true) {
        ListCreation()
    }
}