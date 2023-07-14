package com.khaled.popcornpals.presentation.views

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.khaled.popcornpals.R
import com.khaled.popcornpals.databinding.FragmentOnboardingBinding

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding, container, false)
        binding.nextBtn.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment())
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            binding.background.setRenderEffect(
                RenderEffect.createBlurEffect(
                    5F,
                    5F,
                    Shader.TileMode.MIRROR
                )
            )
        }
        return binding.root
    }
}