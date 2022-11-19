package com.example.feature_book_detail.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.feature_book_detail.BookDetailInfo
import com.example.feature_book_detail.BookDetailInfoUiState
import com.example.feature_book_detail.BookDetailViewModel
import com.example.feature_book_detail.BookDetailViewModel.ViewEvent
import com.example.feature_book_detail.R
import com.example.extension.R as ExtensionR

@Composable
fun BookDetailRoute(
    viewModel: BookDetailViewModel = viewModel()
) {
    val context = LocalContext.current

    BookDetailScreen(
        uiState = viewModel.bookInfoDetail.collectAsState().value,
        context = context,
        onClickDescription = { viewModel.onClickDescription(it) }
    )

    when (val event = viewModel.viewEvent.collectAsState(initial = ViewEvent.None).value) {
        is ViewEvent.OpenDetailInfoDialog -> {
            DetailInfoDialog(event.text)
        }
        ViewEvent.None -> {}
    }
}

@Composable
fun BookDetailScreen(
    uiState: BookDetailInfoUiState,
    context: Context,
    modifier: Modifier = Modifier,
    onClickDescription: (String) -> Unit
) {
    // TODO statusをみる
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (image, bookDetail, purchaseButton, description, about, userReviews) = createRefs()
        // 商品画像
        AsyncImage(
            model = uiState.bookDetailInfo.image,
            placeholder = painterResource(id = ExtensionR.drawable.ic_not_found_image),
            error = painterResource(id = ExtensionR.drawable.ic_not_found_image),
            fallback = painterResource(id = ExtensionR.drawable.ic_not_found_image),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
        )

        // タイトル, 著者, レビュー, カテゴリ, 値段
        Content(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(bookDetail) {
                    start.linkTo(image.end)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                },
            titleText = uiState.bookDetailInfo.title,
            authorsText = uiState.bookDetailInfo.authors,
            averageReviewRateText = uiState.bookDetailInfo.averageReviewRate,
            totalReviewCountText = uiState.bookDetailInfo.totalReviewCount,
            categoriesText = uiState.bookDetailInfo.categories,
            priceText = uiState.bookDetailInfo.price,
        )

        // 購入するボタン
        val purchaseBarrier = createBottomBarrier(image, bookDetail)
        OutlinedButton(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .constrainAs(purchaseButton) {
                    top.linkTo(purchaseBarrier)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            border = null,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
            onClick = {
                Toast.makeText(context, "購入しました！", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(
                text = stringResource(id = R.string.purchase),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }

        // 詳細説明
        Description(
            modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(purchaseButton.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(vertical = 8.dp, horizontal = 24.dp),
            onClickDescription = onClickDescription,
            description = uiState.bookDetailInfo.description
        )

        // 情報
        About(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .constrainAs(about) {
                    top.linkTo(description.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            publisherText = uiState.bookDetailInfo.publisher,
            publishedDateText = uiState.bookDetailInfo.publishedDate,
            languageText = uiState.bookDetailInfo.language,
        )

        // カスタマーレビュー
        UserReviews(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .constrainAs(userReviews) {
                    top.linkTo(about.bottom)
                    start.linkTo(parent.start)
                },
            averageReviewRate = uiState.bookDetailInfo.averageReviewRate,
            totalReviewCount = uiState.bookDetailInfo.totalReviewCount
        )
    }
}

@Preview
@Composable
private fun Preview() {
    val context = LocalContext.current

    BookDetailScreen(
        uiState = BookDetailInfoUiState(
            bookDetailInfo = BookDetailInfo(
                title = "titletitletitletitletitletitletitletitle",
                authors = "authorsauthorsauthorsauthorsauthorsauthors",
                publisher = "出版社出版社出版社出版社出版社出版社",
                publishedDate = "発売日発売日発売日発売日発売日発売日",
                description = "descriptiondescriptiondescriptiondescriptiondescriptiondescription",
                pageCount = 100000,
                categories = "categoriescategoriescategoriescategories",
                image = "image",
                language = "言語言語言語言語言語言語言語言語言語言語言語",
                previewLink = "previewLink",
                averageReviewRate = 100,
                totalReviewCount = 100,
                price = 1000,
            )
        ),
        context = context,
        modifier = Modifier.background(Color.White),
        onClickDescription = {}
    )
}
