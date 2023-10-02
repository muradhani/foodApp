package com.example.foodapp.adapters

import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.base.BaseAdapter
import com.example.foodapp.databinding.CategoriesFragmentRvItemBinding
import com.example.foodapp.pojo.Category

class CategoryFragmentAdapter(
    private val list :MutableList<Category>,
    private val listener: CategoryFragmentListener
):BaseAdapter<CategoriesFragmentRvItemBinding,Category> (list){
    override val layoutId: Int
        get() = R.layout.categories_fragment_rv_item

    override fun bind(binding: CategoriesFragmentRvItemBinding, item: Category, position: Int) {
       Glide.with(binding.root).load(item.strCategoryThumb).into(binding.ivCategoryImg)
        binding.tvCategoryName.text = item.strCategory
        binding.root.setOnClickListener {
            listener.onCategoryClicked(item,position)
        }
    }
}
interface CategoryFragmentListener {
    fun onCategoryClicked(categoryItem: Category,position: Int)
}