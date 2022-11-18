package com.example.feature_book_detail.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.core_design.compose.StarsRating
import com.example.feature_book_detail.BookDetailInfo
import com.example.feature_book_detail.BookDetailInfoUiState
import com.example.feature_book_detail.BookDetailViewModel
import com.example.feature_book_detail.R
import com.example.core_design.R as Core_designR
import com.example.extension.R as ExtensionR

@Composable
fun BookDetailRoute(
    viewModel: BookDetailViewModel = viewModel()
) {
    val context = LocalContext.current

    BookDetailScreen(
        uiState = viewModel.bookInfoDetail.collectAsState().value,
        context = context
    )
}

@Composable
fun BookDetailScreen(
    uiState: BookDetailInfoUiState,
    context: Context,
    modifier: Modifier = Modifier
) {
    // TODO statusをみる
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (image, bookDetail, purchaseButton, description, info, userReviews) = createRefs()
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

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(bookDetail) {
                    start.linkTo(image.end)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                }
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // タイトル
            Text(
                text = uiState.bookDetailInfo.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 2,
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 著者
            Text(
                text = uiState.bookDetailInfo.authors,
                maxLines = 2,
            )
            Spacer(modifier = Modifier.height(8.dp))

            // レビュー
            StarsRating(
                rateAverage = uiState.bookDetailInfo.averageReviewRate,
                totalAverage = uiState.bookDetailInfo.totalReviewCount,
            )
            Spacer(modifier = Modifier.height(8.dp))

            // カテゴリ
            Text(
                text = uiState.bookDetailInfo.categories,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 値段
            Text(
                text = stringResource(id = Core_designR.string.price, formatArgs = arrayOf(uiState.bookDetailInfo.price)),
                maxLines = 2,
            )
        }

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
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .constrainAs(description) {
                    top.linkTo(purchaseButton.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = stringResource(id = R.string.description),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            Card(
                shape = RoundedCornerShape(size = 8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primaryContainer
                ),
                colors = CardDefaults.cardColors(Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
            ) {
                ConstraintLayout(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val (text, more) = createRefs()

                    Text(
                        text = uiState.bookDetailInfo.description,
                        modifier = Modifier
                            .padding(8.dp)
                            .constrainAs(text) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                end.linkTo(more.start)
                                width = Dimension.fillToConstraints
                            },
                        maxLines = 3
                    )
                    Image(
                        painter = painterResource(id = Core_designR.drawable.ic_more),
                        modifier = Modifier.constrainAs(more) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                        contentDescription = null
                    )
                }
            }
        }

        // 情報
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .constrainAs(info) {
                    top.linkTo(description.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = stringResource(id = R.string.info),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            Card(
                shape = RoundedCornerShape(size = 8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primaryContainer
                ),
                colors = CardDefaults.cardColors(Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    val (publisherTitle, publisher, publishDateTitle, publishDate, languageTitle, language, more) = createRefs()
                    val titleBarrier = createEndBarrier(publisherTitle, publishDateTitle, languageTitle, margin = 16.dp)
                    val publisherBarrier = createBottomBarrier(publisherTitle, publisher, margin = 4.dp)
                    val publisherDateBarrier = createBottomBarrier(publishDateTitle, publishDate, margin = 4.dp)

                    // 出版社タイトル
                    Text(
                        text = stringResource(id = R.string.publisher_title),
                        modifier = Modifier.constrainAs(publisherTitle) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                    )
                    // 出版社
                    Text(
                        text = uiState.bookDetailInfo.publisher,
                        modifier = Modifier.constrainAs(publisher) {
                            start.linkTo(titleBarrier)
                            top.linkTo(parent.top)
                            end.linkTo(more.start)
                            width = Dimension.fillToConstraints
                        },
                        maxLines = 2,
                    )

                    // 発売日タイトル
                    Text(
                        text = stringResource(id = R.string.publish_date_title),
                        modifier = Modifier.constrainAs(publishDateTitle) {
                            top.linkTo(publisherBarrier)
                            start.linkTo(parent.start)
                        }
                    )
                    // 発売日
                    Text(
                        text = uiState.bookDetailInfo.publishedDate,
                        modifier = Modifier
                            .constrainAs(publishDate) {
                                start.linkTo(titleBarrier)
                                top.linkTo(publisherBarrier)
                                baseline.linkTo(publishDateTitle.baseline)
                                end.linkTo(more.start)
                                width = Dimension.fillToConstraints
                            },
                        maxLines = 2,
                    )

                    // 言語タイトル
                    Text(
                        text = stringResource(id = R.string.language_title),
                        modifier = Modifier.constrainAs(languageTitle) {
                            top.linkTo(publisherDateBarrier)
                            start.linkTo(parent.start)
                        }
                    )
                    // 言語
                    Text(
                        text = uiState.bookDetailInfo.language,
                        modifier = Modifier
                            .constrainAs(language) {
                                top.linkTo(publisherDateBarrier)
                                start.linkTo(titleBarrier)
                                end.linkTo(more.start)
                                baseline.linkTo(languageTitle.baseline)
                                width = Dimension.fillToConstraints
                            },
                        maxLines = 2,
                    )

                    Image(
                        painter = painterResource(id = Core_designR.drawable.ic_more),
                        modifier = Modifier.constrainAs(more) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                        contentDescription = null
                    )
                }
            }
        }

        // カスタマーレビュー
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp)
                .constrainAs(userReviews) {
                    top.linkTo(info.bottom)
                    start.linkTo(parent.start)
                }
        ) {
            Text(
                text = stringResource(id = R.string.customer_review_title),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            StarsRating(
                rateAverage = uiState.bookDetailInfo.averageReviewRate,
                totalAverage = uiState.bookDetailInfo.totalReviewCount,
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
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
        modifier = Modifier.background(Color.White)
    )
}
