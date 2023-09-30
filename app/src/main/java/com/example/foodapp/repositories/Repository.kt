package com.example.foodapp.repositories

import android.util.Log
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CategoryResponse
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealX
import com.example.foodapp.retrofit.RetrofitInstance
import com.example.foodapp.state.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response


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

    suspend fun getRandomMeal():Flow<State<Meal>>{
        return flow {
            emit(State.Loading)
            val response = retrofit.getRandomMeal()
            if (response.isSuccessful){
                emit(State.Success(response.body()!!.meals[0]))
            }
            else{
                emit(State.Error(response.message()))
            }
        }
    }
    suspend fun getMealList(category:String):Flow<State<List<MealX>>>{
        return flow {
            emit(State.Loading)
          try {
              var result =  RetrofitInstance.retrofit.getMealList("Seafood")
              if (result.isSuccessful){
                  val mealList = result.body()?.meals
                  if (mealList != null){
                      emit(State.Success(mealList))
                  }
                  else{
                      emit(State.Error("No meal data found"))
                  }

              }else {
                  val errorMessage = result.errorBody()?.string() ?: "Unknown error"
                  emit(State.Error(errorMessage))
              }
          }catch (e: Exception) {
              val errorMessage = "Network error: ${e.message}"
              emit(State.Error(errorMessage))
          }
        }

    }
    suspend fun getCategoryList():Flow<State<List<Category>>>{
        return flow {
            emit(State.Loading)
            try {
                var result =  RetrofitInstance.retrofit.getCategoriesList()
                if (result.isSuccessful){
                    val categoryList = result.body()?.categories
                    if (categoryList != null){
                        emit(State.Success(categoryList))
                    }
                    else{
                        emit(State.Error("No category data found"))
                    }

                }else {
                    val errorMessage = result.errorBody()?.string() ?: "Unknown error"
                    emit(State.Error(errorMessage))
                }
            }catch (e: Exception) {
                val errorMessage = "Network error: ${e.message}"
                emit(State.Error(errorMessage))
            }
        }

    }

}


