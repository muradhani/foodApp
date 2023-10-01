package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.activites.MealActivity
import com.example.foodapp.adapters.FavoraiteFragmentRvAdapter
import com.example.foodapp.adapters.FavoraiteItemListener
import com.example.foodapp.databinding.FragmentFavoriateBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.state.State
import com.example.foodapp.viewmodels.FavoriateFramgentViewModel
import kotlinx.coroutines.launch

class favoriate : Fragment(), FavoraiteItemListener {
    val viewModel: FavoriateFramgentViewModel by viewModels()
    lateinit var binding:FragmentFavoriateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favoriate,container,false)
        lifecycleScope.launch {
            viewModel.getAllFavoriateMeals(this@favoriate.requireContext())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observefavoriateMealList()
    }
fun observefavoriateMealList(){
    lifecycleScope.launch {
        viewModel.items.observe(viewLifecycleOwner, Observer {
            when(it){
                is State.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is State.Success -> {
                    val list = it.toData()
                    Log.i("home",list.toString())
                    val adapter = FavoraiteFragmentRvAdapter(list!!, this@favoriate)
                    binding.favoriateItemsRv.layoutManager = LinearLayoutManager(requireContext()) // Set the layout manager
                    binding.favoriateItemsRv.adapter = adapter
                    binding.progressbar.visibility = View.GONE
                }
                is State.Error ->{
                    binding.errorTv.text = it.message
                    binding.progressbar.visibility = View.GONE
                }
                else -> {
                    binding.progressbar.visibility = View.GONE
                }
            }
        })
    }
}

    override fun onFavoriateItemClicked(meal: Meal) {
        val intent = Intent(requireActivity(), MealActivity::class.java).apply {
            putExtra("meal",meal.idMeal)
        }
        startActivity(intent)
    }

    override fun onFavoriateBtnClicked(meal: Meal) {

    }
}