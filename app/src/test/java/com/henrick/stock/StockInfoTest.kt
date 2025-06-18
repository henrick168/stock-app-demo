package com.henrick.stock

import androidx.compose.ui.graphics.Color
import com.henrick.stock.model.StockInfo
import com.henrick.stock.model.STOCK_DEFAULT_STRING
import org.junit.Assert.assertEquals
import org.junit.Test


class StockInfoTest {

    @Test
    fun `getStockDiffStringColor return Color Unspecified when change is defaultString`() {
        // Arrange
        val defaultString = STOCK_DEFAULT_STRING
        val isDarkTheme = true
        val stockInfo = StockInfo(
            code = "0053",
            name = "元大電子",
            tradeVolume = "7125",
            tradeValue = "699205",
            openingPrice = "98.60",
            highestPrice = "98.60",
            lowestPrice = "97.70",
            closingPrice = "98.40",
            change = defaultString,
            transaction = "114",
            monthlyAveragePrice = "98.27"
        )
        val expected = Color.Unspecified

        // Act
        val actual = stockInfo.getStockDiffStringColor(isDarkTheme)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getStockDiffStringColor return Color Unspecified when change is 0`() {
        // Arrange
        val defaultString = "0"
        val isDarkTheme = true
        val stockInfo = StockInfo(
            code = "0053",
            name = "元大電子",
            tradeVolume = "7125",
            tradeValue = "699205",
            openingPrice = "98.60",
            highestPrice = "98.60",
            lowestPrice = "97.70",
            closingPrice = "98.40",
            change = defaultString,
            transaction = "114",
            monthlyAveragePrice = "98.27"
        )
        val expected = Color.Unspecified

        // Act
        val actual = stockInfo.getStockDiffStringColor(isDarkTheme)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getStockDiffStringColor return DarkRed when change bigger then 0 in DarkTheme`() {
        // Arrange
        val isDarkTheme = true
        val stockInfo = StockInfo(
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
        )
        val expected = stockInfo.getColorRedByDarkTheme(isDarkTheme)

        // Act
        val actual = stockInfo.getStockDiffStringColor(isDarkTheme)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getStockDiffStringColor return LightRed when change bigger then 0 in LightTheme`() {
        // Arrange
        val isDarkTheme = false
        val stockInfo = StockInfo(
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
        )
        val expected = stockInfo.getColorRedByDarkTheme(isDarkTheme)

        // Act
        val actual = stockInfo.getStockDiffStringColor(isDarkTheme)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getStockDiffStringColor return DarkGreen when change smaller then 0 in DarkTheme`() {
        // Arrange
        val isDarkTheme = true
        val stockInfo = StockInfo(
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
        )
        val expected = stockInfo.getColorGreenByDarkTheme(isDarkTheme)

        // Act
        val actual = stockInfo.getStockDiffStringColor(isDarkTheme)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getStockDiffStringColor return LightGreen when change smaller then 0 in LightTheme`() {
        // Arrange
        val isDarkTheme = false
        val stockInfo = StockInfo(
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
        )
        val expected = stockInfo.getColorGreenByDarkTheme(isDarkTheme)

        // Act
        val actual = stockInfo.getStockDiffStringColor(isDarkTheme)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getClosingPriceStringColor return Color Unspecified when closingPrice is defaultString`() {
        // Arrange
        val defaultString = STOCK_DEFAULT_STRING
        val isDarkTheme = true
        val stockInfo = StockInfo(
            code = "0050",
            name = "元大台灣50",
            closingPrice = defaultString,
            monthlyAveragePrice = "184.16"
        )
        val expected = Color.Unspecified

        // Act
        val actual = stockInfo.getClosingPriceStringColor(isDarkTheme)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getClosingPriceStringColor return Color Unspecified when monthlyAveragePrice is defaultString`() {
        // Arrange
        val defaultString = STOCK_DEFAULT_STRING
        val isDarkTheme = true
        val stockInfo = StockInfo(
            code = "0050",
            name = "元大台灣50",
            closingPrice = "184.50",
            monthlyAveragePrice = defaultString
        )
        val expected = Color.Unspecified

        // Act
        val actual = stockInfo.getClosingPriceStringColor(isDarkTheme)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getClosingPriceStringColor return DarkRed when closingPrice bigger then monthlyAveragePrice in DarkTheme`() {
        // Arrange
        val isDarkTheme = true
        val stockInfo = StockInfo(
            code = "0050",
            name = "元大台灣50",
            closingPrice = "200.00",
            monthlyAveragePrice = "184.16"
        )
        val expected = stockInfo.getColorRedByDarkTheme(isDarkTheme)

        // Act
        val actual = stockInfo.getClosingPriceStringColor(isDarkTheme)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getClosingPriceStringColor return LightRed when closingPrice bigger then monthlyAveragePrice in LightTheme`() {
        // Arrange
        val isDarkTheme = false
        val stockInfo = StockInfo(
            code = "0050",
            name = "元大台灣50",
            closingPrice = "200.00",
            monthlyAveragePrice = "184.16"
        )
        val expected = stockInfo.getColorRedByDarkTheme(isDarkTheme)

        // Act
        val actual = stockInfo.getClosingPriceStringColor(isDarkTheme)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getClosingPriceStringColor return DarkGreen when closingPrice smaller then monthlyAveragePrice in DarkTheme`() {
        // Arrange
        val isDarkTheme = true
        val stockInfo = StockInfo(
            code = "0050",
            name = "元大台灣50",
            closingPrice = "100.00",
            monthlyAveragePrice = "184.16"
        )
        val expected = stockInfo.getColorGreenByDarkTheme(isDarkTheme)

        // Act
        val actual = stockInfo.getClosingPriceStringColor(isDarkTheme)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getClosingPriceStringColor return LightGreen when closingPrice smaller then monthlyAveragePrice in LightTheme`() {
        // Arrange
        val isDarkTheme = false
        val stockInfo = StockInfo(
            code = "0050",
            name = "元大台灣50",
            closingPrice = "100.00",
            monthlyAveragePrice = "184.16"
        )
        val expected = stockInfo.getColorGreenByDarkTheme(isDarkTheme)

        // Act
        val actual = stockInfo.getClosingPriceStringColor(isDarkTheme)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getOpeningPriceString successful when return correct string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            openingPrice = "995.00",
        )
        val expected = "995.00".trim().toDoubleOrNull().toString()

        // Act
        val actual = stockInfo.getOpeningPriceString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getOpeningPriceString successful when return default string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
        )
        val expected = STOCK_DEFAULT_STRING

        // Act
        val actual = stockInfo.getOpeningPriceString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getClosingPriceString successful when return correct string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            closingPrice = "980.00",
        )
        val expected = "980.00".trim().toDoubleOrNull().toString()

        // Act
        val actual = stockInfo.getClosingPriceString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getHighestPriceString successful when return correct string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            highestPrice = "980.00",
        )
        val expected = "980.00".trim().toDoubleOrNull().toString()

        // Act
        val actual = stockInfo.getHighestPriceString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getLowestPriceString successful when return correct string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            lowestPrice = "980.00",
        )
        val expected = "980.00".trim().toDoubleOrNull().toString()

        // Act
        val actual = stockInfo.getLowestPriceString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getChangePriceString successful when return correct string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            change = "-10.0000",
        )
        val expected = "-10.0000".trim().toDoubleOrNull().toString()

        // Act
        val actual = stockInfo.getChangePriceString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getMonthlyAvgPriceString successful when return correct string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            monthlyAveragePrice = "984.61",
        )
        val expected = "984.61".trim().toDoubleOrNull().toString()

        // Act
        val actual = stockInfo.getMonthlyAvgPriceString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getTransactionString successful when return correct string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            transaction = "41985",
        )
        val expected = "41985".trim()

        // Act
        val actual = stockInfo.getTransactionString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getTransactionString successful when return default string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
        )
        val expected = STOCK_DEFAULT_STRING

        // Act
        val actual = stockInfo.getTransactionString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getTradeVolumeString successful when return correct string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            tradeVolume = "25939071",
        )
        val expected = "25939071".trim()

        // Act
        val actual = stockInfo.getTradeVolumeString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getTradeValueString successful when return correct string`() {
        // Arrange
        val expected = "25540.09M"
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            tradeValue = "25540092915",
        )

        // Act
        val actual = stockInfo.getTradeValueString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getTradeValueString successful when return default string`() {
        // Arrange
        val expected = STOCK_DEFAULT_STRING
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
        )

        // Act
        val actual = stockInfo.getTradeValueString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getTradeValueString failure and throw error string`() {
        // Arrange
        val expected = "Invalid Number."
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            tradeValue = "ERROR",
        )

        // Act
        val actual = stockInfo.getTradeValueString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getPeRatioString successful when return correct string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            peRatio = "21.66",
        )
        val expected = "21.66".trim().toDoubleOrNull().toString()

        // Act
        val actual = stockInfo.getPeRatioString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getPbRatioString successful when return correct string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            pbRatio = "5.93"
        )
        val expected = "5.93".trim().toDoubleOrNull().toString()

        // Act
        val actual = stockInfo.getPbRatioString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getDividendYieldString successful when return correct string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
            dividendYield = "1.73",
        )
        val expected = "1.73%"

        // Act
        val actual = stockInfo.getDividendYieldString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getDividendYieldString successful when return default string`() {
        // Arrange
        val expected = STOCK_DEFAULT_STRING
        val stockInfo = StockInfo(
            code = "2330",
            name = "台積電",
        )

        // Act
        val actual = stockInfo.getDividendYieldString()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `getStockInfoDialogTitle successful when return correct string`() {
        // Arrange
        val stockInfo = StockInfo(
            code = "0050",
            name = "元大台灣50")
        val expected = "(${stockInfo.code})${stockInfo.name}"

        // Act
        val actual = stockInfo.getStockInfoDialogTitle()

        // Assert
        assertEquals(expected, actual)
    }
}