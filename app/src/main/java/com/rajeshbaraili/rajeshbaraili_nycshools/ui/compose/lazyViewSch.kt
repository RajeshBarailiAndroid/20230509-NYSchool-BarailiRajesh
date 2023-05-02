package com.jp.nysandroidapp.ui.compose

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.jp.nycschoolapp.util.Response
import com.jp.nycschools.model.School
import com.jp.nycschools.viewmodel.SchoolViewModel
import com.rajeshbaraili.rajeshbaraili_nycshools.R
import com.rajeshbaraili.rajeshbaraili_nycshools.model.RadioButtonOption
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.CircularProgressBar
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.ErrorMsg
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.theme.backCard

@Composable
fun ItemUiSc(school: School, navController: NavHostController) {
    var context= LocalContext.current
    var expand by remember { (mutableStateOf(false)) }
    Card(
        backgroundColor = Color.White,
        elevation = Dp(2F),
        modifier = Modifier.padding(all = 16.dp),
                shape = RoundedCornerShape(8.dp)
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
                    Log.e("TAG", "ItemUiSc: "+school.dbn )
                    val list = listOf(
                        "Total Students :-  " to school.total_students,
                        "Graduation Rate :-  " to school.graduation_rate,
                        "College Career Rate:- " to school.college_career_rate,
                        "College Safe Rate:- " to school.pct_stu_safe,
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
                                Row (    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically){
                                    Image(
                                        painter = painterResource(id = R.drawable.map),
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clickable {
                                                       mapOpen(context,coordinates[0],coordinates[1],address)
                                            },
                                        contentDescription = "map"
                                    )
                                    Spacer(modifier = Modifier.width(46.dp))
                                    Icon(
                                        painter = painterResource(id = R.drawable.sat),
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clickable {
                                                navController.navigate("sbnId/${school.dbn}")
                                            },
                                        contentDescription = "",
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

fun mapOpen(context: Context, latitude: String, longitude: String, address: String) {
    val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($address)")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}


@Composable
fun SchoolScreen(viewModel:SchoolViewModel ,navController: NavHostController) {
    var response = viewModel.schools.observeAsState().value
//loading,success and error condition
    when (response) {
        is Response.Loading -> {
            CircularProgressBar()
        }

        is Response.Success -> {

            LoadData(response,navController)

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

    val listItems=when(selectedOption.value){
        1 ->listItem.sortedByDescending { it.graduation_rate}
        2 ->listItem.sortedByDescending { it.college_career_rate }
        3 ->listItem.sortedByDescending { it.pct_stu_safe }
        4 ->listItem.sortedByDescending { it.total_students }
        else -> {listItem}
    }


    var filteredList = if (searchQuery.isEmpty()) {
        listItems

    } else {
        listItems.filter { it.school_name.contains(searchQuery, ignoreCase = true) }
    }


    // show dialog
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


    //search options
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
                }, trailingIcon = {if (isVisible){
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

        //list view
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(backCard),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {

            items(filteredList) { item ->
                ItemUiSc(item,navController)
            }
        }
    }


}
