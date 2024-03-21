package com.kseniabl.dictionarywithexamples.presentation.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun DictionaryAsyncImage(
    request: ImageRequest,
    modifier: Modifier,
    size: Dp
) {
    SubcomposeAsyncImage(
        modifier = modifier,
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
                    modifier = Modifier.size(size),
                    painter = painter,
                    contentDescription = null)
            }
            else -> {
                Log.e("qqq", "else")
            }
        }
    }
}