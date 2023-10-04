package com.example.foodapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CategoryResponse
import com.example.foodapp.pojo.ListMeals
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealX
import com.example.foodapp.pojo.mealResponse
import com.example.foodapp.repositories.Repository
import com.example.foodapp.retrofit.RetrofitInstance
import com.example.foodapp.state.State
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFramgmentViewModel: ViewModel() {
    private var _randomMeal = MutableLiveData<State<Meal>>()
    var randomMeal :LiveData<State<Meal>> = _randomMeal

    private var _randomMealList = MutableLiveData<State<List<MealX>>>()
    var randomMealList : LiveData<State<List<MealX>>> = _randomMealList

//    private val _specificMealmutable = MutableLiveData<Meal>()
//    val specificMealmutable:LiveData<Meal> = _specificMealmutable
    //val mealList :LiveData<List<MealX>> = _mealList

    private var _categoriesList = MutableLiveData<State<List<Category>>>()
    var categoriesList :LiveData<State<List<Category>>> = _categoriesList
    val repository=Repository()
    init {
        viewModelScope.launch {
            getRandomMeal()
            getListMeals()
            getCategoryList()
        }
    }
    suspend fun getRandomMeal(){
        repository.getRandomMeal().collect{
            _randomMeal.postValue(it)
        }
    }
//    fun observeMeal():LiveData<Meal>{
//        return mealmutable
//    }

    suspend fun getListMeals(){
        repository.getMealList("Seafood").collect{
            _randomMealList.postValue(it)
        }
    }
//    fun getMeal(id:String){
//        RetrofitInstance.retrofit.getMeal(id).enqueue(object:Callback<mealResponse>{
//            override fun onResponse(call: Call<mealResponse>, response: Response<mealResponse>) {
//                if (response.body()!= null){
//                    val meal = response.body()!!.meals[0]
//                    _specificMealmutable.postValue(meal)
//                }
//            }
//
//            override fun onFailure(call: Call<mealResponse>, t: Throwable) {
//
//            }
//        } )
//    }

    suspend fun getCategoryList(){
       repository.getCategoryList().collect{
           _categoriesList.postValue(it)
       }
    }
}