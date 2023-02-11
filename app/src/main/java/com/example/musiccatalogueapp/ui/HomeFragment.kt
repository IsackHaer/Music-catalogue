package com.example.musiccatalogueapp.ui

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.musiccatalogueapp.ApiStatus
import com.example.musiccatalogueapp.MainViewModel
import com.example.musiccatalogueapp.adapter.SongAdapter
import com.example.musiccatalogueapp.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private val currentFocus: View? = null
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val songAdapter = SongAdapter()
        binding.homeRv.adapter = songAdapter

        binding.homeSearchBtn.setOnClickListener {
            //this is to hide soft keyboard once the search button is clicked
            if (view != null){
                val inputMethodManager =
                    requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
            }

            viewModel.loadSongs(binding.homeSearchInputEdit.text.toString())
        }

        viewModel.songs.observe(viewLifecycleOwner){
            songAdapter.submitList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner){
            when (it) {
                ApiStatus.LOADING -> {
                    binding.homeProgressBar.visibility = View.VISIBLE
                    binding.homeNoInternetIv.visibility = View.GONE
                }
                ApiStatus.DONE -> {
                    binding.homeProgressBar.visibility = View.GONE
                    binding.homeNoInternetIv.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    binding.homeProgressBar.visibility = View.GONE
                    binding.homeNoInternetIv.visibility = View.VISIBLE
                }
            }
        }
    }
}