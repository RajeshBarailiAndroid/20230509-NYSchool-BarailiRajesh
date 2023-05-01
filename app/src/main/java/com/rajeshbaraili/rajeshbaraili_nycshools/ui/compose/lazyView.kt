package com.jp.nysandroidapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
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
import com.jp.nycschoolapp.util.Response
import com.jp.nycschools.viewmodel.SatViewModel
import com.jp.nysandroidapp.ui.model.Sat
import com.rajeshbaraili.rajeshbaraili_nycshools.R
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.CircularProgressBar
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose.ErrorMsg
import com.rajeshbaraili.rajeshbaraili_nycshools.ui.theme.backCard

@Composable
fun ItemUi(sat: Sat) {
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
                        "Critical Reading Average Score :-" to sat.dbn,
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
fun SatScreen(viewModel: SatViewModel) {
    var response = viewModel.sat.observeAsState().value
    when (response) {
        is Response.Loading -> {
            CircularProgressBar()
        }
        is Response.Success -> {
           LoadDataSat(response)
        }
        is Response.Error -> {
            ErrorMsg("SAT")
        }
        else -> { CircularProgressBar()}
    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoadDataSat(response: Response.Success<List<Sat>>) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
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
                .background(backCard).padding(vertical = 10.dp),
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
                    IconButton(onClick = {
                        searchQuery = ""
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                    }
                    ){
                        Icon(Icons.Filled.Close, contentDescription = "Clear Search")
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(backCard),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {

            items(filteredList) { item ->
                ItemUi(item)
            }
        }
    }






}






