package com.example.interviewtest.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.interviewtest.R
import com.example.interviewtest.databinding.FragmentPostTabBinding
import com.example.interviewtest.utils.adapter.recyclerViewAdapter.post.ListAdapterForPost
import com.example.interviewtest.utils.extensions.Status
import com.example.interviewtest.viewModel.MainViewModel


class PostTab : Fragment(R.layout.fragment_post_tab) {
    private var _binding: FragmentPostTabBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<MainViewModel>()
    private val mAdapter: ListAdapterForPost by lazy {
        ListAdapterForPost { id ->
            handleClickEventOnPostItem(
                id
            )
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostTabBinding.bind(view)
        setupPostRecyclerView()
        checkDataAvailabilityInDataBase()
        observePostResponse()
    }

    private fun checkDataAvailabilityInDataBase() {
        viewModel.allPostFromDB.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                mAdapter.submitList(it)
            }
        }
    }

    private fun setupPostRecyclerView() {
        binding.postRecyclerView.adapter = mAdapter
    }

    private fun observePostResponse() {
        viewModel.postResponseLiveData.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    mAdapter.submitList(response.data)
                }
                Status.LOADING -> {
                    showProgressBar()
                }
                else -> {
                    hideProgressBar()
                }
            }
        }
    }


    private fun handleClickEventOnPostItem(post_id: Int) {
        val gotoDetailPage = TabContainerDirections.actionTabContainerToDetailScreen(post_id)
        findNavController().navigate(gotoDetailPage)
    }



    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }


}