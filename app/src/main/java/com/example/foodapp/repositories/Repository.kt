package com.example.foodapp.repositories

import android.content.Context
import android.util.Log
import com.example.foodapp.database.MealDatabase
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
import kotlinx.coroutines.withContext
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

    suspend fun getRandomMeal(): Flow<State<Meal>> {
        return flow {
            emit(State.Loading)
            val response = retrofit.getRandomMeal()
            if (response.isSuccessful) {
                emit(State.Success(response.body()!!.meals[0]))
            } else {
                emit(State.Error(response.message()))
            }
        }
    }

    suspend fun getMealList(category: String): Flow<State<List<MealX>>> {
        return flow {
            emit(State.Loading)
            try {
                var result = RetrofitInstance.retrofit.getMealList("Seafood")
                if (result.isSuccessful) {
                    val mealList = result.body()?.meals
                    if (mealList != null) {
                        emit(State.Success(mealList))
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

    suspend fun getCategoryList(): Flow<State<List<Category>>> {
        return flow {
            emit(State.Loading)
            try {
                var result = RetrofitInstance.retrofit.getCategoriesList()
                if (result.isSuccessful) {
                    val categoryList = result.body()?.categories
                    if (categoryList != null) {
                        emit(State.Success(categoryList))
                    } else {
                        emit(State.Error("No category data found"))
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

    suspend fun getCategoryMeals(category: String): Flow<State<List<MealX>>> = flow {
        try {
            emit(State.Loading)
            val response = RetrofitInstance.retrofit.getMealListflow(category)
            if (response.isSuccessful) {
                val meals = response.body()?.meals
                if (meals != null) {
                    emit(State.Success(meals))
                }
            } else {
                emit(State.Error(response.message()))
            }
        } catch (e: Exception) {
            // Handle exceptions
        }
    }.flowOn(Dispatchers.IO)

    suspend fun addfavoriate(context: Context, meal: Meal): Flow<State<Long>> {
        return flow {
            emit(State.Loading)
            try {
                val id = withContext(Dispatchers.IO) {
                    MealDatabase.getInstance(context).mealDao().insertAll(meal)
                }

                if (id > 0) {
                    // The item was inserted successfully
                    emit(State.Success(id))
                    //Toast.makeText(baseContext, "Item inserted with ID: $id", Toast.LENGTH_SHORT).show()
                } else {
                    // There was an issue with the insertion
                    //Toast.makeText(baseContext, "Failed to insert item", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle any exceptions that might occur during the insertion
                //Toast.makeText(baseContext, "Error inserting item: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    suspend fun getAllFavoraites(context: Context): Flow<State<List<Meal>?>> {
        return flow {
            emit(State.Loading)
            try {
                val result = MealDatabase.getInstance(context).mealDao().getAll()
                Log.i("favoriate", result.toString())
                if (result != null) {
                    emit(State.Success(result))
                } else {
                    emit(State.Error("no list"))
                }
            } catch (e: Exception) {

            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun removeFavoriate(meal: Meal, context: Context): Flow<State<Int>> {
        return flow {
            try {
                emit(State.Loading)
                val id = withContext(Dispatchers.IO) {
                    MealDatabase.getInstance(context).mealDao().delete(meal)
                }

                if (id > 0) {
                    // The item was inserted successfully
                    emit(State.Success(id))
                    //Toast.makeText(baseContext, "Item inserted with ID: $id", Toast.LENGTH_SHORT).show()
                } else {
                    // There was an issue with the insertion
                    //Toast.makeText(baseContext, "Failed to insert item", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle any exceptions that might occur during the insertion
                //Toast.makeText(baseContext, "Error inserting item: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }
}



