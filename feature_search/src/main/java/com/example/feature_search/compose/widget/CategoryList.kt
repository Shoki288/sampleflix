package com.example.feature_search.compose.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoryList(
    categories: List<String>,
    onClickCategory: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        categories.forEach {
            item {
                CategoryItem(
                    category = it,
                    onClickItem = onClickCategory
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: String,
    onClickItem: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(size = 20.dp),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier
            .width(200.dp)
            .height(100.dp)
            .clickable { onClickItem(category) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = category,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            )
        }
    }
}