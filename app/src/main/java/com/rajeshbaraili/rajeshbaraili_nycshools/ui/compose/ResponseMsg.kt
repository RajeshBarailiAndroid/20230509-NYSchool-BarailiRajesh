package com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun CircularProgressBar() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(80.dp)
                .semantics {
                    testTag = "progressLoader"
                },
            color = MaterialTheme.colors.surface,
            strokeWidth = 6.dp
        )
    }
}

@Composable
fun ErrorMsg(data: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error: $data Data could not be loaded. Please try again.",
            style = MaterialTheme.typography.h5,
            color = Color.Magenta,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

    }
}