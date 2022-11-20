package com.example.core_design.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.core_design.R


@Composable
fun IconText(
    modifier: Modifier = Modifier,
    startIcon: Painter? = null,
    topIcon: Painter? = null,
    endIcon: Painter? = null,
    bottomIcon: Painter? = null,
    margin: Dp? = null,
    content: @Composable () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (refTopIcon, refStartIcon, refBottomIcon, refEndIcon, refContent) = createRefs()

        Box(
            modifier = Modifier.constrainAs(refContent) {
                top.linkTo(topIcon?.run { refTopIcon.bottom } ?: parent.top)
                start.linkTo(startIcon?.run { refStartIcon.end } ?: parent.start)
                end.linkTo(endIcon?.run { refEndIcon.start } ?: parent.end)
                bottom.linkTo(bottomIcon?.run { refBottomIcon.top } ?: parent.bottom)
            },
        ) {
            content()
        }

        startIcon?.let { icon ->
            Icon(
                painter = icon,
                modifier = Modifier.constrainAs(refStartIcon) {
                    linkTo(start = parent.start, end = refContent.start, endMargin = margin ?: 0.dp)
                    linkTo(top = refContent.top, bottom = refContent.bottom)
                },
                contentDescription = null
            )
        }

        topIcon?.let { icon ->
            Icon(
                painter = icon,
                modifier = Modifier.constrainAs(refTopIcon) {
                    linkTo(start = refContent.start, end = refContent.end)
                    linkTo(top = parent.top, bottom = refContent.top, bottomMargin = margin ?: 0.dp)
                },
                contentDescription = null
            )
        }

        endIcon?.let { icon ->
            Icon(
                painter = icon,
                modifier = Modifier.constrainAs(refEndIcon) {
                    linkTo(start = refContent.end, end = parent.end, startMargin = margin ?: 0.dp)
                    linkTo(top = refContent.top, bottom = refContent.bottom)
                },
                contentDescription = null
            )
        }

        bottomIcon?.let { icon ->
            Icon(
                painter = icon,
                modifier = Modifier.constrainAs(refBottomIcon) {
                    linkTo(start = refContent.start, end = refContent.end)
                    linkTo(top = refContent.bottom, bottom = parent.bottom, topMargin = margin ?: 0.dp)
                },
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    IconText(
        startIcon = painterResource(id = R.drawable.ic_home),
        topIcon = painterResource(id = R.drawable.ic_home),
        endIcon = painterResource(id = R.drawable.ic_home),
        bottomIcon = painterResource(id = R.drawable.ic_home),
        margin = 10.dp
    ) {
        Text(text = "texttexttexttexttexttexttexttext")
    }
}