package com.example.foodapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.ListMeals
import com.example.foodapp.pojo.MealX
import com.example.foodapp.repositories.Repository
import com.example.foodapp.retrofit.RetrofitInstance
import com.example.foodapp.state.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryItemsViewModel:ViewModel() {
    private val _mealList = MutableLiveData<State<List<MealX>>>()
    val mealList :LiveData<State<List<MealX>>> = _mealList
    val repository =Repository()
    suspend fun getListMeals(category: String){
        repository.getCategoryMeals(category).collect{
            _mealList.postValue(it)
        }
    }
}