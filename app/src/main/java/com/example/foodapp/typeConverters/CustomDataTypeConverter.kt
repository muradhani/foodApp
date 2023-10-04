package com.example.foodapp.typeConverters

import androidx.room.TypeConverter
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.dto.MealDB
import com.google.gson.Gson

class CustomDataTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun mealToString(meal: Meal?): String? {
        return if (meal == null) null else gson.toJson(meal)
    }

    @TypeConverter
    fun stringToMeal(json: String?): Meal? {
        return if (json == null) null else gson.fromJson(json, Meal::class.java)
    }
}