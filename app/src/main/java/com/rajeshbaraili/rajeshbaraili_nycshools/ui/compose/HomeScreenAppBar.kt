package com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


// Header
@Composable
fun Header(header: String) {
    Row(modifier = Modifier.fillMaxWidth(),Arrangement.Center){
        Text(    text = header,
            modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
            color = MaterialTheme.colors.surface,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.subtitle1)
    }


}

@Composable
fun Content() {
    Column() {
        Spacer(modifier = Modifier.height(18.dp))
        Header("NY Schools Info")

    }
}

