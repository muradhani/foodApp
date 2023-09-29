package com.example.foodapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.database.MealDatabase
import com.example.foodapp.factory.MealActivityViewModelRepository

class MealActivityViewModelFactory(private val repository: MealActivityViewModelRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val constructor = modelClass.getDeclaredConstructor(repository::class.java)
        return constructor.newInstance(repository)
    }
}