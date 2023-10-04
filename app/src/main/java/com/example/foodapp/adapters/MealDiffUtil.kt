package com.example.foodapp.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.foodapp.pojo.Meal

class MealDiffUtil(private val oldList: List<Meal>, private val newList: List<Meal>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
       return oldList.size
    }

    override fun getNewListSize(): Int {
        return  newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition].idMeal == newList[newItemPosition].idMeal
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].idMeal == newList[newItemPosition].idMeal -> true
            oldList[oldItemPosition].strMeal == newList[newItemPosition].strMeal -> true
            else -> false
        }
    }

}