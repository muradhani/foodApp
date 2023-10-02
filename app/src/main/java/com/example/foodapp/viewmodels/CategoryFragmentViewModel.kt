package com.example.foodapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.pojo.Category
import com.example.foodapp.repositories.Repository
import com.example.foodapp.state.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryFragmentViewModel:ViewModel() {
    val repository = Repository()
    private var _categories = MutableLiveData<State<List<Category>>>()
    var categories:LiveData<State<List<Category>>> = _categories
    suspend fun getCategoryList(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategoryList().collect{
                if (it is State.Success){
                    withContext(Dispatchers.Main){
                        _categories.postValue(it)
                    }
                }
            }
        }
    }
}