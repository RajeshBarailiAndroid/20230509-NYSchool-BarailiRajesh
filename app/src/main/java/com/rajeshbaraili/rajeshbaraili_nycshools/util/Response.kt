package com.jp.nycschoolapp.util

sealed class Response<T>{
    class Loading<T>:Response<T>()
    class Success<T>(val data:T):Response<T>()
    class Error<T>(val errorMsg:String):Response<T>()
}


