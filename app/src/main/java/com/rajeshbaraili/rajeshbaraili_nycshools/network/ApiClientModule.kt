package com.jp.nycschools.network

import com.rajeshbaraili.rajeshbaraili_nycshools.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClientModule {
    var baseUrl="https://data.cityofnewyork.us/"
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesSchoolApi(retrofit: Retrofit): SchoolApi =
        retrofit.create(SchoolApi::class.java)


}