package com.khaled.popcornpals.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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
            searchViewModel.displayMovieDetails(movie.id)
        })

        binding.searchedMoviesList.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = searchItemAdapter
        }

        searchViewModel.searchedMovies.observe(viewLifecycleOwner) { items ->
            searchItemAdapter.submitList(items)
        }

        searchViewModel.navigateToSelectedMovie.observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                this.findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToShowDetailFragment(movie)
                )
                searchViewModel.displayMovieDetailsComplete()
            }
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.getSearchedMovies(query!!)
                binding.search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return binding.root
    }
}