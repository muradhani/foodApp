package com.example.foodapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.database.MealDatabase
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.mealResponse
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealActivityViewModel() :ViewModel() {

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
}