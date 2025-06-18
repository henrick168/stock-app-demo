package com.henrick.stock.network
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NoNetworkException : IOException("No network connection available.")

open class NetworkStatusInterceptor @Inject constructor(
    private val networkChecker: NetworkChecker
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (networkChecker.isNetworkAvailable()) {
            val request = chain.request()
            try {
                val response = chain.proceed(request)
                if (!response.isSuccessful) {
                    // 處理 HTTP 錯誤碼
                    when (response.code) {
                        500 -> throw IOException("Internal Server Error") //伺服器內部錯誤
                        501 -> throw IOException("Not Implemented") //尚未實作
                        502 -> throw IOException("Bad Gateway") //錯誤閘道
                        503 -> throw IOException("Service Unavailable") //服務不可用
                        504 -> throw IOException("Gateway Timeout") //閘道逾時

                        400 -> throw IOException("Bad Request") //錯誤請求
                        401 -> throw IOException("Unauthorized") //未授權
                        403 -> throw IOException("Forbidden") //禁止
                        404 -> throw IOException("Not Found") //找不到
                        405 -> throw IOException("Method Not Allowed") //方法不允許
                        429 -> throw IOException("Too Many Requests") //過多請求
                        // 其他錯誤碼...
                        else -> throw IOException("Network request failed, error code: ${response.code}")
                    }
                }
                return response
            } catch (e: IOException) {
                // 處理網路連線錯誤
                throw IOException("Network connection failed. \n${e.message}")
            }
        } else {
            throw NoNetworkException()
        }
    }

}