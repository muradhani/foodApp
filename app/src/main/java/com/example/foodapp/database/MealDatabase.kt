package com.example.foodapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.dto.MealDB
import com.example.foodapp.typeConverters.CustomDataTypeConverter

@Database(entities = [Meal::class], version = 3, exportSchema = false)
@TypeConverters(CustomDataTypeConverter::class)
 abstract class MealDatabase :RoomDatabase(){
    abstract fun mealDao() :MealDao
    companion object {
        @Volatile
        private var instance: MealDatabase? = null
        @Synchronized
        fun getInstance(ctx: Context): MealDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(ctx.applicationContext, MealDatabase::class.java, "database").build()
            }
            return instance!!
        }
    }
}