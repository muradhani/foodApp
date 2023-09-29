package com.example.foodapp.state

sealed class State<out T>{
    data class Error<T>(val meassage:T):State<T>()
    data class Success<T>(val data:T):State<T>()
    class Loading<T>:State<T>()
    fun toData() = if (this is State.Success) data else null
}
