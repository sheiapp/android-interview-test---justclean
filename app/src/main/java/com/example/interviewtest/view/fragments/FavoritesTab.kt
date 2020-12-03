package com.example.interviewtest.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.interviewtest.R
import com.example.interviewtest.databinding.FragmentFavoritesTabBinding
import com.example.interviewtest.utils.adapter.recyclerViewAdapter.Favorite.ListAdapterForFavoritePost
import com.example.interviewtest.viewModel.MainViewModel


class FavoritesTab : Fragment(R.layout.fragment_favorites_tab) {
    private var _binding: FragmentFavoritesTabBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<MainViewModel>()
    private val mAdapter: ListAdapterForFavoritePost by lazy {
        ListAdapterForFavoritePost()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesTabBinding.bind(view)
        observeListOfFavoritePost()
        setupFavoritePostRecyclerView()
        observeMessages()
    }

    private fun setupFavoritePostRecyclerView() {
        binding.favoriteRecyclerView.adapter = mAdapter
    }


    private fun observeListOfFavoritePost() {
        showProgressBar()
        viewModel.getAllFavoriteDataFromFavoriteEntity()
        viewModel.allFavoritePostFromDB.observe(viewLifecycleOwner) {
            hideProgressBar()
            if (it != null) {
                mAdapter.submitList(it)
            }
        }
    }
    private fun observeMessages() {
        viewModel.message.observe(viewLifecycleOwner) {
            hideProgressBar()
        }
    }
    private fun showProgressBar(){
        binding.progressBar.visibility=View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility=View.INVISIBLE
    }


}