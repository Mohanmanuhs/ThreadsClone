package com.example.threadsclone.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp

@Composable
fun MyImage(
    painter: Painter,
    cd: String,
    size: Dp,
    contentScale: ContentScale,
    shape: Shape,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painter,
        contentDescription = cd,
        modifier = modifier
            .size(size)
            .clip(shape)
            .clickable {
                onClick()
            },
        contentScale = contentScale
    )
}
