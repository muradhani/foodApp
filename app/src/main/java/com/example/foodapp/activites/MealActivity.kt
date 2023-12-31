package com.example.foodapp.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.database.MealDatabase
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.state.State
import com.example.foodapp.viewmodels.MealActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.withContext
import kotlin.math.log

class MealActivity : AppCompatActivity() {
    lateinit var binding: ActivityMealBinding
    lateinit var meal: Meal
    lateinit var mealId :String
    val viewModel : MealActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal)
        binding.viewModel = viewModel
        val intent = intent
        mealId = intent.getStringExtra("meal").toString()
        Log.i("home",mealId)
        getMealData(mealId)
        setupListener()
        observeAddFavoriate()

    }

    private fun observeAddFavoriate() {
        viewModel.inserMealResponse.observe(this@MealActivity, Observer {
            when(it){
                is State.Loading ->{
                    binding.linearBar.visibility=View.VISIBLE
                }
                is State.Success ->{
                    binding.linearBar.visibility=View.GONE
                    Toast.makeText(this@MealActivity,"meal added to favoriate",Toast.LENGTH_SHORT).show()
                }
                else -> {
                    binding.linearBar .visibility=View.GONE
                }
            }
        })
    }

    private fun setupListener() {
        binding.favoriateBtn.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO){
//                  MealDatabase.getInstance(this@MealActivity).mealDao().insertAll(meal)
                    viewModel.addFavoriate(this@MealActivity,meal)
                }
            }

        }
    }

    private fun getMealData(mealId: String) {
        lifecycleScope.launch {
            viewModel.getMeal(mealId)
            viewModel._mealMutable.observe(this@MealActivity, Observer {
                if (it is State.Success){
                    meal = it.data
                    binding.meal = it.data
                    binding.loadingProgressBar.visibility =View.GONE
                }
                else if(it is State.Loading){
                    binding.loadingProgressBar.visibility =View.VISIBLE
                }
                else{
                    binding.loadingProgressBar.visibility =View.GONE
                }
            })
        }
    }

}