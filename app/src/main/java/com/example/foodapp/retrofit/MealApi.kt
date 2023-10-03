package com.example.foodapp.retrofit

import com.example.foodapp.pojo.CategoryResponse
import com.example.foodapp.pojo.ListMeals
import com.example.foodapp.pojo.dto.MealResponse
import com.example.foodapp.pojo.mealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
     suspend fun getRandomMeal():Response<mealResponse>

    @GET("lookup.php?")
    suspend fun getMeal(@Query("i") id:String):Response<mealResponse>

    @GET("filter.php?")
     suspend fun getMealList(@Query("c") id: String):Response<ListMeals>
    @GET("filter.php?")
     suspend fun getMealListflow(@Query("c") id: String):Response<ListMeals>

    @GET("categories.php")
    suspend fun getCategoriesList():Response<CategoryResponse>

    @GET("search.php")
    suspend fun search(@Query("s") mealName:String):Response<MealResponse>
}