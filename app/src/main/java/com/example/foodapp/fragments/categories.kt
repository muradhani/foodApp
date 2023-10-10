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
import androidx.navigation.fragment.findNavController
import com.example.foodapp.R
import com.example.foodapp.activites.CategoryItems
import com.example.foodapp.adapters.CategoryFragmentAdapter
import com.example.foodapp.adapters.CategoryFragmentListener
import com.example.foodapp.databinding.CategoriesFragmentRvItemBinding
import com.example.foodapp.databinding.FragmentCategoriesBinding
import com.example.foodapp.pojo.Category
import com.example.foodapp.state.State
import com.example.foodapp.viewmodels.CategoryFragmentViewModel
import com.example.foodapp.viewmodels.FavoriateFramgentViewModel
import kotlinx.coroutines.launch

class categories : Fragment() , CategoryFragmentListener {
    val viewModel: CategoryFragmentViewModel by viewModels()
    lateinit var binding:FragmentCategoriesBinding
    var list: MutableList<Category> = mutableListOf()
    lateinit var adapter:CategoryFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_categories,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getCategoryList()
            observeCateogryList()
            searchOnClickListener()
            adapter = CategoryFragmentAdapter(list,this@categories)
            binding.categoriesRv.adapter = adapter
        }

    }
    private fun searchOnClickListener() {
        binding.searchBtn.setOnClickListener {
            findNavController().navigate(R.id.action_categories_to_searchFragment)
        }
    }

    private fun observeCateogryList() {
        lifecycleScope.launch {
            viewModel.categories.observe(viewLifecycleOwner, Observer {
                when(it){
                    is State.Success -> {
                        list = it.data.toMutableList()
                        adapter.updateData(list)
                    binding.loadingProgressBar.visibility = View.GONE
                }
                is State.Loading -> {
                binding.loadingProgressBar.visibility = View.VISIBLE
            }
                    else -> {
                        binding.loadingProgressBar.visibility = View.GONE
                    }
                }
            })
        }
    }

    override fun onCategoryClicked(categoryItem: Category, position: Int) {
        val intent = Intent(requireActivity(), CategoryItems::class.java)
        intent.putExtra("category",categoryItem.strCategory)
        startActivity(intent)
    }

}