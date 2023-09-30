package com.example.foodapp.state

sealed class State<out T>{
    data class Error(val meassage:String):State<Nothing>()
    data class Success<T>(val data:T):State<T>()
    object Loading:State<Nothing>()
    fun toData():T? {
        if (this is Success){
            return data
        }
        return null
    }
}
