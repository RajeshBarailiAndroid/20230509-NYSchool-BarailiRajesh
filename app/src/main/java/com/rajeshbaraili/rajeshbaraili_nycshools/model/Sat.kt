package com.jp.nysandroidapp.ui.model



data class Sat(
    val dbn: String,
    val num_of_sat_test_takers: String,
    val sat_critical_reading_avg_score: String,
    val sat_math_avg_score: String,
    val sat_writing_avg_score: String,
    val school_name: String
) {
    val testTakers: Int
        get() = num_of_sat_test_takers.toIntOrNull() ?: 0
}