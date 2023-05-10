package com.jp.nysandroidapp.ui.compose

import ItemCard
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jp.nycschoolapp.util.Response
import com.jp.nycschools.model.School
import com.jp.nycschools.viewmodel.SchoolViewModel
import com.rajeshbaraili.rajeshbaraili_nycshools.model.RadioButtonOption
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.CircularProgressBar
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.Content
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.ErrorMsg

@Composable
fun ItemUiSc(school: School, navController: NavHostController) {
    ItemCard(
        school,
        onItemClicked = { navController.navigate("sbnId/${school.dbn}/${school.school_name}/${school.overview_paragraph}/${school.location}/${school.graduation_rate}/${school.college_career_rate}/${school.pct_stu_safe}")
        })

}


@Composable
fun SchoolScreen(viewModel: SchoolViewModel, navController: NavHostController) {
    var response = viewModel.schools.observeAsState().value
//loading,success and error condition
    when (response) {
        is Response.Loading -> {
            CircularProgressBar()
        }

        is Response.Success -> {
            LoadData(response, navController)

        }

        is Response.Error -> {
            ErrorMsg("School")
        }

        else -> {
            CircularProgressBar()
        }
    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoadData(response: Response.Success<List<School>>, navController: NavHostController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var searchQuery by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val isVisible by remember {
        derivedStateOf {
            searchQuery.isNotBlank()
        }
    }

    // filter the list
    val radioButtonOptions = listOf(
        RadioButtonOption("Highest Graduation Rate", 1),
        RadioButtonOption("Highest Collage Career Rate", 2),
        RadioButtonOption("Highest Safe ", 3),
        RadioButtonOption("Highest Number Of Students ", 4)
    )
    var selectedOption by remember { mutableStateOf(radioButtonOptions[0]) }


    var listItem = response.data

    val listItems = when (selectedOption.value) {
        1 -> listItem.sortedByDescending { it.graduation_rate }
        2 -> listItem.sortedByDescending { it.college_career_rate }
        3 -> listItem.sortedByDescending { it.pct_stu_safe }
        4 -> listItem.sortedByDescending { it.totalStudent }
        else -> {
            listItem
        }
    }


    var filteredList = if (searchQuery.isEmpty()) {
        listItems

    } else {
        listItems.filter { it.school_name.contains(searchQuery, ignoreCase = true) }
    }


    // show dialog
    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {


            Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
                radioButtonOptions.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { selectedOption = option }
                    ) {
                        RadioButton(
                            selected = selectedOption == option,
                            onClick = { selectedOption = option },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Text(
                            text = option.text,color =MaterialTheme.colors.surface,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }

                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { showDialog = false }, modifier = Modifier.background(MaterialTheme.colors.onBackground)
                          ) {
                        Text(text = "Done",color=MaterialTheme.colors.surface)

                    }
                }

            }

        }


    }


    //search options
    Column {
        Content()
        Row(
            modifier = Modifier
                .fillMaxWidth()

                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 30.dp),
                shape = RoundedCornerShape(10.dp),
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
                    if (isVisible) {
                        IconButton(onClick = {
                            searchQuery = ""
                            keyboardController?.hide()
                            focusManager.clearFocus(true)
                        }
                        ) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Clear Search",
                                tint = Color.Gray
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            showDialog = !showDialog
                        }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "menu", tint = Color.Gray)
                        }
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.Black

                )
            )
        }

        //list view
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {

            items(filteredList) { item ->
                ItemUiSc(item, navController)
            }
        }
    }


}
