package com.example.foodapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.ListMeals
import com.example.foodapp.pojo.MealX
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryItemsViewModel:ViewModel() {
    private val _mealList = MutableLiveData<List<MealX>>()
    val mealList :LiveData<List<MealX>> = _mealList
    suspend fun getListMeals(category: String): Flow<List<MealX>> = flow {
        try {
            val response = RetrofitInstance.retrofit.getMealListflow(category)
            if (response.isSuccessful) {
                val meals = response.body()?.meals
                if (meals != null) {
                    emit(meals)
                }
            }
        } catch (e: Exception) {
            // Handle exceptions
        }
    }.flowOn(Dispatchers.IO)
}