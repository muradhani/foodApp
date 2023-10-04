package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.activites.MealActivity
import com.example.foodapp.databinding.FragmentBottomDialogFragmentBinding
import com.example.foodapp.state.State
import com.example.foodapp.viewmodels.BottomDialogFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

open class BottomDialogFragment : BottomSheetDialogFragment() {
    lateinit var binding:FragmentBottomDialogFragmentBinding
    val viewModel:BottomDialogFragmentViewModel by viewModels()
    private var mealId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(ARG_PARAM1)
            lifecycleScope.launch {
                viewModel.getMeal(mealId!!)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bottom_dialog_fragment,container,false)
        binding.root.setOnClickListener {
            val intent = Intent(requireActivity(), MealActivity::class.java).apply {
                putExtra("meal",mealId)
            }
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMeal()

    }
    fun observeMeal(){
        viewModel._mealMutable.observe(viewLifecycleOwner, Observer {
            when(it){
                is State.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is State.Success -> {
                    Glide.with(binding.root).load(it.data.strMealThumb).into(binding.ivCategoryImg)
                    binding.mealNameTv.text = it.data.strMeal
                    binding.areaTv.text = it.data.strArea
                    binding.categoryTv.text = it.data.strCategory
                    binding.progressBar.visibility = View.GONE
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BottomDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}