package com.khaled.popcornpals.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khaled.popcornpals.R
import com.khaled.popcornpals.databinding.FragmentShowDetailBinding
import com.khaled.popcornpals.presentation.adapter.ActorItemAdapter
import com.khaled.popcornpals.presentation.viewmodel.MovieViewModel
import com.khaled.popcornpals.util.invisible
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class ShowDetailFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding: FragmentShowDetailBinding
    private lateinit var actorItemAdapter: ActorItemAdapter
    private val args by navArgs<ShowDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_detail, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        args.movie.image.let {
            val imgUri = args.movie.image.toUri().buildUpon().scheme("https").build()
            Glide.with(binding.movieImg.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(binding.movieImg)
        }
        binding.movieTv.text = args.movie.title
        binding.yearTv.text = args.movie.year
        binding.genresTv.text = args.movie.genres
        binding.runtimeTv.text = args.movie.runtimeStr
        if (args.movie.imDbRating != "null") {
            binding.rating.rating = args.movie.imDbRating.toFloat().roundToInt().toFloat() / 2
        } else {
            binding.rating.invisible()
        }
        binding.storyContentTv.text = args.movie.plot
        actorItemAdapter = ActorItemAdapter()
        actorItemAdapter.submitList(args.movie.actorList)
        binding.castList.apply {
            adapter = actorItemAdapter
        }
        return binding.root
    }
}