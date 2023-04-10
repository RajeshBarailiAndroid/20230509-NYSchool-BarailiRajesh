package com.jp.nysandroidapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jp.nycschoolapp.util.Response
import com.jp.nycschools.model.School
import com.jp.nycschools.viewmodel.SchoolViewModel
import com.rajeshbaraili.rajeshbaraili_nycshools.R
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.CircularProgressBar
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.ErrorMsg
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.theme.backCard

@Composable
fun ItemUiSc(school: School) {
    var expand by remember { (mutableStateOf(false)) }
    Card(
        backgroundColor =Color.White,
        elevation = Dp(2F),
        modifier = Modifier.padding(all = 16.dp)
    )  {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 6.dp)
                ) {
                    Text(
                        text = "School Name", style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Text(
                        text = school.school_name, style = MaterialTheme.typography.subtitle2.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }

//click the arrow up/arrow down
                IconButton(onClick = { expand = !expand }) {
                    Icon(
                        painter = if (expand) painterResource(id = R.drawable.arrow_up) else painterResource(
                            id = R.drawable.arrow_down
                        ), contentDescription = null
                    )
                }
            }
            //expand
            if (expand) {
                Column(
                    modifier = Modifier
                        .background(color = backCard)
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    val list = listOf(
                        "Total Students :-  " to school.total_students,
                        "Graduation Rate :-  " to school.graduation_rate,
                        "College Career Rate:- " to school.college_career_rate,
                        "Location :- " to school.location,
                        "School Email :- " to school.school_email,
                        "Phone Number :- " to school.phone_number,
                        "Fax Number :- " to school.fax_number
                    )

                    list.forEachIndexed { index, pair ->
                        Row(modifier = Modifier.padding(vertical = 9.dp)) {
                            Text(
                                text = list[index].first,
                                style = MaterialTheme.typography.body2.copy(
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                            (list[index].second)?.let { Text(text = (list[index].second)) } ?: Text(
                                text = "N/A"
                            )

                        }

                    }
                }
            }
        }
    }
}

@Composable
fun schoolScreen() {
    val viewModel: SchoolViewModel = viewModel()
    var response = viewModel.schools.observeAsState().value
//loading,success and error condition
    when (response) {
        is Response.Loading -> {
            CircularProgressBar()
        }
        is Response.Success -> {

            loadData(response)

        }
        is Response.Error -> {
            ErrorMsg("School")
        }
        else -> { CircularProgressBar()}
    }


}

@Composable
fun loadData(response: Response.Success<List<School>>) {
    LazyColumn(Modifier.background(backCard)) {
        items(response.data!!) { index ->
            ItemUiSc(index)
        }

    }
}

