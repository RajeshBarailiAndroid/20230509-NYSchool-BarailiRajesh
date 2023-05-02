package com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose

import TabLayout
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp.nycschools.viewmodel.SatViewModel
import com.jp.nycschools.viewmodel.SchoolViewModel


@OptIn(ExperimentalPagerApi::class)
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
            TabLayout(navController, schoolVieModel, satViewModel)
        }
        //navigation to Sat graph View
        composable("sbnId/{id}",
            arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        )) {
            val id = it.arguments?.getString("id") ?:""
            SatView(navController,satViewModel,id)
        }

    }
}




