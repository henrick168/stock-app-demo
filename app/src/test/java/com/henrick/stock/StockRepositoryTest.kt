package com.henrick.stock

import android.content.Context
import com.henrick.stock.data.remote.RemoteStockDataSource
import com.henrick.stock.model.StockDay
import com.henrick.stock.model.StockDayAvg
import com.henrick.stock.model.StockRatio
import com.henrick.stock.network.StockApiService
import com.henrick.stock.repository.StockRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock


class StockRepositoryTest {

    private lateinit var context: Context
    private lateinit var stockApiService: StockApiService
    private lateinit var remoteStockDataSource: RemoteStockDataSource
    private lateinit var stockRepository: StockRepository

    // data
    private val testStockDayLists = flow {
        val list = listOf(
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
        emit(list)
    }
    private val testStockDayAvgLists = flow {
        val list = listOf(
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
        emit(list)
    }
    private val testStockRatioLists = flow {
        val list = listOf(
            StockRatio(
                code = "2330",
                name = "台積電",
                peRatio = "21.66",
                dividendYield = "1.73",
                pbRatio = "5.93"
            )
        )
        emit(list)
    }

    @Before
    fun setUp() {
        context = mock()
        stockApiService = mock()
        remoteStockDataSource = mock()
        stockRepository = StockRepository(remoteStockDataSource)
    }

    @Test
    fun `getStockDayLists returns success with stockDayLists`() = runTest {
        // Arrange
        Mockito.`when`(remoteStockDataSource.getStockDayLists()).thenReturn(testStockDayLists)

        // Act
        val result = stockRepository.getStockDayLists()

        // Assertion
        assertEquals(testStockDayLists, result)
    }

    @Test
    fun `getStockDayLists should throw exception when API call fails`() = runTest {
        // Arrange 模擬 API 拋出異常，使用 thenAnswer 拋出異常
        val expectedException = RuntimeException("Network Error")
        Mockito.`when`(remoteStockDataSource.getStockDayLists()).thenAnswer { throw expectedException }

        // 驗證 Flow 是否能捕獲並傳播異常
        try {
            // Act 嘗試獲取值，預期會拋出異常
            stockRepository.getStockDayLists().first()
            // 如果代碼執行到這裡，說明沒有拋出異常，測試失敗
            assertTrue("Expected an exception to be thrown, but it was not.", false)
        } catch (e: Exception) {
            // Assertion 驗證捕獲到的異常是否是預期的異常
            assertEquals(expectedException.message, e.message)
            assertTrue(e is RuntimeException)
        }

    }

    @Test
    fun `getStockDayAvgLists returns success with stockDayAvgLists`() = runTest {
        // Arrange
        Mockito.`when`(remoteStockDataSource.getStockDayAvgLists()).thenReturn(testStockDayAvgLists)

        // Act
        val result = stockRepository.getStockDayAvgLists()

        // Assertion
        assertEquals(testStockDayAvgLists, result)
    }

    @Test
    fun `getStockDayAvgLists should throw exception when API call fails`() = runTest {
        // Arrange 模擬 API 拋出異常，使用 thenAnswer 拋出異常
        val expectedException = RuntimeException("Server Unavailable")
        Mockito.`when`(remoteStockDataSource.getStockDayAvgLists()).thenAnswer { throw expectedException }

        // 驗證 Flow 是否能捕獲並傳播異常
        try {
            // Act 嘗試獲取值，預期會拋出異常
            stockRepository.getStockDayAvgLists().first()
            // 如果代碼執行到這裡，說明沒有拋出異常，測試失敗
            assertTrue("Expected an exception to be thrown, but it was not.", false)
        } catch (e: Exception) {
            // Assertion 驗證捕獲到的異常是否是預期的異常
            assertEquals(expectedException.message, e.message)
            assertTrue(e is RuntimeException)
        }
    }

    @Test
    fun `getStockRatioLists returns success with stockDayAvgLists`() = runTest {
        // Arrange
        Mockito.`when`(remoteStockDataSource.getStockRatioLists()).thenReturn(testStockRatioLists)

        // Act
        val result = stockRepository.getStockRatioLists()

        // Assertion
        assertEquals(testStockRatioLists, result)
    }

    @Test
    fun `getStockRatioLists should throw exception when API call fails`() = runTest {
        // Arrange 模擬 API 拋出異常，使用 thenAnswer 拋出異常
        val expectedException = RuntimeException("Timeout Error")
        Mockito.`when`(remoteStockDataSource.getStockRatioLists()).thenAnswer { throw expectedException }

        // 驗證 Flow 是否能捕獲並傳播異常
        try {
            // Act 嘗試獲取值，預期會拋出異常
            stockRepository.getStockRatioLists().first()
            // 如果代碼執行到這裡，說明沒有拋出異常，測試失敗
            assertTrue("Expected an exception to be thrown, but it was not.", false)
        } catch (e: Exception) {
            // Assertion 驗證捕獲到的異常是否是預期的異常
            assertEquals(expectedException.message, e.message)
            assertTrue(e is RuntimeException)
        }
    }
}