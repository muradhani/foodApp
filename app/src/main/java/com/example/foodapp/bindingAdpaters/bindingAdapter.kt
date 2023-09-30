package com.example.foodapp.bindingAdpaters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.foodapp.state.State

@BindingAdapter("GLideImg")
fun displayImage(view:ImageView,imgUrl:String?){
    Glide.with(view.context).load(imgUrl).into(view)
}
@BindingAdapter("showWhenLoading")
fun<T>showLoading(view:View,state: State<T>?){
    if (state is State.Loading){
        view.visibility = View.VISIBLE
    }
    else{
        view.visibility = View.GONE
    }
}