package com.khaled.popcornpals.presentation.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
import com.khaled.popcornpals.util.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val movieViewModel: MovieViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: MovieCategoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("TAG", "onCreateView Home: $movieViewModel")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = movieViewModel

//        Log.d("TAG", moviesViewModel.inTheatersMovies.value.toString())

        binding.logout.setOnClickListener {
            authViewModel.logout {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }

        binding.name.text = FirebaseAuth.getInstance().currentUser?.displayName

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        val imageList = ArrayList<SlideModel>()

        movieViewModel.mostPopularMovie.observe(viewLifecycleOwner, Observer { popularMovies ->
            val slideList = popularMovies.map { SlideModel(it.image, ScaleTypes.CENTER_INSIDE) }
            imageList.clear()
            imageList.addAll(slideList)
            binding.imageSlider.setImageList(imageList)
        })

        categoryAdapter = MovieCategoryAdapter(MovieItemAdapter.OnClickListener { movie ->
            movieViewModel.displayMovieDetails(movie.id)
        })

        movieViewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer { movie ->
            if (movie != null) {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToShowDetailFragment(movie)
                )
                movieViewModel.displayMovieDetailsComplete()
            }
        })

        binding.moviesList.adapter = categoryAdapter

        movieViewModel.movieCategories.observe(viewLifecycleOwner, Observer { categories ->
            categoryAdapter.submitList(categories)
        })

        movieViewModel.status.observe(viewLifecycleOwner) { networkStatus ->
            if (networkStatus == NetworkStatus.DONE) {
                Log.d("HomeFragment", "Network status: DONE")
            } else if (networkStatus == NetworkStatus.ERROR) {
                Log.e("HomeFragment", "Network status: ERROR")
            } else if (networkStatus == NetworkStatus.LOADING) {
                Log.d("HomeFragment", "Network status: LOADING")
            }
        }

        return binding.root
    }
}