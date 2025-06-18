package com.henrick.stock.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.henrick.stock.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockModalBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onBottomSheetDescClick: () -> Unit,
    onBottomSheetAscClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        BottomSheetContent(
            onBottomSheetDescClick = onBottomSheetDescClick,
            onBottomSheetAscClick = onBottomSheetAscClick,
        )
    }
}

@Composable
private fun BottomSheetContent(
    onBottomSheetAscClick: () -> Unit,
    onBottomSheetDescClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        BottomSheetTextButton(
            text = R.string.button_text_sort_stock_code_desc,
            onBottomSheetButtonClick = onBottomSheetDescClick,
        )
        BottomSheetTextButton(
            text = R.string.button_text_sort_stock_code_asc,
            onBottomSheetButtonClick = onBottomSheetAscClick,
        )
    }
}

@Composable
private fun BottomSheetTextButton(
    @StringRes text: Int,
    onBottomSheetButtonClick: () -> Unit
) {
    TextButton(
        onClick = onBottomSheetButtonClick
    ) {
        Text(
            text = stringResource(text),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.fillMaxWidth()
        )
    }
}