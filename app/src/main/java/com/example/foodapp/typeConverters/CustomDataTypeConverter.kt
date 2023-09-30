package com.example.foodapp.typeConverters

import androidx.room.TypeConverter
import com.example.foodapp.pojo.dto.MealDB
import com.google.gson.Gson

class CustomDataTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun mealToString(meal: MealDB?): String? {
        return if (meal == null) null else gson.toJson(meal)
    }

    @TypeConverter
    fun stringToMeal(json: String?): MealDB? {
        return if (json == null) null else gson.fromJson(json, MealDB::class.java)
    }
}