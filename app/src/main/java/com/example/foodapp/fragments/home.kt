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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRandomMeal()
        viewModel.getListMeals()
        viewModel.getCategoryList()
       viewModel.observeMeal().observe(viewLifecycleOwner, Observer {
         Glide.with(this).load(it.strMealThumb).into(binding.randomMealIv)
            meal = it
       })

        binding.firstCardView.setOnClickListener {
            val intent = Intent(requireActivity(), MealActivity::class.java).apply {
                putExtra("meal",meal.idMeal.toString())
            }
            startActivity(intent)
        }
        viewModel.mealList.observe(viewLifecycleOwner, Observer {
            mealList = it
            val adapter = MealAdapter(mealList, this)
            binding.recyclerViewRandom.adapter = adapter
        })
        viewModel.categorieslmutable.observe(viewLifecycleOwner, Observer {
            val CategoryList = it
            val adapter = CategoryAdapter(CategoryList, this)
            binding.rvCategories.adapter = adapter
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