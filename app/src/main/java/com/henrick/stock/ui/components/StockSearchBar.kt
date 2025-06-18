package com.henrick.stock.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = false,
                onExpandedChange = {},
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        modifier = Modifier.clickable(onClick = onClearClick)
                    )
                },
            )
        },
        expanded = false,
        onExpandedChange = {},
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        windowInsets = WindowInsets(top = 0.dp), // 強制為0, 避免受到系統帶入的 padding 導致顯示不如預期
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp) // 藉此設定讓 SearchBar 畫面置中
    ) {

    }
}

@Preview
@Composable
private fun StockSearchBarPreview() {
    StockSearchBar(
        query = "",
        onSearch = { },
        onQueryChange = { },
        onClearClick = { },
    )
}