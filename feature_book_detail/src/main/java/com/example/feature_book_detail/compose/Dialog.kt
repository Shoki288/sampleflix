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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.feature_book_detail.BookDetailViewModel.ViewEvent

@Composable
internal fun DetailInfoDialog(text: String) {
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

@Preview
@Composable
private fun Preview() {
    DetailInfoDialog("PreviewPreviewPreviewPreviewPreview")
}
