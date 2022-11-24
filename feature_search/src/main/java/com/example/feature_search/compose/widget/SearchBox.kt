package com.example.feature_search.compose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_design.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    keyword: String,
    onChangeValue: (String) -> Unit,
    onClickEnter: () -> Unit
) {
    OutlinedTextField(
        value = keyword,
        onValueChange = onChangeValue,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
            )
        },
        keyboardActions = KeyboardActions(
            onDone = { onClickEnter() }
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        singleLine = true,
        placeholder = { stringResource(id = R.string.search_box_hint) }
    )
}