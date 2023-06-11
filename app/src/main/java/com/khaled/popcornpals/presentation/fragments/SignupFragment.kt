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
import com.khaled.popcornpals.databinding.FragmentSignupBinding
import com.khaled.popcornpals.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        binding.signupBtn.setOnClickListener {
            register()
        }
        binding.loginTv.setOnClickListener {
            findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
        }
        return binding.root
    }

    private fun register() {
        binding.signupBtn.startAnimation()
        authViewModel.registerUser(
            binding.emailEt.text.toString(),
            binding.passwordEt.text.toString(),
            {
                navigateToSplash()
            },
            {
                binding.signupBtn.revertAnimation {
                    binding.signupBtn.background = resources.getDrawable(R.drawable.rounded_button)
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
        findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToHomeFragment())
    }
}