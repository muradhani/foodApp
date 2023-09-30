package com.example.foodapp.pojo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "meal")
data class Meal(
    @PrimaryKey(autoGenerate = true)
    val mealId :Int,
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