package com.example.foodapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.dto.MealDto
import com.example.foodapp.repositories.Repository
import com.example.foodapp.state.State


class SearchFragmentViewModel : ViewModel() {
    private val repository = Repository()
    private var _searchResponse = MutableLiveData<State<List<MealDto>>>()
    var searchResponse : LiveData<State<List<MealDto>>> = _searchResponse
    suspend fun searchMeal(mealName: String) {
        repository.search(mealName).collect {
            //Log.i("home",it.toData().toString())
            _searchResponse.postValue(it)
        }
    }
}