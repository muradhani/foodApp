package com.example.foodapp.repositories

import android.util.Log
import com.example.foodapp.pojo.Meal
import com.example.foodapp.retrofit.RetrofitInstance
import com.example.foodapp.state.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class Repository {
    val retrofit = RetrofitInstance.retrofit
    suspend fun getMeal(id: String): Flow<State<Meal>> {
        return flow {
            emit(State.Loading)
            try {
                val result = retrofit.getMeal(id)
                if (result.isSuccessful) {
                    val meal = result.body()?.meals?.firstOrNull()
                    if (meal != null) {
                        emit(State.Success(meal))
                    } else {
                        emit(State.Error("No meal data found"))
                    }
                } else {
                    val errorMessage = result.errorBody()?.string() ?: "Unknown error"
                    emit(State.Error(errorMessage))
                }
            } catch (e: Exception) {
                val errorMessage = "Network error: ${e.message}"
                emit(State.Error(errorMessage))
            }
        }
    }

}