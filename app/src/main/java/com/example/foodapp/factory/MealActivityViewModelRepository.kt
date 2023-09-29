package com.example.foodapp.factory

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import com.example.foodapp.database.MealDatabase
import com.example.foodapp.pojo.Meal

class MealActivityViewModelRepository(private val database: MealDatabase) {
    suspend fun insertMeal(meal: Meal) {
       database.getDao().insertMeal(meal)
    }

    fun getAllMeal(): LiveData<List<Meal>> {
        return database.getDao().getAllMeals()
    }

}