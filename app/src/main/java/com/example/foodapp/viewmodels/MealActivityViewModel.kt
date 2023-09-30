package com.example.foodapp.viewmodels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.repositories.Repository
import com.example.foodapp.state.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

class MealActivityViewModel() : ViewModel() {
    val repository = Repository()
    val _mealMutable = MutableLiveData<State<Meal>>()
    //val meal: LiveData<State<Meal>> = _mealMutable
    suspend fun getMeal(id: String) {
        coroutineScope {
            repository.getMeal(id).collect {
                Log.i("MealActivityViewModel", "Received data: ${it.toData()}")
                _mealMutable.postValue(it)
            }
        }

    }
}

