package com.jp.nycschools.model

import com.google.gson.annotations.SerializedName

data class School(
    val dbn: String,
    @SerializedName("school_name")
    val schoolName: String,
    @SerializedName("total_students")
    val totalStudents: String,
    @SerializedName("graduation_rate")
    val graduationRate: String,
    @SerializedName("college_career_rate")
    val collegeCareerRate: String,
    val location: String,
    @SerializedName("pct_stu_safe")
    val pctStuSafe:String,
    @SerializedName("overview_paragraph")
    val overviewParagraph:String

    ){
   val address: String
   get() = location.substringAfter(", ").substringBefore(" (")
    val totalStudent: Int
        get() = totalStudents.toIntOrNull() ?: 0
}
