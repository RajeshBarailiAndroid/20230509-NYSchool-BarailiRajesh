package com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose

import TabLayout
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp.nycschools.viewmodel.SchoolViewModel
import com.rajeshbaraili.rajeshbaraili_nycshools.util.Destination


@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationScreen(navController: NavHostController, schv:SchoolViewModel) {

        val navController = rememberNavController()
    val viewModelSchool:SchoolViewModel = viewModel()
    //val viewModelSat = viewModel<SatViewModel>()

        NavHost(
            navController = navController,
            startDestination = Destination.TabLayout.route
        ) {
            composable(Destination.TabLayout.route) {

                TabLayout()
            }
           }
        }



    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun HomeScreen(navController: NavController) {
        // Your HomeScreen Composable function code goes here
       // TabLayout()
        Button(onClick = { navController.navigate("details") }) {
            Text("Go to Details Screen")
        }
    }

    @Composable
    fun DetailsScreen(navController: NavController) {
        // Your DetailsScreen Composable function code goes here
        Button(onClick = { navController.popBackStack() }) {
            Text("Go Back")
        }
    }

