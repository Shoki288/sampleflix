package com.example.core_design.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.core_design.R
import com.example.core_design.theme.white

@Composable
fun SingleColumnItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    author: String,
    reviewTotalCount: Int,
    reviewAverage: Int,
    description: String,
    isFavorite: Boolean,
    price: Int,
    onClickItem: () -> Unit,
    onClickFavorite: (Boolean) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .zIndex(8F)
            .padding(horizontal = 4.dp)
            .clickable { onClickItem() }
    ) {
        val (refImage, refTitle, refAuthor, refReview, refDescription, refPrice, refFavorite) = createRefs()

        // サムネイル
        AsyncImage(
            model = imageUrl,
            placeholder = painterResource(id = R.drawable.ic_not_found_image),
            error = painterResource(id = R.drawable.ic_not_found_image),
            fallback = painterResource(id = R.drawable.ic_not_found_image),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
                .constrainAs(refImage) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start)
                }
        )
        // タイトル
        Text(
            text = title,
            modifier = Modifier.constrainAs(refTitle) {
                top.linkTo(parent.top, margin = 8.dp)
                linkTo(
                    start = refImage.end,
                    end = parent.end,
                    startMargin = 4.dp,
                    endMargin = 8.dp,
                    bias = 0F
                )
                width = Dimension.fillToConstraints
            }
        )
        // 著者
        Text(
            text = author,
            modifier = Modifier.constrainAs(refAuthor) {
                top.linkTo(refTitle.bottom, margin = 4.dp)
                linkTo(
                    start = refImage.end,
                    end = parent.end,
                    startMargin = 4.dp,
                    endMargin = 8.dp,
                    bias = 0F
                )
                width = Dimension.fillToConstraints
            }
        )
        // レビュー
        StarsRating(
            rateAverage = reviewTotalCount,
            totalAverage = reviewAverage,
            modifier = Modifier.constrainAs(refReview) {
                top.linkTo(refAuthor.bottom, margin = 4.dp)
                linkTo(
                    start = refImage.end,
                    end = parent.end,
                    startMargin = 4.dp,
                    endMargin = 8.dp,
                    bias = 0F
                )
            }
        )
        // 説明
        Text(
            text = description,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(refDescription) {
                top.linkTo(refReview.bottom, margin = 4.dp)
                linkTo(
                    start = refImage.end,
                    end = parent.end,
                    startMargin = 4.dp,
                    endMargin = 8.dp,
                    bias = 0F
                )
                width = Dimension.fillToConstraints
            }
        )
        // 値段
        Text(
            text = stringResource(
                id = R.string.price,
                price
            ),
            modifier = Modifier.constrainAs(refPrice) {
                top.linkTo(refDescription.bottom, margin = 4.dp)
                linkTo(
                    start = refImage.end,
                    end = refFavorite.start,
                    startMargin = 4.dp,
                    endMargin = 8.dp,
                    bias = 0F
                )
            }
        )
        // お気に入りボタン
        IconButton(
            onClick = { onClickFavorite(isFavorite) },
            modifier = Modifier
                .padding(4.dp)
                .constrainAs(refFavorite) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            val iconPaintRes = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_no_favorite
            Icon(painter = painterResource(id = iconPaintRes), contentDescription = "お気に入り")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SingleColumnItem(
        modifier = Modifier.background(white),
        imageUrl = "imageUrl",
        title = "title",
        author = "author",
        reviewTotalCount = 100,
        reviewAverage = 100,
        description = "descriptiondescriptiondescriptiondescriptiondescriptiondescription",
        isFavorite = true,
        price = 100,
        onClickItem = {},
        onClickFavorite = {}
    )
}
