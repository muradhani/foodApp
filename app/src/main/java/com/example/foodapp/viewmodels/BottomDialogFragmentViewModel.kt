package com.example.foodapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.repositories.Repository
import com.example.foodapp.state.State

class BottomDialogFragmentViewModel:ViewModel() {
    val repository = Repository()
    val _mealMutable = MutableLiveData<State<Meal>>()
    suspend fun getMeal(id:String){
        repository.getMeal(id).collect{
            _mealMutable.postValue(it)
        }
    }
}