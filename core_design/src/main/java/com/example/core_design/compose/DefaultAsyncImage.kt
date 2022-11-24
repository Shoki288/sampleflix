package com.example.core_design.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.core_design.R

@Composable
fun DefaultAsyncImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        placeholder = painterResource(id = R.drawable.ic_not_found_image),
        error = painterResource(id = R.drawable.ic_not_found_image),
        fallback = painterResource(id = R.drawable.ic_not_found_image),
        contentDescription = null,
        modifier = modifier
    )
}