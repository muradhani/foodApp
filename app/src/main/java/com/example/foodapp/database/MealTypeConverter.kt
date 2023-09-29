package com.example.foodapp.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun anyToString(any: Any):String{
        if (any == null)
            return ""
        return any.toString()
    }
    @TypeConverter
    fun stringToAny(attribute: String):Any{
        if (attribute == null)
            return ""
        return attribute
    }
}