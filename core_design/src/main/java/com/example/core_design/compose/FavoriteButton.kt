package com.example.core_design.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_design.R

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onClickFavorite: (Boolean) -> Unit
) {
    // お気に入りボタン
    IconToggleButton(
        checked = isFavorite,
        modifier = modifier,
        onCheckedChange = { onClickFavorite(isFavorite) }
    ) {
        val iconPaintRes = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_no_favorite
        Icon(
            painter = painterResource(id = iconPaintRes),
            contentDescription = "お気に入り"
        )
    }
}

@Preview
@Composable
private fun Preview() {
    FavoriteButton(
        modifier = Modifier.padding(4.dp),
        isFavorite = true,
        onClickFavorite = { }
    )
}
