package com.jp.nysandroidapp.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jp.nycschoolapp.util.Response
import com.jp.nycschools.model.School
import com.jp.nycschools.viewmodel.SchoolViewModel
import com.rajeshbaraili.rajeshbaraili_nycshools.R
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.CircularProgressBar
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.ErrorMsg
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.theme.backCard

@Composable
fun ItemUiSc(school: School) {
    var navController: NavHostController = rememberNavController()
    var expand by remember { (mutableStateOf(false)) }
    Card(
        backgroundColor = Color.White,
        elevation = Dp(2F),
        modifier = Modifier.padding(all = 16.dp)
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
                        "School Email :- " to school.school_email,
                        "Phone Number :- " to school.phone_number,
                        "Fax Number :- " to school.fax_number,
                        "Location :- " to school.location
                    )

                    list.forEachIndexed { index, pair ->

                        if (index!=list.size-1){
                            Row(modifier = Modifier.padding(vertical = 9.dp)) {
                                Text(
                                    text = list[index].first,
                                    style = MaterialTheme.typography.body2.copy(
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                )
                                //set text=data  if data null set text N/A
                                (list[index].second)?.let { Text(text = (list[index].second)) }
                                    ?: Text(
                                        text = "N/A"
                                    )

                            }

                        }
                        else {
                            Column {
                                var input = list[index].second
                                val address = input.substringBefore("(").trim()
                                val coordinates =
                                    input.substringAfter("(").replace(")", "").split(",")
                                        .map { it.trim() }
                                Row(modifier = Modifier.padding(vertical = 9.dp)) {
                                    Text(
                                        text = list[index].first,
                                        style = MaterialTheme.typography.body2.copy(
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    )
                                    //set text=data  if data null set text N/A
                                    (list[index].second)?.let { Text(text = address) }
                                        ?: Text(
                                            text = "N/A"
                                        )
                                }
                                Row {
                                    Image(
                                        painter = painterResource(id = R.drawable.map),
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clickable {
                                             //   navController.navigate(Destination.MapScreen.route)
                                            },
                                        contentDescription = "map"
                                    )

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SchoolScreen() {
    val viewModel: SchoolViewModel = viewModel()
    var response = viewModel.schools.observeAsState().value
//loading,success and error condition
    when (response) {
        is Response.Loading -> {
            CircularProgressBar()
        }

        is Response.Success -> {

            LoadData(response)

        }

        is Response.Error -> {
            ErrorMsg("School")
        }

        else -> {
            CircularProgressBar()
        }
    }


}

@Composable
fun LoadData(response: Response.Success<List<School>>) {
    var searchQuery by remember { mutableStateOf("") }
    var listItems = response.data
    val filteredList = if (searchQuery.isEmpty()) {
        listItems
    } else {
        listItems.filter { it.school_name.contains(searchQuery, ignoreCase = true) }
    }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backCard),
            verticalAlignment = Alignment.CenterVertically

        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .background(backCard)
                    .padding(horizontal = 30.dp, vertical = 8.dp),

                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    backgroundColor = backCard
                ),
                shape = RoundedCornerShape(8.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(backCard),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {

            items(filteredList) { item ->
                ItemUiSc(item)
            }
        }
    }


}