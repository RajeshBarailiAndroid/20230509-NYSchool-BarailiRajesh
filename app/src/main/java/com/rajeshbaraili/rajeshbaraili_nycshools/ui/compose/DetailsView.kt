package com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jp.nycschoolapp.util.Response
import com.jp.nycschools.model.School
import com.jp.nycschools.network.SchoolApi
import com.jp.nycschools.network.SchoolRepository
import com.jp.nycschools.viewmodel.SatViewModel
import com.jp.nycschools.viewmodel.SchoolViewModel
import com.jp.nysandroidapp.ui.model.Sat
import com.rajeshbaraili.rajeshbaraili_nycshools.R
import javax.inject.Inject


@Composable
fun SatView(
    navController: NavHostController,
    satViewModel: SatViewModel,
    schoolVieModel: SchoolViewModel

) {

    var satResponse = satViewModel.sat.observeAsState().value
    var school = schoolVieModel.school.observeAsState().value
    if (satResponse != null) {
        when (satResponse) {
            is Response.Loading -> {
                CircularProgressBar()
            }

            is Response.Success -> {
                if (school != null) {
                    Load(satResponse, navController, school)
                }
            }

            is Response.Error -> {
                ErrorMsg("SAT")
            }
        }
    }


}

@Composable
fun Load(
    satResponse: Response.Success<List<Sat>>,
    navController: NavHostController,
    school: School,
    ) {
    var listSat = satResponse.data
    var position = school.dbn
    var sat = listSat.filter { it.dbn == position }
    if (sat.isNotEmpty()) {
        //HomeScreen()
        Details(navController, school, sat)
    } else {
        var sat = listOf(Sat("1", "0", "0", "0", "0", "0"))
        Details(
            navController,
            school,
            sat

        )
    }
}


@Composable
fun Details(
    navController: NavHostController,
    school: School,
    sat: List<Sat>

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = (MaterialTheme.colors.surface),
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

        content = {
            it.calculateTopPadding()
            DetailsView(school, sat)
        }
    )
}

@Composable
fun DetailsView(
    school: School, list: List<Sat>

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
                text = school.schoolName,
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
                    item {
                        InfoCard(title = "Math", value = list[0].satMathAvgScore)
                        Spacer(modifier = Modifier.width(9.dp))
                        InfoCard(title = "Reading", value = list[0].satWritingAvgScore)
                        Spacer(modifier = Modifier.width(9.dp))
                        InfoCard(title = "Writing", value = list[0].satReadingAvgScore)
                    }
                }
            }
        }
        item {
            school.apply {

                Spacer(modifier = Modifier.height(24.dp))
                Title(title = "School Info (Rate)")
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    item {
                        school?.graduationRate?.let { InfoCard(title = "Graduation", value = it) }
                        Spacer(modifier = Modifier.width(9.dp))
                        school?.collegeCareerRate?.let { InfoCard(title = "Career", value = it) }
                        Spacer(modifier = Modifier.width(9.dp))
                        school?.pctStuSafe?.let { InfoCard(title = "Stu. Safe", value = it) }

                    }
                }
            }
        }

        // Overview
        item {
            school.apply {
                Spacer(modifier = Modifier.height(24.dp))
                Title(title = "Overview")
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = school.overviewParagraph,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.text),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start
                )
            }
        }



        item {
            school.apply {
                Spacer(modifier = Modifier.height(24.dp))
                Title(title = "GPS Map")
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    item {
                        Spacer(modifier = Modifier.width(9.dp))
                        ImageClick(school.location)


                    }
                }
            }
        }

    }
}

@Composable
fun ImageClick(location: String) {
    var context = LocalContext.current
    Image(
        painter = painterResource(R.drawable.map),
        contentDescription = "Map",
        modifier = Modifier
            .size(50.dp)
            .clickable {
                mapOpen(context = context, location)
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
