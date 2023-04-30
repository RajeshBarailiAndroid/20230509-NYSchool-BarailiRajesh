package com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose

import TabLayout
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp.nycschools.viewmodel.SatViewModel
import com.jp.nycschools.viewmodel.SchoolViewModel
import com.rajeshbaraili.rajeshbaraili_nycshools.util.Destination


@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationScreen() {
    var schoolVieModel: SchoolViewModel = viewModel()
    var satViewModel: SatViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.HomeScreen.route
    ) {
        composable(route = Destination.HomeScreen.route) {
            TabLayout(navController, schoolVieModel, satViewModel)
        }
        composable(route = Destination.MapScreen.route) {
            MapScreen()
        }
    }
}




