package com.henrick.stock.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.henrick.stock.R


@Composable
fun ScrollToTopFab(
    showFab: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = showFab,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = modifier
                .clip(shape = CircleShape)
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_arrow_upward_24),
                contentDescription = "移動至列表頂部"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScrollToTopFabPreview() {
    ScrollToTopFab(
        showFab = true,
        onClick = {}
    )
}