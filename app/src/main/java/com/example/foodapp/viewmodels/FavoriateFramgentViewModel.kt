package com.example.foodapp.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.repositories.Repository
import com.example.foodapp.state.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriateFramgentViewModel:ViewModel() {
    val repository = Repository()
    private var _items = MutableLiveData<State<List<Meal>?>>()
    var items :LiveData<State<List<Meal>?>> = _items

    private var _removeItemResponse = MutableLiveData<State<Int>>()
    var removeItemResponse :LiveData<State<Int>> = _removeItemResponse
    suspend fun getAllFavoriateMeals(context: Context){
        repository.getAllFavoraites(context).collect{
            withContext(Dispatchers.Main){
                _items.postValue(it)
            }

        }
    }
    suspend fun removeFavoriate(meal: Meal,context: Context){
        repository.removeFavoriate(meal,context).collect{
            _removeItemResponse.postValue(it)
        }
    }
    suspend fun updateData(context: Context) {
        getAllFavoriateMeals(context)
    }
}