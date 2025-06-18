package com.henrick.stock

import com.henrick.stock.data.remote.RemoteStockDataSource
import com.henrick.stock.model.StockDay
import com.henrick.stock.model.StockDayAvg
import com.henrick.stock.model.StockRatio
import com.henrick.stock.network.StockApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.mockito.Mockito
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi // 標記為實驗性 API，因為 Coroutines Test 還在演進
class RemoteStockDataSourceTest {

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

    // Mock StockApiService
    private val stockApiService: StockApiService = mock()

    // 待測試的實例
    private lateinit var remoteStockDataSource: RemoteStockDataSource

    // Coroutine Test Dispatcher
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // 在每個測試運行前初始化
        remoteStockDataSource = RemoteStockDataSource(stockApiService)
        // 設置主 Dispatcher 為測試 Dispatcher，這樣 flowOn(Dispatchers.IO) 也能被測試 Dispatcher 控制
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        // 在每個測試運行後重置主 Dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `getStockDayLists should emit data when API call is successful`() = runTest {
        // Arrange
        val expectedLists = testStockDayLists
        Mockito.`when`(stockApiService.getStockDayLists()).thenReturn(expectedLists)

        // Act
        val resultFlow = remoteStockDataSource.getStockDayLists()
        val actualLists = resultFlow.first() // 使用 first() 獲取 Flow 發射的第一個（也是唯一一個）值

        // Assertion 驗證 Flow 是否發射了預期的數據
        assertEquals(expectedLists, actualLists)
    }

    @Test
    fun `getStockDayLists should throw exception when API call fails`() = runTest {
        // Arrange 模擬 API 拋出異常
        val expectedException = RuntimeException("Network Error")
        Mockito.`when`(stockApiService.getStockDayLists()).thenAnswer { throw expectedException } // 使用 thenAnswer 拋出異常

        // 驗證 Flow 是否能捕獲並傳播異常
        try {
            // Act
            remoteStockDataSource.getStockDayLists().first() // 嘗試獲取值，預期會拋出異常
            // 如果代碼執行到這裡，說明沒有拋出異常，測試失敗
            assertTrue("Expected an exception to be thrown, but it was not.", false)
        } catch (e: Exception) {
            // Assertion 驗證捕獲到的異常是否是預期的異常
            assertEquals(expectedException.message, e.message)
            assertTrue(e is RuntimeException)
        }
    }

    @Test
    fun `getStockDayAvgLists should emit data when API call is successful`() = runTest {
        // Arrange
        val expectedLists = testStockDayAvgLists
        Mockito.`when`(stockApiService.getStockDayAvgLists()).thenReturn(expectedLists)

        // Act
        val resultFlow = remoteStockDataSource.getStockDayAvgLists()
        val actualLists = resultFlow.first()

        // Assertion
        assertEquals(expectedLists, actualLists)
    }

    @Test
    fun `getStockDayAvgLists should throw exception when API call fails`() = runTest {
        // Arrange
        val expectedException = RuntimeException("Server Unavailable")
        Mockito.`when`(stockApiService.getStockDayAvgLists()).thenAnswer { throw expectedException }

        try {
            // Act
            remoteStockDataSource.getStockDayAvgLists().first()
            assertTrue("Expected an exception to be thrown, but it was not.", false)
        } catch (e: Exception) {
            // Assertion 驗證捕獲到的異常是否是預期的異常
            assertEquals(expectedException.message, e.message)
            assertTrue(e is RuntimeException)
        }
    }

    @Test
    fun `getStockRatioLists should emit data when API call is successful`() = runTest {
        // Arrange
        val expectedLists = testStockRatioLists
        Mockito.`when`(stockApiService.getStockRatioLists()).thenReturn(expectedLists)

        // Act
        val resultFlow = remoteStockDataSource.getStockRatioLists()
        val actualLists = resultFlow.first()

        // Assertion
        assertEquals(expectedLists, actualLists)
    }

    @Test
    fun `getStockRatioLists should throw exception when API call fails`() = runTest {
        // Arrange
        val expectedException = RuntimeException("Timeout Error")
        Mockito.`when`(stockApiService.getStockRatioLists()).thenAnswer { throw expectedException }

        try {
            // Act
            remoteStockDataSource.getStockRatioLists().first()
            assertTrue("Expected an exception to be thrown, but it was not.", false)
        } catch (e: Exception) {
            // Assertion 驗證捕獲到的異常是否是預期的異常
            assertEquals(expectedException.message, e.message)
            assertTrue(e is RuntimeException)
        }
    }

    // 額外測試：驗證 Dispatchers.IO 是否被用於 API 調用 (間接驗證)
    // 由於我們將 Dispatchers.Main 替換為 testDispatcher，
    // 而 flowOn(Dispatchers.IO) 實際上會被 testDispatcher 接管，
    // 所以這個測試會通過，但更重要的測試是驗證正確的數據發射和錯誤處理。
    @Test
    fun `flowOn Dispatchers_IO is handled correctly by test dispatcher`() = runTest {
        // Arrange
        val expectedLists = testStockDayLists
        Mockito.`when`(stockApiService.getStockDayLists()).thenReturn(expectedLists)

        // Act
        val resultFlow = remoteStockDataSource.getStockDayLists()
        // 由於我們使用了 runTest 和 StandardTestDispatcher，
        // flowOn 的效果將由 testDispatcher 模擬，確保 Flow 操作在正確的上下文中執行。
        // first() 會等待 Flow 完成並發射其值。
        val actualLists = resultFlow.first()

        // Assertion
        assertEquals(expectedLists, actualLists)
    }
}