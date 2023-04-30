package com.rajeshbaraili.rajeshbaraili_nycshools.util

sealed class Destination(val route:String){
    object HomeScreen:Destination(route = "School")
    object MapScreen:Destination(route = "map")
}
