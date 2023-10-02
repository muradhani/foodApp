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
import com.bumptech.glide.Glide
import com.example.foodapp.activites.CategoryItems
import com.example.foodapp.activites.MealActivity
import com.example.foodapp.R
import com.example.foodapp.adapters.CategoryAdapter
import com.example.foodapp.adapters.CategoryListener
import com.example.foodapp.adapters.MealAdapter
import com.example.foodapp.adapters.MealListener
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealX
import com.example.foodapp.state.State
import com.example.foodapp.viewmodels.HomeFramgmentViewModel
import java.io.Serializable


class home : Fragment(),MealListener ,CategoryListener{
    lateinit var binding:FragmentHomeBinding
    val viewModel:HomeFramgmentViewModel by viewModels()
    lateinit var meal: Meal
    lateinit var mealList: List<MealX>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeRandomMeal()
        observeRandomMealList()
        observeCategoryList()
        binding.firstCardView.setOnClickListener {
            var intent = Intent(requireActivity(), MealActivity::class.java).apply {
                putExtra("meal",meal.idMeal.toString())
            }
            startActivity(intent)
        }
    }
    private fun observeCategoryList(){
        viewModel.categoriesList.observe(viewLifecycleOwner, Observer {
            when(it){
                is State.Success -> {
                    var CategoryList = it.toData()
                    var adapter = CategoryAdapter(CategoryList!!.toMutableList(), this)
                    binding.rvCategories.adapter = adapter
                    binding.cardViewProgressbar.visibility = View.GONE
                }
                is State.Loading -> {
                    binding.cardViewProgressbar.visibility = View.VISIBLE
                }
                else -> {
                    // Do nothing
                }
            }
        })
    }
    private fun observeRandomMealList(){
        viewModel.randomMealList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is State.Success -> {
                    mealList = it.data
                    val adapter = MealAdapter(mealList.toMutableList(), this)
                    binding.recyclerViewRandom.adapter = adapter
                    binding.recyclerViewRandomFrameLayoutProgressBar.visibility = View.GONE
                }
                is State.Loading -> {
                    binding.randomMealProgressBar.visibility = View.VISIBLE
                }
                else -> {
                    // Do nothing
                }
            }
        })
    }

    private fun observeRandomMeal() {
        viewModel.randomMeal.observe(viewLifecycleOwner, Observer {
            when (it) {
                is State.Success -> {
                    Glide.with(this).load(it.data.strMealThumb).into(binding.randomMealIv)
                    binding.randomMealProgressBar.visibility = View.GONE
                    meal = it.data
                }
                is State.Loading -> {
                    binding.randomMealProgressBar.visibility = View.VISIBLE
                }
                else -> {
                    // Do nothing
                }
            }
        })
    }

    override fun onMealClicked(meal: MealX) {
        //Toast.makeText(requireContext(),meal.idMeal,Toast.LENGTH_SHORT).show()
            val intent = Intent(requireActivity(), MealActivity::class.java).apply {
                putExtra("meal",meal.idMeal)
            }
            startActivity(intent)
    }

    override fun onFavouriteClicked(meal: MealX) {

    }

    override fun onCategoryClicked(categoryItem: Category) {
       val intent = Intent(requireActivity(), CategoryItems::class.java)
        intent.putExtra("category",categoryItem.strCategory)
        startActivity(intent)
    }

}