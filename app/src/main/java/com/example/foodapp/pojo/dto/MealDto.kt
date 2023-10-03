package com.example.foodapp.pojo.dto

import androidx.room.PrimaryKey

data class MealDto(
    val idMeal: String?,
    val strArea: String?,
    val strCategory: String?,
    val strInstructions: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val strSource: String?,
    val strTags: String?,
    val strYoutube: String?
)
