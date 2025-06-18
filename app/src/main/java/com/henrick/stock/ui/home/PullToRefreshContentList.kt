package com.henrick.stock.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.henrick.stock.ui.components.StockCard
import com.henrick.stock.ui.components.StockListNotFoundScreen
import com.henrick.stock.ui.components.StockPlaceHolderCard
import com.henrick.stock.model.StockInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshContentList(
    stockLists: List<StockInfo>,
    listState: LazyListState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClearFilter: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberPullToRefreshState()
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = state,
        modifier = modifier
    ) {
        if (!isRefreshing && stockLists.isEmpty()) {
            // no result found.
            StockListNotFoundScreen(
                onClick = onClearFilter,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(vertical = 4.dp),
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                val cardModifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(shape = RoundedCornerShape(8.dp))

                if (isRefreshing && stockLists.isEmpty()) {
                    // placeholder
                    items(count = 5) {
                        StockPlaceHolderCard(modifier = cardModifier)
                    }
                } else {
                    items(items = stockLists) { stockInfo ->
                        StockCard(stockInfo = stockInfo, modifier = cardModifier)
                    }
                }
            }
        }
    }
}