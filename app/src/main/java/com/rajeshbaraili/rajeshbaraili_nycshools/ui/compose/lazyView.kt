package com.jp.nysandroidapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.jp.nycschoolapp.util.Response
import com.jp.nycschools.viewmodel.SatViewModel
import com.jp.nysandroidapp.ui.model.Sat
import com.rajeshbaraili.rajeshbaraili_nycshools.R
import com.rajeshbaraili.rajeshbaraili_nycshools.model.RadioButtonOption
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.CircularProgressBar
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.ErrorMsg
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.theme.backCard

@Composable
fun ItemUi(sat: Sat, navController: NavHostController) {

    var expand by remember { (mutableStateOf(false)) }
    Card(
        backgroundColor = Color.White,
        elevation = Dp(2F),
        modifier = Modifier.padding(all = 16.dp), shape = RoundedCornerShape(8.dp)
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
                        .background(color = backCard)
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
                        Column {
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
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.sat),
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    navController.navigate("sbnId/${sat.dbn}")
                                },
                            contentDescription = "",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SatScreen(viewModel: SatViewModel, navController: NavHostController) {
    var response = viewModel.sat.observeAsState().value
    when (response) {
        is Response.Loading -> {
            CircularProgressBar()
        }
        is Response.Success -> {
           LoadDataSat(response,navController)
        }
        is Response.Error -> {
            ErrorMsg("SAT")
        }
        else -> { CircularProgressBar()}
    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoadDataSat(response: Response.Success<List<Sat>>, navController: NavHostController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var searchQuery by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val isVisible by remember {
        derivedStateOf {
            searchQuery.isNotBlank()
        }
    }
//filter the sat scores
    val radioButtonOptions = listOf(
        RadioButtonOption("Highest Math Score", 1),
        RadioButtonOption("Highest Reading Score", 2),
        RadioButtonOption("Highest Writing Score", 3),
        RadioButtonOption("Highest Numbers Of Test Takers", 4)
    )
    var selectedOption by remember { mutableStateOf(radioButtonOptions[0]) }

    var listItem = response.data
    var listI=listItem.filterNot { it.sat_math_avg_score.contentEquals("s") }
    val listItems=when(selectedOption.value){
        1 ->listI.sortedByDescending { it.sat_math_avg_score}
        2 ->listI.sortedByDescending { it.sat_critical_reading_avg_score }
        3 ->listI.sortedByDescending { it.sat_writing_avg_score }
        4 ->listI.sortedByDescending { it.num_of_sat_test_takers}
        else -> {listItem}
    }


    var filteredList = if (searchQuery.isEmpty()) {
        listItems

    } else {
        listItems.filter { it.school_name.contains(searchQuery, ignoreCase = true) }
    }


    // show dialog for filter
    if (showDialog){
        Dialog(onDismissRequest = { showDialog=false }) {


                Column(modifier = Modifier.background(backCard)) {
                    radioButtonOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clickable { selectedOption = option }
                        ) {
                            RadioButton(
                                selected = selectedOption == option,
                                onClick = { selectedOption = option },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Text(
                                text = option.text,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }

                    }
                    Row(Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        Button(onClick = { showDialog=false}) {
                            Text(text = "Filter")

                        }
                    }

                }

            }


        }



    //filter by  search text
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backCard)
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            TextField(
                modifier = Modifier
                    .weight(1f)
                    .background(backCard)
                    .padding(horizontal = 30.dp),
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                }, trailingIcon = {
                    if (isVisible){
                    IconButton(onClick = {
                        searchQuery = ""
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                    }
                    ){
                        Icon(Icons.Filled.Close, contentDescription = "Clear Search")
                    }
                    }else{
                        IconButton(onClick = {
                            showDialog = !showDialog
                        }
                        ){
                            Icon(Icons.Default.Menu, contentDescription = "menu")
                        }
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    backgroundColor = backCard,
                    cursorColor = Color.Black

                )
            )
        }

        // load data to list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(backCard),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {

            items(filteredList) { item ->
                ItemUi(item,navController)
            }
        }
    }






}






