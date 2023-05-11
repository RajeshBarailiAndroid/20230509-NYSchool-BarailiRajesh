package com.rajeshbaraili.rajeshbaraili_nycshools

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestUiCompose {
    @get:Rule
    val composeUiTest= createAndroidComposeRule<MainActivity>()

    @Test
    fun testSatScore() {
  composeUiTest.onNodeWithText("progressLoader")

    }

}