package com.example.foodapp.adapters

import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.base.BaseAdapter
import com.example.foodapp.databinding.RandomMealItemBinding
import com.example.foodapp.pojo.MealX

class MealAdapter(
    private val list: MutableList<MealX>,
    private val mealListener: MealListener
) :BaseAdapter<RandomMealItemBinding,MealX>(list){
    override val layoutId: Int
        get() = R.layout.random_meal_item

    override fun bind(binding: RandomMealItemBinding, item: MealX, position: Int) {
        Glide.with(binding.root).load(item.strMealThumb).into(binding.imMeal)
        binding.root.setOnClickListener { mealListener.onMealClicked(item) }
        binding.root.setOnLongClickListener {
            mealListener.onHold(item)
            true // Return true to indicate that the long click event is consumed
        }
    }
}
interface MealListener {
    fun onMealClicked(meal:MealX)
    fun onFavouriteClicked(meal:MealX)
    fun onHold(meal:MealX)
}