package com.example.foodapp.adapters

import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.base.BaseAdapter
import com.example.foodapp.databinding.CategoryItemViewBinding
import com.example.foodapp.pojo.MealX

class CategoryItemsAdapter (
    private val list: MutableList<MealX>,
    private val listener: CategoryItemListener
) : BaseAdapter<CategoryItemViewBinding, MealX>(list){
    override val layoutId: Int
        get() = R.layout.category_item_view

    override fun bind(binding: CategoryItemViewBinding, item: MealX, position: Int) {
        binding.tvCategoryName.text = item.strMeal
        Glide.with(binding.root).load(item.strMealThumb).into(binding.ivCategory)
        binding.root.setOnClickListener { listener.onCategoryClicked(item) }
    }
}
interface CategoryItemListener {
    fun onCategoryClicked(categoryItem: MealX)
}