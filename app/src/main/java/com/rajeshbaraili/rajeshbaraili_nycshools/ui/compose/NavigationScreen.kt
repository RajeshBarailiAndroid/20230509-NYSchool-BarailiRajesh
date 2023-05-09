package com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose

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
            "sbnId/{id}/{schoolName}/{overview}/{location}/{graduationRate}/{careerRate}/{stuSafe}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("schoolName") { type = NavType.StringType },
                navArgument("overview") { type = NavType.StringType },
                navArgument("location") { type = NavType.StringType },
                navArgument("graduationRate") { type = NavType.StringType },
                navArgument("careerRate") { type = NavType.StringType },
                navArgument("stuSafe") { type = NavType.StringType },
                )
        ) {
            val id = it.arguments?.getString("id") ?: ""
            val schoolName = it.arguments?.getString("schoolName") ?: ""
            val overview = it.arguments?.getString("overview") ?: ""
            val location = it.arguments?.getString("location") ?: ""
            val graduationRate = it.arguments?.getString("graduationRate") ?: ""
            val careerRate = it.arguments?.getString("careerRate") ?: ""
            val stuSafe = it.arguments?.getString("stuSafe") ?: ""
            SatView(navController, satViewModel, id,schoolName,overview,location,graduationRate,careerRate,stuSafe)
        }

    }
}





