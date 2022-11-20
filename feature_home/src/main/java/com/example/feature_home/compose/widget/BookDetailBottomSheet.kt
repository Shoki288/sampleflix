package com.example.feature_home.compose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.core_design.compose.IconText
import com.example.core_unit_test.fixture.createBookInfo
import com.example.feature_home.R
import com.example.feature_home.vo.BookDetailBottomSheetUiState
import com.example.core_design.R as CoreR
import com.example.extension.R as ExtensionR

@Composable
fun SingleBookDetailBottomSheet(
    uiState: BookDetailBottomSheetUiState,
    onClickClose: () -> Unit,
) = when (uiState) {
    is BookDetailBottomSheetUiState.Loading -> {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
    is BookDetailBottomSheetUiState.Success -> {
        ConstraintLayout(
            modifier = Modifier
                .padding(8.dp)
                .background(MaterialTheme.colors.background),
        ) {
            val (image, title, close, author, description, more) = createRefs()

            // ×ボタン
            IconButton(
                onClick = onClickClose,
                modifier = Modifier.constrainAs(close) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
            ) {
                Icon(
                    painter = painterResource(id = CoreR.drawable.ic_close),
                    contentDescription = null
                )
            }

            // サムネ
            AsyncImage(
                model = uiState.book.volumeInfo.images.imageUrl,
                placeholder = painterResource(id = ExtensionR.drawable.ic_not_found_image),
                error = painterResource(id = ExtensionR.drawable.ic_not_found_image),
                fallback = painterResource(id = ExtensionR.drawable.ic_not_found_image),
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            // タイトル
            Text(
                text = uiState.book.volumeInfo.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(anchor = parent.top, margin = 4.dp)
                    linkTo(start = image.end, end = close.start, startMargin = 4.dp, endMargin = 4.dp, bias = 0F)
                }
            )

            // 著者
            Text(
                text = uiState.book.volumeInfo.author,
                modifier = Modifier.constrainAs(author) {
                    top.linkTo(anchor = title.bottom, margin = 4.dp)
                    linkTo(start = image.end, end = close.start, startMargin = 4.dp, endMargin = 4.dp, bias = 0F)
                }
            )

            // 説明
            Text(
                text = uiState.book.volumeInfo.description,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(description) {
                    top.linkTo(anchor = author.bottom, margin = 4.dp)
                    start.linkTo(image.end, margin = 4.dp)
                }
            )

            val bottomBarrier = createBottomBarrier(image, description)

            Row(
                modifier = Modifier
                    .constrainAs(more) {
                        top.linkTo(bottomBarrier)
                        linkTo(start = parent.start, end = parent.end, bias = 0F)
                    }
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .clickable { },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconText(
                    startIcon = painterResource(id = CoreR.drawable.ic_info),
                    margin = 8.dp,
                ) {
                    Text(text = stringResource(id = R.string.detail_info))
                }
                Icon(
                    painter = painterResource(id = CoreR.drawable.ic_more),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun preview() {
    SingleBookDetailBottomSheet(
        uiState = BookDetailBottomSheetUiState.Success(createBookInfo()),
        onClickClose = {},
    )
}