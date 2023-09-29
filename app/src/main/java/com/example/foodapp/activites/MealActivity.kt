package com.example.foodapp.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.database.MealDatabase
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.factory.MealActivityViewModelRepository
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewmodels.MealActivityViewModel
import com.example.foodapp.viewmodels.MealActivityViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealActivity : AppCompatActivity() {
    lateinit var binding: ActivityMealBinding
    lateinit var meal: Meal

    lateinit var database :MealDatabase
    lateinit var repository :MealActivityViewModelRepository
    lateinit var factory :MealActivityViewModelFactory
    lateinit var viewModel : MealActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal)
        val intent = intent
        meal = intent.getSerializableExtra("meal") as Meal
        binding.meal = meal
        Glide.with(this).load(meal.strMealThumb).into(binding.mealImg)
        database = MealDatabase.getInstance(this)
        repository = MealActivityViewModelRepository(database)
        factory = MealActivityViewModelFactory(repository)
        viewModel = ViewModelProvider(this,factory)[MealActivityViewModel::class.java]
        lifecycleScope.launch {
            viewModel.getAllMeals()
        }

        initatebuttons()
    }

    private fun initatebuttons() {
        binding.favoriateBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.insertMeal(meal)
            }
           Toast.makeText(this,"Clicked!!",Toast.LENGTH_SHORT).show()
        }
    }
}