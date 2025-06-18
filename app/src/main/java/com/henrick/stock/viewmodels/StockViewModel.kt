package com.henrick.stock.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henrick.stock.repository.StockRepository
import com.henrick.stock.model.StockDay
import com.henrick.stock.model.StockDayAvg
import com.henrick.stock.model.StockInfo
import com.henrick.stock.model.StockRatio
import com.henrick.stock.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val stockRepository: StockRepository,
) : ViewModel() {

    private val TAG = StockRepository::class.java.simpleName

    private var queryString = ""

    // 完整資料清單
    private val _fullStockInfoLists = MutableStateFlow<List<StockInfo>>(emptyList())

    // 經過處理後的清單資訊
    private val _stockInfoLists = MutableStateFlow<List<StockInfo>>(emptyList())
    val stockInfoLists: StateFlow<List<StockInfo>> = _stockInfoLists

    private val _sortedByDesc = MutableStateFlow(true)
    val sortedByDesc: StateFlow<Boolean> = _sortedByDesc

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val dataList: List<StockInfo>) : UiState()
        data class Error(val errorMessage: String) : UiState()
    }

    init {
        //viewModelScope.launch {  }
        fetchData()
    }

    fun fetchData() {
        if (!NetworkUtils.isNetworkAvailable(applicationContext)) {
            _uiState.value = UiState.Error("No network connection.")
        } else {
            //Log.d(TAG, "fetchData starting")
            viewModelScope.launch {
                try {
                    _uiState.value = UiState.Loading
                    combine(
                        stockRepository.getStockDayLists(),
                        stockRepository.getStockDayAvgLists(),
                        stockRepository.getStockRatioLists(),
                    ) { day, dayAvg, dayRatio ->
                        mergeData(day, dayAvg, dayRatio)
                    }.collect { result ->
                        //Log.e(TAG, "result: ${result.size}")
                        _fullStockInfoLists.value = result
                        queryAndSortedStockLists(queryString)
                        _uiState.value = UiState.Success(result)
                    }
                } catch (e: IOException) {
                    //Log.d(TAG, "UiState.Error -> 網路錯誤：${e.message ?: "Unknown Error"}")
                    _uiState.value = UiState.Error(e.message ?: "Unknown Error")
                } catch (e: Exception) {
                    //Log.d(TAG, "UiState.Error -> 業務錯誤：${e.message ?: "Unknown Error"}")
                    _uiState.value = UiState.Error(e.message ?: "Unknown Error")
                }
            }
        }
    }

    private fun mergeData(
        dayList: List<StockDay>,
        dayAvgList: List<StockDayAvg>,
        dayRatioList: List<StockRatio>,
    ): List<StockInfo> {
        //Log.e(TAG, "mergeData: starting")
        //Log.e(TAG, "mergeData -> day: ${dayList.size}, avg: ${dayAvgList.size}, ratio: ${dayRatioList.size},")
        val mergeMap = mutableMapOf<String, StockInfo>()
        dayList.forEach {
            mergeMap[it.code] = StockInfo(
                it.code,
                name = it.name,
                tradeVolume = it.tradeVolume,
                tradeValue = it.tradeValue,
                openingPrice = it.openingPrice,
                highestPrice = it.highestPrice,
                lowestPrice = it.lowestPrice,
                closingPrice = it.closingPrice,
                change = it.change,
                transaction = it.transaction,
            )
        }

        dayAvgList.forEach {
            mergeMap[it.code] = mergeMap[it.code]?.copy(monthlyAveragePrice = it.monthlyAveragePrice)
                ?: StockInfo(it.code, it.name, it.closingPrice, it.monthlyAveragePrice)
        }

        dayRatioList.forEach {
            mergeMap[it.code] = mergeMap[it.code]?.copy(
                peRatio = it.peRatio,
                pbRatio = it.pbRatio,
                dividendYield = it.dividendYield
            ) ?: StockInfo(it.code, it.name, it.peRatio, it.pbRatio, it.dividendYield)
        }
        return mergeMap.values.toList()
    }

    fun setStockListsSortedByAsc() {
        _sortedByDesc.value = false
        _stockInfoLists.value = _stockInfoLists.value.sortedBy { it.code }
    }

    fun setStockListsSortedByDesc() {
        _sortedByDesc.value = true
        _stockInfoLists.value = _stockInfoLists.value.sortedByDescending { it.code }
    }

    fun queryAndSortedStockLists(query: String) {
        // filter list
        queryString = query
        _stockInfoLists.value = _fullStockInfoLists.value.filter {
            it.code.contains(query) || it.name.contains(query, false)
        }

        // sorted list
        if (sortedByDesc.value) {
            setStockListsSortedByDesc()
        } else {
            setStockListsSortedByAsc()
        }
    }

}

// 加入 Hilt 後，不再需要 Factory 方法.
/*class StockViewModelFactory(
    private val appContext: Context,
    private val stockRepository: StockRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StockViewModel(appContext, stockRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/