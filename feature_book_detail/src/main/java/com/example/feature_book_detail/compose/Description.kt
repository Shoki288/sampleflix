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
internal fun Description(
    modifier: Modifier,
    onClickDescription: (String) -> Unit,
    description: String
) {
    Column(modifier = modifier) {
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
                .clickable {
                    onClickDescription(description)
                }
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (text, more) = createRefs()

                Text(
                    text = description,
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