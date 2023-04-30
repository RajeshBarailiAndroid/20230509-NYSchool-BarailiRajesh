package com.rajeshbaraili.rajeshbaraili_nycshools.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.NavigationScreen
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.theme.RajeshBarailiNYCShoolsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
lateinit var navController: NavHostController

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RajeshBarailiNYCShoolsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
             //call to tabLayout
                  // TabLayout()
                    NavigationScreen( )
                }
            }
        } }

}


