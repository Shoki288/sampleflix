package com.example.feature_book_detail.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.feature_book_detail.R

@Composable
internal fun About(
    modifier: Modifier,
    publisherText: String,
    publishedDateText: String,
    languageText: String,
    onClickAbout: () -> Unit
) {
    Column(modifier = modifier) {
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
                .clickable { onClickAbout() }
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                val (publisherTitle, publisher, publishDateTitle, publishDate, languageTitle, language, more) = createRefs()
                val titleBarrier = createEndBarrier(
                    publisherTitle,
                    publishDateTitle,
                    languageTitle,
                    margin = 16.dp
                )
                val publisherBarrier = createBottomBarrier(publisherTitle, publisher, margin = 4.dp)
                val publisherDateBarrier =
                    createBottomBarrier(publishDateTitle, publishDate, margin = 4.dp)

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
                    text = publisherText,
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
                    text = publishedDateText,
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
                    text = languageText,
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
                    painter = painterResource(id = com.example.core_design.R.drawable.ic_more),
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
}