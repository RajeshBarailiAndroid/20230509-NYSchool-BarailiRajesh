package com.jp.nycschools.network

import com.jp.nycschools.model.School
import com.jp.nysandroidapp.ui.model.Sat
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolApi {
    @GET("resource/s3k6-pzi2.json")
    suspend fun getSchools(): List<School>
    @GET("  resource/f9bf-2cp4.json")
    suspend fun getSat(): List<Sat>

}