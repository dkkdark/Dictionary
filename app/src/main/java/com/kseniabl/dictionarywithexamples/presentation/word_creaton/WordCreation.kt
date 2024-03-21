package com.kseniabl.dictionarywithexamples.presentation.word_creaton

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.kseniabl.dictionarywithexamples.R
import com.kseniabl.dictionarywithexamples.domain.model.ListModel
import com.kseniabl.dictionarywithexamples.presentation.common.BaseEvent
import com.kseniabl.dictionarywithexamples.presentation.common.DictionaryAsyncImage
import com.kseniabl.dictionarywithexamples.presentation.common.DictionaryFloatingButton
import com.kseniabl.dictionarywithexamples.ui.theme.ContainerFour
import com.kseniabl.dictionarywithexamples.ui.theme.ContainerOne
import com.kseniabl.dictionarywithexamples.ui.theme.ContainerThree
import com.kseniabl.dictionarywithexamples.ui.theme.ContainerTwo
import com.kseniabl.dictionarywithexamples.ui.theme.DictionaryWithExamplesTheme


@Composable
fun CreateWord(
    paddingValues: PaddingValues,
    toMainScreen: () -> Unit = {},
    viewModel: WordCreationViewModel = hiltViewModel()
) {
    var wordsItemsVisible by remember {
        mutableStateOf(false)
    }
    var addWordItemPadding by remember {
        mutableStateOf(0.dp)
    }
    var fabSize by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current

    var word by rememberSaveable {
        mutableStateOf("")
    }

    val definitions by viewModel.definition.collectAsState()
    val synonyms by viewModel.synonym.collectAsState()
    val translations by viewModel.translation.collectAsState()

    val lists by viewModel.lists.collectAsState()
    val chosenList by viewModel.chosenList.collectAsState()

    LaunchedEffect(true) {
        viewModel.wordCreationStates.collect {
            when (it) {
                is WordCreationViewModel.WordCreationStates.Error -> TODO()
                WordCreationViewModel.WordCreationStates.Loading -> TODO()
                WordCreationViewModel.WordCreationStates.ReturnToMainScreen -> {
                    toMainScreen()
                }
                WordCreationViewModel.WordCreationStates.SpecifyWord -> TODO()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column {
            DictionaryTextField(
                "Слово",
                onValueChange = {
                    viewModel.processEvents(BaseEvent.GetInfoForWord(it))
                    word = it
                },
                onClick = {
                    wordsItemsVisible = true
                },
                onSizeChanged = {
                    addWordItemPadding = it - 14.dp
                },
                chosenList,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                if (definitions.isNotEmpty()) {
                    TitleWordCreation(icon = R.drawable.world, title = "Definitions")
                    CardWordCreation(
                        color = ContainerOne,
                        titles = definitions.map { it.definition }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                if (translations.text.isNotEmpty()) {
                    TitleWordCreation(icon = R.drawable.language, title = "Translation")
                    CardWordCreation(
                        color = ContainerTwo,
                        titles = listOf(translations.text)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                if (synonyms.isNotEmpty()) {
                    TitleWordCreation(icon = R.drawable.book, title = "Synonyms")
                    CardWordCreation(
                        color = ContainerThree,
                        titles = synonyms.map { it.word }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                if (definitions.isNotEmpty() && !definitions.all { it.example == null }) {
                    TitleWordCreation(icon = R.drawable.world, title = "Examples")
                    CardWordCreation(
                        color = ContainerFour,
                        titles = definitions.mapNotNull { it.example }
                    )
                }
                Spacer(modifier = Modifier.height(fabSize))
            }
        }
        Column(
            modifier = Modifier.align(Alignment.BottomEnd),
        ) {
            DictionaryFloatingButton(text = "Добавить",
                onClick = {
                    viewModel.processWordCreationEvents(WordCreationViewModel.WordCreationEvent.SaveWord(word))
                },
                onSizeChanged = { fabSize = it + 10.dp }
            )
        }
        if (wordsItemsVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.5f))
            ) {}
        }
        AnimatedVisibility(
            visible = wordsItemsVisible,
            enter = slideInVertically { with(density) { (addWordItemPadding - 80.dp).roundToPx() } } + fadeIn(
                initialAlpha = 0.3F,
            ),
            exit = slideOutVertically { with(density) { (addWordItemPadding - 80.dp).roundToPx() } } + fadeOut(),
        ) {
            AddWordsItems(addWordItemPadding, lists, onClick = {
                viewModel.processWordCreationEvents(WordCreationViewModel.WordCreationEvent.UpdateChosenList(it))
                wordsItemsVisible = false
            })
        }
    }
}

@Composable
fun AddWordsItems(
    padding: Dp,
    lists: List<ListModel>,
    onClick: (ListModel) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = padding),
        horizontalAlignment = Alignment.End
    ) {
        lists.forEach {
            DefineListButton(modifier =
            Modifier
                .wrapContentWidth()
                .height(47.dp)
                .padding(top = 6.dp, bottom = 6.dp, start = 6.dp, end = 18.dp),
                it.listIcon, it.listName, onClick = { onClick(it) })
        }
    }
}

@Composable
fun DefineListButton(
    modifier: Modifier,
    data: String,
    text: String,
    onClick: () -> Unit = {}
) {
    val request =
        ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data(data = data)
            .build()
    Button(
        modifier = modifier,
        onClick = { onClick() },
        contentPadding = PaddingValues(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        DictionaryAsyncImage(request = request, Modifier.padding(vertical = 4.dp), 40.dp)
        Text(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            text = text,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CardWordCreation(
    color: Color,
    titles: List<String>
) {
    FlowRow(
        modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalArrangement = Arrangement.Top
    ) {
        titles.forEach {
            Card(
                modifier = Modifier.padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = color)
            ) {
                Text(
                    modifier = Modifier.padding(16.dp), text = it, style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun TitleWordCreation(
    icon: Int, title: String
) {
    Row(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(22.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title, style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun DictionaryTextField(
    hint: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    onSizeChanged: (Dp) -> Unit,
    list: ListModel?
) {
    var text by rememberSaveable {
        mutableStateOf("")
    }
    val localDensity = LocalDensity.current

    BasicTextField(modifier = Modifier
        .fillMaxWidth()
        .background(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        )
        .onGloballyPositioned { coordinates ->
            onSizeChanged(with(localDensity) { coordinates.size.height.toDp() })
        }, value = text, onValueChange = {
            text = it
            onValueChange(text)
         }, textStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 14.sp,
        fontWeight = FontWeight.Black
    ), cursorBrush = Brush.linearGradient(
        listOf(
            MaterialTheme.colorScheme.onBackground, MaterialTheme.colorScheme.onBackground
        )
    )) { innerTextField ->
        Box(
            modifier = Modifier.padding(18.dp),
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (box, button) = createRefs()
                createHorizontalChain(box, button, chainStyle = ChainStyle.SpreadInside)

                Box(
                    modifier = Modifier.constrainAs(box) {
                        top.linkTo(button.top)
                        bottom.linkTo(button.bottom)
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                    },
                ) {
                    innerTextField()
                    if (text.isEmpty()) {
                        Text(
                            text = hint, style = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground, fontSize = 14.sp
                            )
                        )
                    }
                }
                DefineListButton(
                    modifier = Modifier
                        .constrainAs(button) {
                            start.linkTo(box.end)
                            end.linkTo(parent.end)
                            height = Dimension.value(36.dp)
                        }
                        .widthIn(max = 170.dp),
                    data = list?.listIcon ?: "",
                    text = list?.listName ?: "Списков нет!",
                    onClick = { onClick() }
                )

            }
        }
    }
}

@Preview
@Composable
fun CreateWordPreview() {
    DictionaryWithExamplesTheme(darkTheme = true) {
        CreateWord(PaddingValues(0.dp))
    }
}