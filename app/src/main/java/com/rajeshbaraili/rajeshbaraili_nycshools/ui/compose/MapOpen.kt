package com.rajeshbaraili.rajeshbaraili_nycshools.ui.compose

import android.content.Context
import android.content.Intent
import android.net.Uri

fun mapOpen(context: Context, location: String) {
    val address = location.substringBefore("(").trim()
    val latitude = location.substringAfter("(").substringBefore(",").trim()
    val longitude = location.substringAfter(",").substringBefore(")").trim()
    val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($address)")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}