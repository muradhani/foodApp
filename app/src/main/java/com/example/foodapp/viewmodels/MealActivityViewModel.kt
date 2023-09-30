package com.example.foodapp.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.repositories.Repository
import com.example.foodapp.state.State

class MealActivityViewModel() : ViewModel() {
    val repository = Repository()
    private val _mealMutable = MutableLiveData<State<Meal>>()
    val meal: LiveData<State<Meal>> = _mealMutable
    init {
        _mealMutable.postValue(State.Loading)
    }
    suspend fun getMeal(id: String) {
        repository.getMeal(id).collect {
            _mealMutable.postValue(it)
        }
    }
}

