package com.example.foodapp.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.foodapp.R
import com.example.foodapp.activites.MealActivity
import com.example.foodapp.adapters.SearchFragmentAdapter
import com.example.foodapp.adapters.SearchItemListener
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.dto.MealDto
import com.example.foodapp.state.State
import com.example.foodapp.viewmodels.SearchFragmentViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(), SearchItemListener {
    lateinit var binding :FragmentSearchBinding
    private val viewModel: SearchFragmentViewModel by viewModels()
    private lateinit var adapter : SearchFragmentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inizializeRecycleView()
        inizializeSearchEditText()
        observeSearchItems()
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeSearchItems() {
        viewModel.searchResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is State.Loading -> {
                    binding.noItemsTv.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is State.Success ->{
                    if (it.toData() != null){
                        adapter.updateData(it.toData()!!)
                        binding.noItemsTv.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                    }else if(it.toData() == null || it.data.isEmpty()){
                        binding.noItemsTv.visibility = View.VISIBLE
                    }
                    else{
                        binding.noItemsTv.visibility = View.VISIBLE
                    }

                }
                is State.Error -> {
                    binding.noItemsTv.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }
                else -> {
                    binding.noItemsTv.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun inizializeSearchEditText() {
        var searchJob :Job? = null
        binding.searchEt.addTextChangedListener {
            searchJob?.cancel()
            Log.i("input",it.toString())
            if (it.toString().isEmpty()){
                adapter.updateData(mutableListOf())
            }else{

                searchJob = lifecycleScope.launch {
                    delay(800)
                    viewModel.searchMeal(it.toString())
                }
            }
        }
    }

    private fun inizializeRecycleView() {
        adapter = SearchFragmentAdapter(listener = this@SearchFragment)
        binding.searchRv.adapter = adapter
    }


    override fun onMealClicked(meal: MealDto, position: Int) {
        val intent = Intent(requireActivity(), MealActivity::class.java).apply {
            putExtra("meal",meal.idMeal)
        }
        startActivity(intent)
    }

}