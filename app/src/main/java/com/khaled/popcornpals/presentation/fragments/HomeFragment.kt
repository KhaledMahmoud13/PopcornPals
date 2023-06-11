package com.khaled.popcornpals.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.auth.FirebaseAuth
import com.khaled.popcornpals.R
import com.khaled.popcornpals.presentation.adapter.MovieItemAdapter
import com.khaled.popcornpals.databinding.FragmentHomeBinding
import com.khaled.popcornpals.presentation.adapter.MovieCategoryAdapter
import com.khaled.popcornpals.presentation.viewmodel.AuthViewModel
import com.khaled.popcornpals.presentation.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: MovieCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.logout.setOnClickListener {
            authViewModel.logout {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }

        binding.name.text = FirebaseAuth.getInstance().currentUser?.displayName
        Log.d("TAG",FirebaseAuth.getInstance().currentUser?.displayName.toString())

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        val imageList = ArrayList<SlideModel>()

        viewModel.mostPopularMovie.observe(viewLifecycleOwner, Observer { PopularMovies ->
            val slideList = PopularMovies.map { SlideModel(it.image, ScaleTypes.CENTER_INSIDE) }
            imageList.clear()
            imageList.addAll(slideList)
            binding.imageSlider.setImageList(imageList)
        })
        binding.imageSlider.setImageList(imageList)


        categoryAdapter = MovieCategoryAdapter(MovieItemAdapter.OnClickListener { movie ->
            viewModel.displayMovieDetails(movie.id)
            Toast.makeText(requireContext(), movie.id, Toast.LENGTH_SHORT).show()
        })
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer { movie ->
            if (movie != null) {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToShowDetailFragment(movie))
                viewModel.displayMovieDetailsComplete()
            }
        })
        binding.moviesList.adapter = categoryAdapter

        viewModel.movieCategories.observe(viewLifecycleOwner, Observer { categories ->
            categoryAdapter.submitList(categories)
        })

        return binding.root
    }
}