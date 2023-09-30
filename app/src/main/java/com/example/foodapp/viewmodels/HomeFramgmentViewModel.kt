package com.example.foodapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CategoryResponse
import com.example.foodapp.pojo.ListMeals
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealX
import com.example.foodapp.pojo.mealResponse
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFramgmentViewModel: ViewModel() {
    private val mealmutable = MutableLiveData<Meal>()
    private val _mealList = MutableLiveData<List<MealX>>()

    private val _specificMealmutable = MutableLiveData<Meal>()
    val specificMealmutable:LiveData<Meal> = _specificMealmutable
    val mealList :LiveData<List<MealX>> = _mealList

    private val _categorieslmutable = MutableLiveData<List<Category>>()
    val categorieslmutable:LiveData<List<Category>> = _categorieslmutable
    fun getRandomMeal(){
        RetrofitInstance.retrofit.getRandomMeal().enqueue(object :retrofit2.Callback<mealResponse>{
            override fun onResponse(
                call: retrofit2.Call<mealResponse>,
                response: retrofit2.Response<mealResponse>
            ) {
                val meal = response.body()!!.meals[0]
                mealmutable.postValue(meal)
            }

            override fun onFailure(call: retrofit2.Call<mealResponse>, t: Throwable) {
                Log.i("home","there something error in app")
            }
        })
    }
    fun observeMeal():LiveData<Meal>{
        return mealmutable
    }

    fun getListMeals(){
        RetrofitInstance.retrofit.getMealList("Seafood").enqueue(object :Callback<ListMeals>{
            override fun onResponse(call: Call<ListMeals>, response: Response<ListMeals>) {
                if (response.body()!= null){
                    _mealList.postValue(response.body()!!.meals)
                }
            }

            override fun onFailure(call: Call<ListMeals>, t: Throwable) {

            }
        })
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

    fun getCategoryList(){
        RetrofitInstance.retrofit.getCategoriesList().enqueue(object:Callback<CategoryResponse>{
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.body()!= null){
                    val categories = response.body()!!.categories
                    _categorieslmutable.postValue(categories)
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {

            }
        } )
    }
}