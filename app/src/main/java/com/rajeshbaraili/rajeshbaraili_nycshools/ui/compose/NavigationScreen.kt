package com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jp.nycschools.model.School
import com.jp.nycschools.viewmodel.SatViewModel
import com.jp.nycschools.viewmodel.SchoolViewModel
import com.jp.nysandroidapp.ui.compose.SchoolScreen

@Composable
fun NavigationScreen() {
    // navigation
    var schoolVieModel: SchoolViewModel = viewModel()
    var satViewModel: SatViewModel = viewModel()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            //   HomeScreen(schoolVieModel,navController)
            SchoolScreen(schoolVieModel, navController)
        }
        composable(
            "SchoolInfo",
        ){
                SatView(navController, satViewModel,schoolVieModel)
        }

    }
}





