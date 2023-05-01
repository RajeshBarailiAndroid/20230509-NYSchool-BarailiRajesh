package com.jp.nycschools.network

import com.jp.nycschools.model.School
import com.jp.nysandroidapp.ui.model.Sat
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SchoolRepository @Inject constructor(private val schoolApi: SchoolApi) {

    suspend fun getSchool(): List<School> {
        return schoolApi.getSchools()
    }
    suspend fun getSat(): List<Sat> {
        return schoolApi.getSat()


    }
}


