package com.example.foodapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.dto.MealDB

@Dao
interface MealDao {
    @Query("SELECT * FROM meal")
    fun getAll():List<Meal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( meal: Meal):Long

    @Delete
    fun delete(meal: Meal)
    @Update
    fun update(meal: Meal)
}