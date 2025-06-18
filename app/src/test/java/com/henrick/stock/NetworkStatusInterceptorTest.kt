package com.henrick.stock

import com.henrick.stock.di.NetworkModule
import com.henrick.stock.network.NetworkChecker
import com.henrick.stock.network.NetworkStatusInterceptor
import com.henrick.stock.network.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.io.IOException


class NetworkStatusInterceptorTest {

    private lateinit var networkChecker: NetworkChecker
    private lateinit var chain: Interceptor.Chain
    private lateinit var request: Request

    private fun createInterceptor() = NetworkStatusInterceptor(networkChecker)

    @Before
    fun setUp() {
        networkChecker = mock()
        chain = mock()
        request = Request.Builder().url(NetworkModule.BASE_URL).build()
    }

    @Test
    fun `networkInterceptor with network connection and response successful`() {

        // Arrange
        Mockito.`when`(networkChecker.isNetworkAvailable()).thenReturn(true)
        val successfulResponse = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .build()

        // Act
        Mockito.`when`(chain.request()).thenReturn(request)
        Mockito.`when`(chain.proceed(request)).thenReturn(successfulResponse)
        val response = createInterceptor().intercept(chain)

        // Assert
        assertEquals(200, response.code)

    }

    private fun responseFailureWithErrorCodeAndMessage(code: Int, message: String) {
        // Arrange
        Mockito.`when`(networkChecker.isNetworkAvailable()).thenReturn(true)
        val failureResponse = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(code)
            .message(message)
            .build()

        // Act
        Mockito.`when`(chain.request()).thenReturn(request)
        Mockito.`when`(chain.proceed(request)).thenReturn(failureResponse)

        // Assert
        assertThrows(IOException::class.java) {
            createInterceptor().intercept(chain)
        }
    }

    @Test
    fun `networkInterceptor with network connection and response failure with code 500`() {

        val code = 500
        val message = "Internal Server Error"
        responseFailureWithErrorCodeAndMessage(code, message)

    }

    @Test
    fun `networkInterceptor with network connection and response failure with code 501`() {

        val code = 501
        val message = "Not Implemented"
        responseFailureWithErrorCodeAndMessage(code, message)

    }

    @Test
    fun `networkInterceptor with network connection and response failure with code 502`() {

        val code = 502
        val message = "Bad Gateway"
        responseFailureWithErrorCodeAndMessage(code, message)

    }

    @Test
    fun `networkInterceptor with network connection and response failure with code 503`() {

        val code = 503
        val message = "Service Unavailable"
        responseFailureWithErrorCodeAndMessage(code, message)

    }

    @Test
    fun `networkInterceptor with network connection and response failure with code 504`() {

        val code = 504
        val message = "Gateway Timeout"
        responseFailureWithErrorCodeAndMessage(code, message)

    }

    @Test
    fun `networkInterceptor with network connection and response failure with code 400`() {

        val code = 400
        val message = "Bad Request"
        responseFailureWithErrorCodeAndMessage(code, message)

    }

    @Test
    fun `networkInterceptor with network connection and response failure with code 401`() {

        val code = 401
        val message = "Unauthorized"
        responseFailureWithErrorCodeAndMessage(code, message)

    }

    @Test
    fun `networkInterceptor with network connection and response failure with code 403`() {

        val code = 403
        val message = "Forbidden"
        responseFailureWithErrorCodeAndMessage(code, message)

    }

    @Test
    fun `networkInterceptor with network connection and response failure with code 404`() {

        val code = 404
        val message = "Not Found"
        responseFailureWithErrorCodeAndMessage(code, message)

    }

    @Test
    fun `networkInterceptor with network connection and response failure with code 405`() {

        val code = 405
        val message = "Method Not Allowed"
        responseFailureWithErrorCodeAndMessage(code, message)

    }

    @Test
    fun `networkInterceptor with network connection and response failure with code 429`() {

        val code = 429
        val message = "Too Many Requests"
        responseFailureWithErrorCodeAndMessage(code, message)

    }

    @Test
    fun `networkInterceptor with network connection and response failure with unknown code`() {

        val code = 999
        val message = "Network request failed, error code: 999"
        responseFailureWithErrorCodeAndMessage(code, message)

    }

    @Test
    fun `networkInterceptor without network connection and throw NoNetworkException`() {

        // Arrange
        Mockito.`when`(networkChecker.isNetworkAvailable()).thenReturn(false)

        // Act
        Mockito.`when`(chain.request()).thenReturn(request)

        // Assert
        assertThrows(NoNetworkException::class.java) {
            createInterceptor().intercept(chain)
        }
    }

}