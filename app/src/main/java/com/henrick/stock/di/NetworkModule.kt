package com.henrick.stock.di

import android.content.Context
import com.henrick.stock.network.NetworkChecker
import com.henrick.stock.network.NetworkCheckerImpl
import com.henrick.stock.network.NetworkStatusInterceptor
import com.henrick.stock.network.StockApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val BASE_URL = "https://openapi.twse.com.tw/"
    private const val TIMEOUT_SECONDS = 20L

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkChecker(
        @ApplicationContext applicationContext: Context
    ): NetworkChecker {
        return NetworkCheckerImpl(applicationContext)
    }

    @Provides
    @Singleton
    fun provideNetworkErrorInterceptor(
        /*@ApplicationContext applicationContext: Context,*/
        networkChecker: NetworkChecker
    ): NetworkStatusInterceptor {
        return NetworkStatusInterceptor(networkChecker)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkStatusInterceptor: NetworkStatusInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): StockApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
        return retrofit.create(StockApiService::class.java)
    }

}