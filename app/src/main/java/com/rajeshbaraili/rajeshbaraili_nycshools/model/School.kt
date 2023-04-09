package com.jp.nycschools.model

import com.google.gson.annotations.SerializedName

data class School(
    val dbn: String,
    val school_name: String,
    val phone_number: String,
    val total_students: String,
    val graduation_rate: String,
    val college_career_rate: String,
    val location: String,
    val fax_number: String,
    val school_email: String,

)
