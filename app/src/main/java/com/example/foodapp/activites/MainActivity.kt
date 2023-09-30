package com.example.foodapp.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.R
import com.example.foodapp.database.MealDatabase
import com.example.foodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //val navController = findNavController(binding.hostFragment.id)
        val navController =this.findNavController(R.id.host_fragment)
        binding.bottomNav.setupWithNavController(navController)

    }
}