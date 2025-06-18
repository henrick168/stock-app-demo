package com.henrick.stock.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.henrick.stock.data.remote.RemoteStockDataSource
import com.henrick.stock.di.NetworkModule
import com.henrick.stock.model.StockDay
import com.henrick.stock.model.StockDayAvg
import com.henrick.stock.model.StockRatio
import com.henrick.stock.network.StockApiService
import com.henrick.stock.repository.StockRepository
import com.henrick.stock.ui.components.ScrollToTopFab
import com.henrick.stock.ui.components.StockTopAppBar
import com.henrick.stock.ui.theme.StockAppTheme
import com.henrick.stock.viewmodels.StockViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import retrofit2.Retrofit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockApp(stockViewModel: StockViewModel, modifier: Modifier = Modifier) {

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val showFab by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            StockTopAppBar(
                onFilterClick = {
                    showBottomSheet = true
                    coroutineScope.launch {
                        sheetState.show()
                    }
                },
            )
        },
        floatingActionButton = {
            ScrollToTopFab(
                showFab = showFab,
                onClick = { coroutineScope.launch { listState.scrollToItem(0) } })
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->

        // 使用 Container 統一加入 innerPadding.
        StockContentScreen(
            stockViewModel = stockViewModel,
            listState = listState,
            sheetState = sheetState,
            showBottomSheet = showBottomSheet,
            onDismissRequest = { showBottomSheet = false },
            onBottomSheetDescClick = {
                showBottomSheet = false
                stockViewModel.setStockListsSortedByDesc()
                coroutineScope.launch {
                    listState.scrollToItem(0)
                    sheetState.hide()
                }
            },
            onBottomSheetAscClick = {
                showBottomSheet = false
                stockViewModel.setStockListsSortedByAsc()
                coroutineScope.launch {
                    listState.scrollToItem(0)
                    sheetState.hide()
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    name = "Dark mode"
)
@Preview(showBackground = true)
@Composable
private fun StockAppPreview() {
    StockAppTheme {
        val context = LocalContext.current
        val fakeApiService = Retrofit.Builder().baseUrl(NetworkModule.BASE_URL).build()
            .create(StockApiService::class.java)
        val removeStockDataSource = RemoteStockDataSource(fakeApiService)
        val fakeRepository = object : StockRepository(removeStockDataSource) {
            override suspend fun getStockDayLists(): Flow<List<StockDay>> {
                return flowOf(
                    listOf(
                        StockDay(
                            code = "0050",
                            name = "元大台灣50",
                            tradeVolume = "7266932",
                            tradeValue = "1339869111",
                            openingPrice = "185.20",
                            highestPrice = "185.40",
                            lowestPrice = "183.85",
                            closingPrice = "184.50",
                            change = "-0.1500",
                            transaction = "15574"
                        ),
                        StockDay(
                            code = "0051",
                            name = "元大中型100",
                            tradeVolume = "32282",
                            tradeValue = "2481312",
                            openingPrice = "76.95",
                            highestPrice = "77.10",
                            lowestPrice = "76.65",
                            closingPrice = "76.65",
                            change = "0.0000",
                            transaction = "260"
                        ),
                        StockDay(
                            code = "0052",
                            name = "富邦科技",
                            tradeVolume = "158673",
                            tradeValue = "28980788",
                            openingPrice = "183.40",
                            highestPrice = "183.65",
                            lowestPrice = "181.85",
                            closingPrice = "182.60",
                            change = "-0.1000",
                            transaction = "666"
                        ),
                        StockDay(
                            code = "0053",
                            name = "元大電子",
                            tradeVolume = "7125",
                            tradeValue = "699205",
                            openingPrice = "98.60",
                            highestPrice = "98.60",
                            lowestPrice = "97.70",
                            closingPrice = "98.40",
                            change = "0.2000",
                            transaction = "114"
                        ),
                        StockDay(
                            code = "2330",
                            name = "台積電",
                            tradeVolume = "25939071",
                            tradeValue = "25540092915",
                            openingPrice = "995.00",
                            highestPrice = "995.00",
                            lowestPrice = "980.00",
                            closingPrice = "980.00",
                            change = "-10.0000",
                            transaction = "41985"
                        ),
                    )
                )
            }

            override suspend fun getStockDayAvgLists(): Flow<List<StockDayAvg>> {
                return flowOf(
                    listOf(
                        StockDayAvg(
                            code = "0050",
                            name = "元大台灣50",
                            closingPrice = "184.50",
                            monthlyAveragePrice = "184.16"
                        ),
                        StockDayAvg(
                            code = "0051",
                            name = "元大中型100",
                            closingPrice = "76.65",
                            monthlyAveragePrice = "77.36"
                        ),
                        StockDayAvg(
                            code = "0052",
                            name = "富邦科技",
                            closingPrice = "182.60",
                            monthlyAveragePrice = "182.15"
                        ),
                        StockDayAvg(
                            code = "0053",
                            name = "元大電子",
                            closingPrice = "98.40",
                            monthlyAveragePrice = "98.27"
                        ),
                        StockDayAvg(
                            code = "2330",
                            name = "台積電",
                            closingPrice = "980.00",
                            monthlyAveragePrice = "984.61"
                        ),
                    )
                )
            }

            override suspend fun getStockRatioLists(): Flow<List<StockRatio>> {
                return flowOf(
                    listOf(
                        StockRatio(
                            code = "2330",
                            name = "台積電",
                            peRatio = "21.66",
                            dividendYield = "1.73",
                            pbRatio = "5.93"
                        )
                    )
                )
            }
        }
        val stockViewModel = StockViewModel(context, fakeRepository)
        StockApp(stockViewModel)
    }
}