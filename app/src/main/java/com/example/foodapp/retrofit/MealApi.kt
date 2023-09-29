package com.example.foodapp.retrofit

import com.example.foodapp.pojo.CategoryResponse
import com.example.foodapp.pojo.ListMeals
import com.example.foodapp.pojo.mealResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<mealResponse>

    @GET("lookup.php?")
    fun getMeal(@Query("i") id:String):Call<mealResponse>

    @GET("filter.php?")
    fun getMealList(@Query("c") id: String):Call<ListMeals>
    @GET("filter.php?")
    suspend fun getMealListflow(@Query("c") id: String):Response<ListMeals>

    @GET("categories.php")
    fun getCategoriesList():Call<CategoryResponse>
}