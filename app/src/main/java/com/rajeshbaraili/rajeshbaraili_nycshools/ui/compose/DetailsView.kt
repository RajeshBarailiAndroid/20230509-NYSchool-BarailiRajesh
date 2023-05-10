package com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jp.nycschoolapp.util.Response
import com.jp.nycschools.viewmodel.SatViewModel
import com.jp.nysandroidapp.ui.model.Sat
import com.rajeshbaraili.rajeshbaraili_nycshools.R
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.theme.backCard


@Composable
fun SatView(
    navController: NavHostController,
    satViewModel: SatViewModel,
    id: String,
    schoolName: String,
    overview: String,
    location: String,
    graduationRate: String,
    careerRate: String,
    stuSafe: String,


    ) {
    var response = satViewModel.sat.observeAsState().value

    when (response) {
        is Response.Loading -> {
            CircularProgressBar()
        }

        is Response.Success -> {
            Load(response, navController, id,schoolName,overview, location,graduationRate,careerRate,stuSafe)
        }

        is Response.Error -> {
            ErrorMsg("SAT")
        }

        else -> {
            CircularProgressBar()
        }
    }


}

@Composable
fun Load(
    response: Response.Success<List<Sat>>,
    navController: NavHostController,
    id: String,
    schoolName: String,
    overview: String,
    location: String,
    graduationRate: String,
    careerRate: String,
    stuSafe: String,

    ) {
    var listSat = response.data
    var position = id
    var sat = listSat.filter { it.dbn == id }
    if (sat.isNotEmpty()) {
       //HomeScreen()
       Details(navController, id,sat,schoolName,overview,location,graduationRate,careerRate,stuSafe)
    } else {
        var sat = listOf(Sat("1", "0", "0", "0", "0", "0"))
      //  chartPage(sat = sat, navController = navController)

        Details(
            navController,
            id,
            sat,
            schoolName,
            overview,
            location,
            graduationRate,
            careerRate,
            stuSafe
        )
    }
}
@Composable
fun chartPage(sat: List<Sat>, navController: NavHostController) {
Column(modifier = Modifier
    .fillMaxWidth()
    .background(backCard)) {

}
}

@Composable
fun Details(
    navController: NavHostController,
    id: String,
    sat: List<Sat>,
    schoolName: String,
    overview: String,
    location: String,
    graduationRate: String,
    careerRate: String,
    stuSafe: String,

    ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                backgroundColor = MaterialTheme.colors.background,
                contentColor =(MaterialTheme.colors.surface),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .clickable {
                                navController.navigateUp()
                            },
                        tint = MaterialTheme.colors.surface
                    )
                }
            )
        },

        content = {it.calculateTopPadding()
            DetailsView(id,sat,schoolName,overview,location,graduationRate,careerRate,stuSafe)
        }
    )
}

@Composable
fun DetailsView(
    id: String,
    list: List<Sat>,
    schoolName: String,
    overview: String,
    location: String,
    graduationRate: String,
    careerRate: String,
    stuSafe: String
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
            .padding(horizontal = 10.dp)
            .padding(vertical = 20.dp)
            .padding(top = 10.dp)
    ) {

        item {
            Text(
                text =schoolName,
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(id = R.color.text),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center
            )
        }
        // Average Sat Scores
        item {
            list.apply {
                Spacer(modifier = Modifier.height(24.dp))
                Title(title = "Average Sat Scores")
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    item{ InfoCard(title = "Math", value = list.get(0).sat_math_avg_score)
                        Spacer(modifier = Modifier.width(9.dp))
                        InfoCard(title = "Reading", value = list.get(0).sat_writing_avg_score)
                        Spacer(modifier = Modifier.width(9.dp))
                        InfoCard(title = "Writing", value = list.get(0).sat_critical_reading_avg_score)}
                }
            }
        }
        item {
            list.apply {

                Spacer(modifier = Modifier.height(24.dp))
                Title(title = "School Info (Rate)")
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    item{ InfoCard(title = "Graduation", value = graduationRate)
                        Spacer(modifier = Modifier.width(9.dp))
                        InfoCard(title = "Career", value = careerRate)
                        Spacer(modifier = Modifier.width(9.dp))
                        InfoCard(title = "Stu. Safe", value = stuSafe)}
                }
            }
        }

        // Overview
        item {
            list.apply {


                Spacer(modifier = Modifier.height(24.dp))
                Title(title = "Overview")
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text =overview,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.text),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start)
            }
        }



        item {
            list.apply {

                Spacer(modifier = Modifier.height(24.dp))
                Title(title = "GPS Map")
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    item{
                        Spacer(modifier = Modifier.width(9.dp))
                   ImageClick(location)



                    }
                }
            }
        }

    }
}

@Composable
fun ImageClick(location: String) {
var context= LocalContext.current
            Image(
                painter = painterResource(R.drawable.map),
                contentDescription = "Map",
                modifier = Modifier.size(50.dp).clickable {
                    mapOpen(context = context,location)
                }
            )



}
@Composable
fun Title(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 0.dp, 0.dp),
        color = colorResource(id = R.color.text),
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.W600,
        textAlign = TextAlign.Start
    )
}

