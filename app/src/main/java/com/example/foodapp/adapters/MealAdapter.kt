package com.example.foodapp.adapters

import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.base.BaseAdapter
import com.example.foodapp.databinding.RandomMealItemBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealX

class MealAdapter(
    private val list: List<MealX>,
    private val mealListener: MealListener
) :BaseAdapter<RandomMealItemBinding,MealX>(list){
    override val layoutId: Int
        get() = R.layout.random_meal_item

    override fun bind(binding: RandomMealItemBinding, item: MealX) {
        Glide.with(binding.root).load(item.strMealThumb).into(binding.imMeal)
        binding.root.setOnClickListener { mealListener.onMealClicked(item) }
    }
}
interface MealListener {
    fun onMealClicked(meal:MealX)
    fun onFavouriteClicked(meal:MealX)
}