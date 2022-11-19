package com.example.feature_book_detail.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.feature_book_detail.BookDetailInfo
import com.example.feature_book_detail.R

@Composable
internal fun DescriptionDialog(text: String) {
    val isOpenDialog = remember { mutableStateOf(true) }
    if (isOpenDialog.value) {
        Dialog(
            onDismissRequest = { isOpenDialog.value = false }
        ) {
            Card(
                shape = RoundedCornerShape(size = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = text,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
    }
}

@Composable
internal fun AboutDialog(
    info: BookDetailInfo,
) {
    val isOpenDialog = remember { mutableStateOf(true) }
    if (isOpenDialog.value) {
        Dialog(
            onDismissRequest = { isOpenDialog.value = false }
        ) {
            Card(
                shape = RoundedCornerShape(size = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ConstraintLayout(
                    modifier = Modifier.padding(32.dp)
                ) {
                    val (authorTitle, author, publisherTitle, publisher, publishDateTitle, publishDate,
                        pageCountTitle, pageCount, categoryTitle, category, languageTitle, language) = createRefs()

                    val titlesBarrier = createEndBarrier(authorTitle, publisherTitle, publishDateTitle,
                        pageCountTitle, categoryTitle, languageTitle, margin = 24.dp)

                    val authorBarrier = createBottomBarrier(authorTitle, author, margin = 8.dp)
                    val publisherBarrier = createBottomBarrier(publisherTitle, publisher, margin = 8.dp)
                    val publishDateBarrier = createBottomBarrier(publishDateTitle, publishDate, margin = 8.dp)
                    val pageCountBarrier = createBottomBarrier(pageCountTitle, pageCount, margin = 8.dp)
                    val categoryBarrier = createBottomBarrier(categoryTitle, category, margin = 8.dp)

                    // 著者
                    Text(
                        text = stringResource(id = R.string.author_title),
                        modifier = Modifier.constrainAs(authorTitle) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                    )
                    Text(
                        text = info.authors,
                        modifier = Modifier.constrainAs(author) {
                            start.linkTo(titlesBarrier)
                            end.linkTo(parent.end)
                            baseline.linkTo(authorTitle.baseline)
                        }
                    )

                    // 出版社
                    Text(
                        text = stringResource(id = R.string.publisher_title),
                        modifier = Modifier.constrainAs(publisherTitle) {
                            top.linkTo(authorBarrier)
                            start.linkTo(parent.start)
                        }
                    )
                    Text(
                        text = info.publisher,
                        modifier = Modifier.constrainAs(publisher) {
                            linkTo(start = titlesBarrier, end = parent.end, bias = 0f)
                            baseline.linkTo(publisherTitle.baseline)
                        }
                    )

                    // 発売日
                    Text(
                        text = stringResource(id = R.string.publish_date_title),
                        modifier = Modifier.constrainAs(publishDateTitle) {
                            top.linkTo(publisherBarrier)
                            start.linkTo(parent.start)
                        }
                    )
                    Text(
                        text = info.publisherDate,
                        modifier = Modifier.constrainAs(publishDate) {
                            linkTo(start = titlesBarrier, end = parent.end, bias = 0f)
                            baseline.linkTo(publishDateTitle.baseline)
                        }
                    )

                    // ページ数
                    Text(
                        text = stringResource(id = R.string.page_count_title),
                        modifier = Modifier.constrainAs(pageCountTitle) {
                            top.linkTo(publishDateBarrier)
                            start.linkTo(parent.start)
                        }
                    )
                    Text(
                        text = info.pageCount.toString(),
                        modifier = Modifier.constrainAs(pageCount) {
                            linkTo(start = titlesBarrier, end = parent.end, bias = 0f)
                            baseline.linkTo(pageCountTitle.baseline)
                        }
                    )

                    // カテゴリ
                    Text(
                        text = stringResource(id = R.string.category_title),
                        modifier = Modifier.constrainAs(categoryTitle) {
                            top.linkTo(pageCountBarrier)
                            start.linkTo(parent.start)
                        }
                    )
                    Text(
                        text = info.categories,
                        modifier = Modifier.constrainAs(category) {
                            linkTo(start = titlesBarrier, end = parent.end, bias = 0f)
                            baseline.linkTo(categoryTitle.baseline)
                        }
                    )

                    // 言語
                    Text(
                        text = stringResource(id = R.string.language_title),
                        modifier = Modifier.constrainAs(languageTitle) {
                            top.linkTo(categoryBarrier)
                            start.linkTo(parent.start)
                        }
                    )
                    Text(
                        text = info.language,
                        modifier = Modifier.constrainAs(language) {
                            linkTo(start = titlesBarrier, end = parent.end, bias = 0f)
                            baseline.linkTo(languageTitle.baseline)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
//    DescriptionDialog("PreviewPreviewPreviewPreviewPreview")

    AboutDialog(
        info = BookDetailInfo(
            title = "titletitletitletitletitletitletitle",
            authors = "authorsauthorsauthorsauthorsauthorsauthorsauthors",
            publisher = "publisherpublisherpublisherpublisherpublisherpublisherpublisher",
            publisherDate = "publisherDatepublisherDatepublisherDatepublisherDatepublisherDatepublisherDatepublisherDate",
            description = "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription",
            pageCount = 1000,
            categories = "categoriescategoriescategoriescategoriescategoriescategoriescategories",
            image = "imageimageimageimageimageimageimage",
            language = "languagelanguagelanguagelanguagelanguagelanguagelanguage",
            previewLink = "previewLinkpreviewLinkpreviewLinkpreviewLinkpreviewLinkpreviewLinkpreviewLink",
            averageReviewRate = 1000,
            totalReviewCount = 1000,
            price = 1000
        )
    )
}
