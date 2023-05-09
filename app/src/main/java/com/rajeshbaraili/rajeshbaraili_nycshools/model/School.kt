package com.jp.nycschools.model

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
    val pct_stu_safe:String,
    val overview_paragraph:String


    ){
   val address: String
   get() = location.substringAfter(", ").substringBefore(" (")
    val latitude: String
        get()= location.substringAfter("(").substringBefore(",").trim()
    val longitude: String
        get() = location.substringAfter(",").substringBefore(")").trim()
    val totalStudent: Int
        get() = total_students.toIntOrNull() ?: 0
}
