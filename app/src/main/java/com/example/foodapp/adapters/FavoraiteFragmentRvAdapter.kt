package com.example.foodapp.adapters

import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.base.BaseAdapter
import com.example.foodapp.databinding.FavoriateFramgentRvItemBinding
import com.example.foodapp.pojo.Meal

class FavoraiteFragmentRvAdapter(
    private val items :MutableList<Meal>,
    private val listener: FavoraiteItemListener
):BaseAdapter<FavoriateFramgentRvItemBinding,Meal>(items) {
    override val layoutId: Int
        get() = R.layout.favoriate_framgent_rv_item

    override fun bind(binding: FavoriateFramgentRvItemBinding, item: Meal, position: Int) {
       Glide.with(binding.root).load(item.strMealThumb).into(binding.ivMealImg)
        binding.tvMealName.text = item.strMeal
        binding.btnFavoriate.setOnClickListener {
            listener.onFavoriateBtnClicked(item,position)
        }
        binding.root.setOnClickListener {
            listener.onFavoriateItemClicked(item,position)
        }
    }
}

interface FavoraiteItemListener {
    fun onFavoriateItemClicked(meal: Meal,position: Int)
    fun onFavoriateBtnClicked(meal: Meal,position: Int)
}