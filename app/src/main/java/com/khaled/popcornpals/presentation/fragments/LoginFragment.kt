package com.khaled.popcornpals.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.khaled.popcornpals.R
import com.khaled.popcornpals.databinding.FragmentLoginBinding
import com.khaled.popcornpals.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.loginBtn.setOnClickListener {
            login()
        }
        binding.signupTv.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
        binding.forgetPasswordTv.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }
        return binding.root
    }

    private fun login() {
        binding.loginBtn.startAnimation()
        authViewModel.loginUser(
            binding.emailEt.text.toString(),
            binding.passwordEt.text.toString(),
            {
                navigateToSplash()
//            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            },
            {
                binding.loginBtn.revertAnimation {
                    binding.loginBtn.background = resources.getDrawable(R.drawable.rounded_button)
                }
                Toast.makeText(
                    requireContext(),
                    it,
                    Toast.LENGTH_LONG
                ).show()
            },
        )
    }

    private fun navigateToSplash() {
        val action =
            LoginFragmentDirections.actionLoginFragmentToSplashFragment()
        action.fromLogin = true
        findNavController().navigate(action)
    }
}