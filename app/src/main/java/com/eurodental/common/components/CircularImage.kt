package com.eurodental.common.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.eurodental.R

@Composable
fun CircularImage(
    imagePath : String?,
    modifier: Modifier = Modifier) {
    AsyncImage(
        model = imagePath,
        contentDescription = "Circular Image",
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.baseline_person_24),
        modifier = modifier
            .size(52.dp)
            .clip(CircleShape)
    )
}