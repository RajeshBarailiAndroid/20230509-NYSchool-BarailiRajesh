package com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose

import TabLayout
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
    val LATTITUDE="latitude"
    val LONGITUDE="longitude"
    val ADDRESS="address"
    NavHost(
        navController = navController,
        startDestination = Destination.HomeScreen.route
    ) {
        composable(route = Destination.HomeScreen.route) {
            TabLayout(navController, schoolVieModel, satViewModel)
        }
        composable(route = Destination.HomeScreen.route) {
            SatScore(navController, satViewModel)
        }

        composable(
            route = Destination.MapScreen.route,
            arguments = listOf(
                navArgument(LATTITUDE) { type = NavType.FloatType },
                navArgument(LONGITUDE) { type = NavType.FloatType } ,
                navArgument(ADDRESS) { type = NavType.StringType }
            )
        ) {
            val latitude = it.arguments?.getFloat(LATTITUDE) ?: 0f
            val longitude = it.arguments?.getFloat(LONGITUDE) ?: 0f
            val address = it.arguments?.getString(ADDRESS) ?:""
            MapScreen(latitude, longitude,address)
        }
    }
}

@Composable
fun SatScore(navController: NavHostController, satViewModel: SatViewModel) {




}




