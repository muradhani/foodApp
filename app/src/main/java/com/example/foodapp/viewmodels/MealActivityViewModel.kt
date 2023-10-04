package com.example.foodapp.viewmodels


import android.content.Context
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

    private var _inserMealResponse = MutableLiveData<State<Long>>()
    var inserMealResponse :LiveData<State<Long>> = _inserMealResponse
    //val meal: LiveData<State<Meal>> = _mealMutable
    suspend fun getMeal(id: String) {
        coroutineScope {
            repository.getMeal(id).collect {
                _mealMutable.postValue(it)
            }
        }

    }
    suspend fun addFavoriate(context: Context, meal:Meal){
        repository.addfavoriate(context,meal).collect{
            _inserMealResponse.postValue(it)
        }
    }
}

