package com.henrick.stock.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.henrick.stock.ui.components.LoadingErrorScreen
import com.henrick.stock.ui.components.StockModalBottomSheet
import com.henrick.stock.ui.components.StockSearchBar
import com.henrick.stock.viewmodels.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockContentScreen(
    stockViewModel: StockViewModel,
    listState: LazyListState,
    sheetState: SheetState,
    showBottomSheet: Boolean,
    onDismissRequest: () -> Unit,
    onBottomSheetDescClick: () -> Unit,
    onBottomSheetAscClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // content
    Column(modifier = modifier) {

        val focusManager = LocalFocusManager.current
        val stockLists by stockViewModel.stockInfoLists.collectAsStateWithLifecycle()
        val uiState by stockViewModel.uiState.collectAsStateWithLifecycle()
        val textFieldState = rememberTextFieldState()

        StockSearchBar(
            query = textFieldState.text.toString(),
            onQueryChange = { query ->
                textFieldState.edit { replace(0, length, query) }
                stockViewModel.queryAndSortedStockLists(query)
            },
            onSearch = { query ->
                stockViewModel.queryAndSortedStockLists(query)
                focusManager.clearFocus()
            },
            onClearClick = {
                if (textFieldState.text.isNotBlank()) {
                    textFieldState.edit { delete(0, length) }
                }
                stockViewModel.queryAndSortedStockLists("")
                focusManager.clearFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        when (uiState) {

            is StockViewModel.UiState.Error -> {
                LoadingErrorScreen(
                    errorMessage = (uiState as StockViewModel.UiState.Error).errorMessage,
                    onRefreshClick = { stockViewModel.fetchData() },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            else -> {
                //StockViewModel.UiState.Loading, StockViewModel.UiState.Success
                PullToRefreshContentList(
                    stockLists = stockLists,
                    listState = listState,
                    isRefreshing = uiState == StockViewModel.UiState.Loading,
                    onRefresh = { stockViewModel.fetchData() },
                    onClearFilter = {
                        if (textFieldState.text.isNotBlank()) {
                            textFieldState.edit { delete(0, length) }
                        }
                        stockViewModel.queryAndSortedStockLists("")
                        focusManager.clearFocus()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

        if (showBottomSheet) {
            StockModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = onDismissRequest,
                onBottomSheetDescClick = onBottomSheetDescClick,
                onBottomSheetAscClick = onBottomSheetAscClick,
            )
        }
    }
}