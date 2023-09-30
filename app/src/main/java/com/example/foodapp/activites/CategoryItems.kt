package com.example.foodapp.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.foodapp.R
import com.example.foodapp.adapters.CategoryItemListener
import com.example.foodapp.adapters.CategoryItemsAdapter
import com.example.foodapp.databinding.ActivityCategoryItemsBinding
import com.example.foodapp.pojo.MealX
import com.example.foodapp.state.State
import com.example.foodapp.viewmodels.CategoryItemsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryItems : AppCompatActivity(), CategoryItemListener {
    lateinit var binding: ActivityCategoryItemsBinding
    val viewModel:CategoryItemsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_items)
        val intent = intent
        val category : String? = intent.getStringExtra("category")
        if (category != null) {
            Log.i("home",category)
        }
        lifecycleScope.launch {
            viewModel.getListMeals(category!!)
        }
        observeCategoryList()

    }

    override fun onCategoryClicked(categoryItem: MealX) {
        //to do edit activity meal to accept the meal id and load it
        val intent = Intent(this, MealActivity::class.java).apply {
            putExtra("meal",categoryItem.idMeal)
        }
        startActivity(intent)
    }

    fun observeCategoryList(){

            viewModel.mealList.observe(this@CategoryItems, Observer {
                val result = it

                when(result){
                    is State.Success ->{
                        var list = it.toData()
                        binding.tvItemsNumber.text = "items: "+ result.toData()!!.size.toString()
                        var adapter = CategoryItemsAdapter(list!!,this@CategoryItems)
                        binding.rvItems.adapter = adapter
                        binding.progressbar.visibility = View.GONE
                    }
                    is State.Loading->{
                        binding.progressbar.visibility = View.VISIBLE
                    }
                    is State.Error ->{
                        binding.progressbar.visibility = View.GONE
                    }
                    else -> {
                        // Do nothing
                        binding.progressbar.visibility = View.GONE
                    }
                }
            })
        }
}