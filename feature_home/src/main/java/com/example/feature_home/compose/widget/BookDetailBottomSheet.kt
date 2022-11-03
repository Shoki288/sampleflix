package com.example.feature_home.compose.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.core_unit_test.fixture.createBookInfo
import com.example.extension.R
import com.example.feature_home.vo.BookDetailBottomSheetUiState
import com.example.core_design.R as CoreR

@Composable
fun SingleBookDetailBottomSheet(
    uiState: BookDetailBottomSheetUiState,
    onClickClose: () -> Unit,
) {
    when (uiState) {
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
            // TODO constraintにして作り変えたい
            Box {
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = onClickClose
                ) {
                    Icon(
                        painter = painterResource(id = CoreR.drawable.ic_close),
                        contentDescription = null
                    )
                }

                Row {
                    // サムネ
                    // TODO 画像がうまく表示されない
                    AsyncImage(
                        model = uiState.book.volumeInfo.images,
                        placeholder = painterResource(id = R.drawable.ic_not_found_image),
                        error = painterResource(id = R.drawable.ic_not_found_image),
                        fallback = painterResource(id = R.drawable.ic_not_found_image),
                        contentDescription = null,
                        modifier = Modifier
                            .width(150.dp)
                            .height(200.dp)
                    )

                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        // タイトル
                        Text(text = uiState.book.volumeInfo.title, maxLines = 1, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        // 著者
                        Text(text = uiState.book.volumeInfo.author)
                        // 説明
                        Text(text = uiState.book.volumeInfo.description, maxLines = 3)
                    }
                }
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