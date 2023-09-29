package com.example.foodapp.adapters

import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.base.BaseAdapter
import com.example.foodapp.databinding.CategoryItemBinding
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.MealX

class CategoryAdapter (
    private val list: List<Category>,
    private val categoryListener: CategoryListener
) :BaseAdapter<CategoryItemBinding, Category>(list){
    override val layoutId: Int
        get() = R.layout.category_item

    override fun bind(binding: CategoryItemBinding, item: Category) {
        Glide.with(binding.root).load(item.strCategoryThumb).into(binding.ivCategory)
        binding.tvCategoryName.text = item.strCategory
        binding.root.setOnClickListener { categoryListener.onCategoryClicked(item) }
    }
}
interface CategoryListener {
    fun onCategoryClicked(categoryItem: Category)
}