package com.example.foodapp.database

import android.content.Context
import androidx.room.Database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodapp.pojo.Meal

@Database(entities = [Meal::class], version = 2)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase :RoomDatabase(){
    abstract fun getDao():MealDao
    companion object{
        @Volatile
        private var INSTANCE:MealDatabase? = null
        @Synchronized
        fun getInstance(context:Context):MealDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext,MealDatabase::class.java,"mealDatabase.db").fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MealDatabase
        }
    }
}