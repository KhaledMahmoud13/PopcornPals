package com.khaled.popcornpals.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.khaled.popcornpals.R
import com.khaled.popcornpals.databinding.FragmentSplashBinding
import com.khaled.popcornpals.presentation.viewmodel.AuthViewModel
import com.khaled.popcornpals.presentation.viewmodel.MovieViewModel
import com.khaled.popcornpals.util.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val movieViewModel: MovieViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "onCreateView Splash: $movieViewModel")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment=
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)

        val args by navArgs<SplashFragmentArgs>()

        if (args.fromLogin) {
            movieViewModel.getAllMovies()
            movieViewModel.status.observe(viewLifecycleOwner) {
                if (it == NetworkStatus.DONE) {
                    movieViewModel.movieCategories.observe(viewLifecycleOwner) { categories ->
                        if (categories.isNotEmpty()) {
                            navigateToHome()
                        }
                    }
                }
            }
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                authViewModel.authenticationState.observe(
                    viewLifecycleOwner
                ) { authenticationState ->
                    when (authenticationState) {
                        AuthViewModel.AuthenticationState.AUTHENTICATED -> {
                            movieViewModel.getAllMovies()
                            movieViewModel.status.observe(viewLifecycleOwner) {
                                if (it == NetworkStatus.DONE) {
                                    movieViewModel.movieCategories.observe(viewLifecycleOwner) { categories ->
                                        if (categories.isNotEmpty()) {
                                            navigateToHome()
                                        }
                                    }
                                }
                            }
                        }

                        else -> {
                            navigateToOnBoarding()
                        }
                    }
                }
            }, 5000)
        }
        return binding.root
    }

    private fun navigateToHome() {
        findNavController().navigate(
            SplashFragmentDirections.actionSplashFragmentToHomeFragment()
        )
    }

    private fun navigateToOnBoarding() {
        findNavController().navigate(
            SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment()
        )
    }
}