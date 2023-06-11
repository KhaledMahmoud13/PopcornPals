package com.khaled.popcornpals.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.khaled.popcornpals.R
import com.khaled.popcornpals.databinding.FragmentSearchBinding
import com.khaled.popcornpals.presentation.adapter.SearchItemAdapter
import com.khaled.popcornpals.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchItemAdapter: SearchItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = searchViewModel

        searchItemAdapter = SearchItemAdapter(SearchItemAdapter.OnClickListener { movie ->

        })

        binding.imageButton.setOnClickListener {
            searchViewModel.getSearchedMovies(binding.searchTextView.text.toString())
        }
        return binding.root
    }
}