package com.example.foodapp.adapters

import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.base.BaseAdapter
import com.example.foodapp.databinding.FavoriateFramgentRvItemBinding
import com.example.foodapp.pojo.Meal

class FavoraiteFragmentRvAdapter(
    private val items :List<Meal>,
    private val listener: FavoraiteItemListener
):BaseAdapter<FavoriateFramgentRvItemBinding,Meal>(items) {
    override val layoutId: Int
        get() = R.layout.favoriate_framgent_rv_item

    override fun bind(binding: FavoriateFramgentRvItemBinding, item: Meal) {
       Glide.with(binding.root).load(item.strMealThumb).into(binding.ivMealImg)
        binding.tvMealName.text = item.strMeal
        binding.btnFavoriate.setOnClickListener {
            listener.onFavoriateBtnClicked(item)
        }
        binding.root.setOnClickListener {
            listener.onFavoriateItemClicked(item)
        }
    }
}

interface FavoraiteItemListener {
    fun onFavoriateItemClicked(meal: Meal)
    fun onFavoriateBtnClicked(meal: Meal)
}