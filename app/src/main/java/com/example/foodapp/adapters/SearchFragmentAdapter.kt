package com.example.foodapp.adapters

import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.base.BaseAdapter
import com.example.foodapp.databinding.SearchFragmentRvItemBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.dto.MealDto


class SearchFragmentAdapter(
    private var items: MutableList<MealDto> = mutableListOf(),
    private val listener: SearchItemListener
) : BaseAdapter<SearchFragmentRvItemBinding, MealDto>(items) {
    override val layoutId: Int
        get() = R.layout.search_fragment_rv_item

    override fun bind(binding: SearchFragmentRvItemBinding, item: MealDto, position: Int) {
        Glide.with(binding.root).load(item.strMealThumb).into(binding.mealImgIv)
        binding.mealNameTv.text = item.strMeal
        binding.root.setOnClickListener {
            listener.onMealClicked(item, position)
        }
    }


}

interface SearchItemListener {
    fun onMealClicked(meal: MealDto, position: Int)
}
