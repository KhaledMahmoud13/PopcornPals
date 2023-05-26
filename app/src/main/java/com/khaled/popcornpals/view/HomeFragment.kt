package com.khaled.popcornpals.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.khaled.popcornpals.R
import com.khaled.popcornpals.adapter.MovieItemAdapter
import com.khaled.popcornpals.databinding.FragmentHomeBinding
import com.khaled.popcornpals.viewmodel.MovieViewModel

class HomeFragment : Fragment() {

    private val viewModel: MovieViewModel by lazy {
        ViewModelProvider(this)[MovieViewModel::class.java]
    }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.moviesList.adapter = MovieItemAdapter(MovieItemAdapter.OnClickListener { movie -> })

        return binding.root
    }
}