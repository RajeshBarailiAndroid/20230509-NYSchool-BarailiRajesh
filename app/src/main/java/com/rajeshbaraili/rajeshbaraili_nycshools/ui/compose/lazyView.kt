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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jp.nycschoolapp.util.Response
import com.jp.nycschools.viewmodel.SatViewModel
import com.jp.nysandroidapp.ui.model.Sat
import com.rajeshbaraili.rajeshbaraili_nycshools.R
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.CircularProgressBar
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.ErrorMsg

@Composable
fun ItemUi(sat: Sat) {
    var expand by remember { (mutableStateOf(false)) }
    Surface(
        color = MaterialTheme.colors.surface, modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(shape = RoundedCornerShape(10))
    ) {
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
                        text = sat.school_name, style = MaterialTheme.typography.subtitle2.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }


                IconButton(onClick = { expand = !expand }) {
                    Icon(
                        painter = if (expand) painterResource(id = R.drawable.arrow_up) else painterResource(
                            id = R.drawable.arrow_down
                        ), contentDescription = null
                    )
                }
            }
            if (expand) {
                Column(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    val list = listOf(
                        "Number Of Test Takers :- " to sat.num_of_sat_test_takers,
                        "Critical Reading Average Score :-" to sat.sat_critical_reading_avg_score,
                        "SAT Writing Average Score:- " to sat.sat_writing_avg_score,
                        "Math Average Score:- " to sat.sat_math_avg_score
                    )

                    list.forEachIndexed { index, pair ->
                        Row(modifier = Modifier.padding(vertical = 9.dp)) {
                            Text(
                                text = list[index].first,
                                style = MaterialTheme.typography.body2.copy(
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                            //set text=data  if data null set text N/A
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
fun satScreen() {
    val viewModel: SatViewModel = viewModel()
    var response = viewModel.sat.observeAsState().value
    when (response) {
        is Response.Loading -> {
            CircularProgressBar()
        }
        is Response.Success -> {
            loadDataSat(response)
        }
        is Response.Error -> {
            ErrorMsg("SAT")
        }
        else -> { CircularProgressBar()}
    }


}

@Composable
fun loadDataSat(response: Response.Success<List<Sat>>) {
    LazyColumn() {
        items(response.data!!) { index ->
            ItemUi(index)
        }

    }
}






