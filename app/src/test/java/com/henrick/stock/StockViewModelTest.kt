package com.henrick.stock

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.henrick.stock.model.StockDay
import com.henrick.stock.model.StockDayAvg
import com.henrick.stock.model.StockInfo
import com.henrick.stock.model.StockRatio
import com.henrick.stock.network.NetworkCheckerImpl
import com.henrick.stock.network.NoNetworkException
import com.henrick.stock.repository.StockRepository
import com.henrick.stock.utils.NetworkUtils
import com.henrick.stock.viewmodels.StockViewModel
import com.henrick.stock.viewmodels.StockViewModel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class StockViewModelTest {

    @get:Rule
    // 確保 Coroutines 在 TestCoroutineDispatcher 上執行
    val mainDispatcherRule = MainDispatcherRule()

    private val context: Context = mock()
    private val stockRepository: StockRepository = mock()
    private lateinit var stockViewModel: StockViewModel

    // network
    private val connectivityManager: ConnectivityManager = mock()
    private val network: Network = mock()
    private val networkCapabilities: NetworkCapabilities = mock()
    private lateinit var networkChecker: NetworkCheckerImpl

    // data
    private val testStockDayLists = listOf(
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
    private val testStockDayAvgLists = listOf(
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
    private val testStockRatioLists = listOf(
        StockRatio(
            code = "2330",
            name = "台積電",
            peRatio = "21.66",
            dividendYield = "1.73",
            pbRatio = "5.93"
        )
    )
    private val testStockInfoLists = listOf(
        StockInfo(
            code = "0050",
            name = "元大台灣50",
            tradeVolume = "7266932",
            tradeValue = "1339869111",
            openingPrice = "185.20",
            highestPrice = "185.40",
            lowestPrice = "183.85",
            closingPrice = "184.50",
            change = "-0.1500",
            transaction = "15574",
            monthlyAveragePrice = "184.16"
        ),
        StockInfo(
            code = "0051",
            name = "元大中型100",
            tradeVolume = "32282",
            tradeValue = "2481312",
            openingPrice = "76.95",
            highestPrice = "77.10",
            lowestPrice = "76.65",
            closingPrice = "76.65",
            change = "0.0000",
            transaction = "260",
            monthlyAveragePrice = "77.36"
        ),
        StockInfo(
            code = "0052",
            name = "富邦科技",
            tradeVolume = "158673",
            tradeValue = "28980788",
            openingPrice = "183.40",
            highestPrice = "183.65",
            lowestPrice = "181.85",
            closingPrice = "182.60",
            change = "-0.1000",
            transaction = "666",
            monthlyAveragePrice = "182.15"
        ),
        StockInfo(
            code = "0053",
            name = "元大電子",
            tradeVolume = "7125",
            tradeValue = "699205",
            openingPrice = "98.60",
            highestPrice = "98.60",
            lowestPrice = "97.70",
            closingPrice = "98.40",
            change = "0.2000",
            transaction = "114",
            monthlyAveragePrice = "98.27"
        ),
        StockInfo(
            code = "2330",
            name = "台積電",
            tradeVolume = "25939071",
            tradeValue = "25540092915",
            openingPrice = "995.00",
            highestPrice = "995.00",
            lowestPrice = "980.00",
            closingPrice = "980.00",
            change = "-10.0000",
            transaction = "41985",
            monthlyAveragePrice = "984.61",
            peRatio = "21.66",
            dividendYield = "1.73",
            pbRatio = "5.93"
        ),
    )

    @Before
    fun setUp() {
        // network
        networkChecker = NetworkCheckerImpl(context)
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(
            connectivityManager
        )
        `when`(connectivityManager.activeNetwork).thenReturn(network)
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
    }

    @Test
    fun `fetchData return Success with correct UiState and lists and SortedByDescending`() = runTest {

        // Arrange
        // 1. 準備期望數據
        val expectedLists = testStockInfoLists.sortedByDescending { it.code }
        val expectedUiState = UiState.Success(expectedLists)

        // 2. 配置 Mock 對象的行為
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true)
        `when`(stockRepository.getStockDayLists()).thenReturn(flowOf(testStockDayLists))
        `when`(stockRepository.getStockDayAvgLists()).thenReturn(flowOf(testStockDayAvgLists))
        `when`(stockRepository.getStockRatioLists()).thenReturn(flowOf(testStockRatioLists))

        // 3. 創建被測試的 ViewModel
        stockViewModel = StockViewModel(context ,stockRepository)

        // Act (執行被測試的方法)
        stockViewModel.fetchData()
        stockViewModel.setStockListsSortedByDesc()
        stockViewModel.queryAndSortedStockLists("")
        val actualUiState = stockViewModel.uiState.value

        // Assert
        assertTrue(networkChecker.isNetworkAvailable())
        assertTrue(NetworkUtils.isNetworkAvailable(context))
        // 驗證 actualUiState 是 Success 類型
        assertTrue(actualUiState is UiState.Success)
        // 安全轉換為 Success 類型
        val successState = actualUiState as UiState.Success
        assertEquals(expectedLists, successState.dataList.sortedByDescending { it.code })

    }

    @Test
    fun `fetchData return Success with correct UiState and lists and SortedByAscending`() = runTest {

        // Arrange
        // 1. 準備期望數據
        val expectedLists = testStockInfoLists
        //val expectedUiState = UiState.Success(expectedLists)

        // 2. 配置 Mock 對象的行為
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true)
        `when`(stockRepository.getStockDayLists()).thenReturn(flowOf(testStockDayLists))
        `when`(stockRepository.getStockDayAvgLists()).thenReturn(flowOf(testStockDayAvgLists))
        `when`(stockRepository.getStockRatioLists()).thenReturn(flowOf(testStockRatioLists))

        // 3. 創建被測試的 ViewModel
        stockViewModel = StockViewModel(context ,stockRepository)

        // Act (執行被測試的方法)
        stockViewModel.fetchData()
        stockViewModel.setStockListsSortedByAsc()
        stockViewModel.queryAndSortedStockLists("")
        val actualUiState = stockViewModel.uiState.value

        // Assert
        assertTrue(networkChecker.isNetworkAvailable())
        assertTrue(NetworkUtils.isNetworkAvailable(context))
        // 驗證 actualUiState 是 Success 類型
        assertTrue(actualUiState is UiState.Success)
        // 安全轉換為 Success 類型
        val successState = actualUiState as UiState.Success
        assertEquals(expectedLists, successState.dataList.sortedBy { it.code })

    }

    @Test
    fun `fetchData return Error has no network with error message`() = runTest {
        val expected = "No network connection."
        `when`(connectivityManager.activeNetwork).thenReturn(null)

        // 3. 創建被測試的 ViewModel
        stockViewModel = StockViewModel(context ,stockRepository)
        val actualUiState = stockViewModel.uiState.value

        // Assert
        assertFalse(networkChecker.isNetworkAvailable())
        assertFalse(NetworkUtils.isNetworkAvailable(context))
        // 驗證 actualUiState 是 Success 類型
        assertTrue(actualUiState is UiState.Error)
        // 安全轉換為 Success 類型
        val errorState = actualUiState as UiState.Error
        assertEquals(expected, errorState.errorMessage)
    }

    @Test
    fun `fetchData return Error, invalid network with error message`() = runTest {
        val expected = "No network connection available."
        val networkConnected = true
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(networkConnected)
        `when`(stockRepository.getStockDayLists()).thenReturn(flow { throw NoNetworkException() })
        `when`(stockRepository.getStockDayAvgLists()).thenReturn(flowOf(testStockDayAvgLists))
        `when`(stockRepository.getStockRatioLists()).thenReturn(flowOf(testStockRatioLists))

        // 3. 創建被測試的 ViewModel
        stockViewModel = StockViewModel(context ,stockRepository)
        val actualUiState = stockViewModel.uiState.value

        // Assert
        assertTrue(networkChecker.isNetworkAvailable())
        assertTrue(NetworkUtils.isNetworkAvailable(context))
        // 驗證 actualUiState 是 Success 類型
        assertTrue(actualUiState is UiState.Error)
        // 安全轉換為 Success 類型
        val errorState = actualUiState as UiState.Error
        assertEquals(expected, errorState.errorMessage)

    }

    @Test
    fun `fetchData return Error, throw RuntimeException with error message`() = runTest {
        val expected = "RuntimeException!"
        val networkConnected = true
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(networkConnected)
        `when`(stockRepository.getStockDayLists()).thenReturn(flow { throw RuntimeException(expected) })
        `when`(stockRepository.getStockDayAvgLists()).thenReturn(flowOf(testStockDayAvgLists))
        `when`(stockRepository.getStockRatioLists()).thenReturn(flowOf(testStockRatioLists))

        // 3. 創建被測試的 ViewModel
        stockViewModel = StockViewModel(context ,stockRepository)
        val actualUiState = stockViewModel.uiState.value

        // Assert
        assertTrue(networkChecker.isNetworkAvailable())
        assertTrue(NetworkUtils.isNetworkAvailable(context))
        // 驗證 actualUiState 是 Success 類型
        assertTrue(actualUiState is UiState.Error)
        // 安全轉換為 Success 類型
        val errorState = actualUiState as UiState.Error
        assertEquals(expected, errorState.errorMessage)

    }

}

// 創建一個 JUnit Rule 來控制 Coroutine 的 Dispatcher，方便在單元測試中使用 TestCoroutineDispatcher
@ExperimentalCoroutinesApi
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher(),
    TestCoroutineScope by TestCoroutineScope(testDispatcher) {

    override fun starting(description: Description) {
        Dispatchers.resetMain()
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}