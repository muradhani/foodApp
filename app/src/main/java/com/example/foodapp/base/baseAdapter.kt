package com.example.foodapp.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<BINDING : ViewDataBinding, T >(
    var data: MutableList<T>
) : RecyclerView.Adapter<BaseViewHolder<BINDING>>() {


    abstract val layoutId: Int

    abstract fun bind(binding: BINDING, item: T, position: Int)

    fun updateData(list: List<T>) {
        this.data = list.toMutableList()
        notifyDataSetChanged()
    }
    fun addItem(item: T) {
        data.add(item)
        notifyItemInserted(data.size - 1)
    }

    fun removeItem(position: Int) {
        if (position >= 0 && position < data.size) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BINDING> {
        val binder = DataBindingUtil.inflate<BINDING>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )

        return BaseViewHolder(binder)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BINDING>, position: Int) {
        bind(holder.binder, data[position],position)
    }

    override fun getItemCount(): Int = data.size
}

class BaseViewHolder<BINDING : ViewDataBinding>(val binder: BINDING) :
    RecyclerView.ViewHolder(binder.root) {
}