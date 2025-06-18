package com.henrick.stock.data.remote

import com.henrick.stock.data.StockDataSource
import com.henrick.stock.model.StockDay
import com.henrick.stock.model.StockDayAvg
import com.henrick.stock.model.StockRatio
import com.henrick.stock.network.StockApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class RemoteStockDataSource @Inject constructor(private val stockApiService: StockApiService) :
    StockDataSource {

    override suspend fun getStockDayLists(): Flow<List<StockDay>> = flow {
        val lists = stockApiService.getStockDayLists()
        emit(lists)
    }.flowOn(Dispatchers.IO)

    override suspend fun getStockDayAvgLists(): Flow<List<StockDayAvg>> = flow {
        val lists = stockApiService.getStockDayAvgLists()
        emit(lists)
    }.flowOn(Dispatchers.IO)

    override suspend fun getStockRatioLists(): Flow<List<StockRatio>> = flow {
        val lists = stockApiService.getStockRatioLists()
        emit(lists)
    }.flowOn(Dispatchers.IO)

}