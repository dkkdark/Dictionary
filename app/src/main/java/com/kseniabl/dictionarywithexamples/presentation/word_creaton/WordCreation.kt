package com.kseniabl.dictionarywithexamples.presentation.word_creaton

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import com.kseniabl.dictionarywithexamples.R
import com.kseniabl.dictionarywithexamples.presentation.common.DictionaryFloatingButton
import com.kseniabl.dictionarywithexamples.ui.theme.BaseContainer
import com.kseniabl.dictionarywithexamples.ui.theme.ContainerFour
import com.kseniabl.dictionarywithexamples.ui.theme.ContainerOne
import com.kseniabl.dictionarywithexamples.ui.theme.ContainerThree
import com.kseniabl.dictionarywithexamples.ui.theme.ContainerTwo
import com.kseniabl.dictionarywithexamples.ui.theme.DictionaryWithExamplesTheme


@Composable
fun CreateWord() {
    var wordsItemsVisible by remember {
        mutableStateOf(false)
    }
    var addWordItemPadding by remember {
        mutableStateOf(0.dp)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (searchWord, column) = createRefs()

        Column(
            modifier = Modifier.constrainAs(column) { top.linkTo(searchWord.bottom) }
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            TitleWordCreation(icon = R.drawable.world, title = "Definitions")
            CardWordCreation(
                color = ContainerOne,
                titles = listOf("causing annoyance, impatience, or mild anger")
            )
            Spacer(modifier = Modifier.height(10.dp))
            TitleWordCreation(icon = R.drawable.language, title = "Translation")
            CardWordCreation(
                color = ContainerTwo,
                titles = listOf("раздраженный", "раздражающий", "мешающий")
            )
            Spacer(modifier = Modifier.height(10.dp))
            TitleWordCreation(icon = R.drawable.book, title = "Synonyms")
            CardWordCreation(color = ContainerThree, titles = listOf("annoying", "exasperation"))
            Spacer(modifier = Modifier.height(10.dp))
            TitleWordCreation(icon = R.drawable.world, title = "Examples")
            CardWordCreation(
                color = ContainerFour,
                titles = listOf(
                    "the substance may be irritating to eyes and skin",
                    "an irritating child"
                )
            )
        }
        if (wordsItemsVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.5f))
            ) {
            }
        }
        DictionaryTextField("Слово", searchWord, onClick =  {
            wordsItemsVisible = !wordsItemsVisible
        }, onSizeChanged = {
            addWordItemPadding = it - 14.dp
        })
        if (wordsItemsVisible) {
            AddWordsItems(addWordItemPadding)
        }
    }
}

@Composable
fun AddWordsItems(
    padding: Dp,
    onClick: () -> Unit = {}
) {
    val list = mapOf(ContainerOne to "example", ContainerTwo to "definition", ContainerThree to "translation", ContainerFour to "synonym")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = padding),
        horizontalAlignment = Alignment.End
    ) {
        list.forEach {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(47.dp)
                    .padding(top = 6.dp, bottom = 6.dp, start = 6.dp, end = 18.dp),
                onClick = { onClick() },
                contentPadding = PaddingValues(4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = it.key)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    text = it.value,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
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
                    modifier = Modifier.padding(16.dp),
                    text = it,
                    style = TextStyle(
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
    icon: Int,
    title: String
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
            text = title,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun ConstraintLayoutScope.DictionaryTextField(
    hint: String,
    ref: ConstrainedLayoutReference,
    onClick: () -> Unit,
    onSizeChanged: (Dp) -> Unit,
) {
    var text by remember {
        mutableStateOf("")
    }
    val localDensity = LocalDensity.current

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
            )
            .constrainAs(ref) { top.linkTo(parent.top) }
            .onGloballyPositioned { coordinates ->
                onSizeChanged(with(localDensity) { coordinates.size.height.toDp() })
            },
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
                            text = hint,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 14.sp
                            )
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .constrainAs(button) {
                            start.linkTo(box.end)
                            end.linkTo(parent.end)
                            width = Dimension.value(50.dp)
                            height = Dimension.value(30.dp)
                        }
                        .padding(start = 10.dp),
                    onClick = { onClick() },
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
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