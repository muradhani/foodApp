package com.example.foodapp.adapters

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
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

    override fun removeItem(position: Int) {
        Log.i("m",position.toString())
        this.items.removeAt(position)
        setUserList(this.items)
    }

    override fun addItem(item: Meal) {

    }

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
    fun setUserList(updatedMealList: List<Meal>) {
        val diffResult = DiffUtil.calculateDiff(MealDiffUtil(items, updatedMealList))
        items.clear()
        items.addAll(updatedMealList)
        diffResult.dispatchUpdatesTo(this)
    }
}

interface FavoraiteItemListener {
    fun onFavoriateItemClicked(meal: Meal,position: Int)
    fun onFavoriateBtnClicked(meal: Meal,position: Int)
}