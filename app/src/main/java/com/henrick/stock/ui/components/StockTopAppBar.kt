package com.henrick.stock.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.henrick.stock.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun StockTopAppBar(
    onFilterClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        title = {
            Text(
                text = stringResource(R.string.text_top_app_bar_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            IconButton(
                onClick = onFilterClick,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    .size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.menu_icon_content_description_list_sorting),
                )
            }
        }
    )
}