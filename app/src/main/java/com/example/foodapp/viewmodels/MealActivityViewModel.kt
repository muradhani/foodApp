package com.example.foodapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.database.MealDatabase
import com.example.foodapp.factory.MealActivityViewModelRepository
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.mealResponse
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealActivityViewModel(private val repository: MealActivityViewModelRepository) :ViewModel() {

    private val mealMutable = MutableLiveData<Meal>()
    fun getMeal(id:String){
        RetrofitInstance.retrofit.getMeal(id).enqueue(object :Callback<mealResponse>{
            override fun onResponse(call: Call<mealResponse>, response: Response<mealResponse>) {
                if (response.body()!= null){
                    val meal = response.body()!!.meals[0]
                    mealMutable.postValue(meal)
                }
            }

            override fun onFailure(call: Call<mealResponse>, t: Throwable) {
            }
        })
    }
    fun observeMeal():LiveData<Meal>{
        return mealMutable
    }
    suspend fun insertMeal(meal:Meal){
        repository.insertMeal(meal)
    }
    fun getAllMeals(){
        var meals = repository.getAllMeal()
        Log.i("home", meals.value.toString())
    }
}