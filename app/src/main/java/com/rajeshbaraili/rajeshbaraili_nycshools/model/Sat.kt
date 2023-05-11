package com.jp.nysandroidapp.ui.model

import com.google.gson.annotations.SerializedName


data class Sat(
    val dbn: String,
    @SerializedName("num_of_sat_test_takers")
    val totalSatTakers: String,
    @SerializedName("sat_critical_reading_avg_score")
    val satReadingAvgScore: String,
    @SerializedName("sat_math_avg_score")
    val satMathAvgScore: String,
    @SerializedName("sat_writing_avg_score")
    val satWritingAvgScore: String,
    @SerializedName("school_name")
    val schoolName: String
) {
    val testTakers: Int
        get() = totalSatTakers.toIntOrNull() ?: 0
}