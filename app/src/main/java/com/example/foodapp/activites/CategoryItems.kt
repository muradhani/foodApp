package com.example.foodapp.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.foodapp.R
import com.example.foodapp.adapters.CategoryItemListener
import com.example.foodapp.adapters.CategoryItemsAdapter
import com.example.foodapp.databinding.ActivityCategoryItemsBinding
import com.example.foodapp.pojo.MealX
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
        val coroutineScope = CoroutineScope(Dispatchers.Main)

        coroutineScope.launch {
            category?.let {category->
//                viewModel.getListMeals(category).collect{
//                    val adapter = CategoryItemsAdapter(it, this@CategoryItems)
//                    binding.rvItems.adapter = adapter
//                }

            }
        }
    }

    override fun onCategoryClicked(categoryItem: MealX) {
        //to do edit activity meal to accept the meal id and load it
    }


}