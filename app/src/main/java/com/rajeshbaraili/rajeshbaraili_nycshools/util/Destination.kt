package com.rajeshbaraili.rajeshbaraili_nycshools.util

sealed class Destination(val route:String){


    object HomeScreen:Destination(route = "School")
    object MapScreen : Destination(route = "map?latitude={latitude}&longitude={longitude}&address={address}"){
        fun passArguments(latitude: String, longitude: String, address: String): String{
            return  "map?latitude=$latitude&longitude=$longitude&address=$address"
        }
    }

}
