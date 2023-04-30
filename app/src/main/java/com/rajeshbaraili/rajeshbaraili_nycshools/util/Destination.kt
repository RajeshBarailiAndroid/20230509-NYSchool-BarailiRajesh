package com.rajeshbaraili.rajeshbaraili_nycshools.util

sealed class Destination(val route:String){
    object TabLayout:Destination(route = "home")
    object MapScreen:Destination(route = "map")
}
