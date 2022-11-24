package com.example.feature_home.compose.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecommendCategories(
    title: String,
    categoryNameList: List<String>,
    onClickItem: (String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(size = 20.dp),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // タイトル
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(start = 8.dp)
            )

            // カテゴリ
            RecommendCategoryCarousel(
                categoryList = categoryNameList,
                onClickCategory = { category ->
                    onClickItem(category)
                }
            )
        }
    }
}

@Composable
private fun RecommendCategoryCarousel(
    categoryList: List<String>,
    onClickCategory: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = categoryList,
            key = { category -> category }
        ) { category ->
            Card(
                shape = RoundedCornerShape(size = 20.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline
                ),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(size = 20.dp))
                    .clickable {
                        onClickCategory(category)
                    }
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
    }
}

@Composable
@Preview
private fun Preview() {
    RecommendCategories(
        title = "title",
        categoryNameList = listOf("list1", "list2", "list3", "list4", "list5", "list6", "list7"),
        onClickItem = {}
    )
}