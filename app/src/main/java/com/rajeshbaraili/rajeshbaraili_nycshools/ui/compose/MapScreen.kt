package com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

@Composable
fun MapScreen(lattitude: Float, longitude: Float, address: String, navController: NavHostController) {
    Column{
var context= LocalContext.current
        val uri = Uri.parse("geo:$lattitude,$longitude?q=$lattitude,$longitude($address)")
        val intent = Intent(Intent.ACTION_VIEW, uri)
       context.startActivity(intent)

}}