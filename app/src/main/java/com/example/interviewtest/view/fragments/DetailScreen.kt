package com.example.interviewtest.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.interviewtest.R
import com.example.interviewtest.databinding.FragmentDetailScreenBinding
import com.example.interviewtest.utils.objects.Constants.argKey
import com.example.interviewtest.utils.adapter.recyclerViewAdapter.comment.ListAdapterForComment
import com.example.interviewtest.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailScreen : Fragment(R.layout.fragment_detail_screen) {
    @Inject
    lateinit var glideRequestManager: RequestManager
    private val factory by lazy {
        DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
    }
    private var _binding: FragmentDetailScreenBinding? = null
    private val binding get() = _binding!!
    private val mAdapter: ListAdapterForComment by lazy {
        ListAdapterForComment(
            glideRequestManager,
            factory
        )
    }
    private val viewModel by activityViewModels<MainViewModel>()
    private var postID: Int? = null
    override fun onDestroyView() {
        mAdapter.submitList(null)
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailScreenBinding.bind(view)
        postID = arguments?.getInt(argKey)
        setupRecyclerView()
        observeCommentResponse()
        setOnClickAction()
        observeMessages()
    }


    private fun setOnClickAction() {
        binding.topAppBar.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    addToFavorite()
                    true
                }
                else -> false
            }
        }
    }

    private fun addToFavorite() {
        if (postID != null) {
            viewModel.checkTheFavoritePostAlreadyExist(postID!!)
        }
    }

    private fun setupRecyclerView() {
        binding.commentRecyclerView.adapter = mAdapter
    }


    private fun observeCommentResponse() {
        if (postID != null) {
            showProgressBar()
            viewModel.getCommentsFromAPI(postID!!).observe(viewLifecycleOwner) {
                hideProgressBar()
                mAdapter.submitList(it)
            }
        }
    }

    private fun observeMessages() {
        viewModel.message.observe(viewLifecycleOwner) {
            hideProgressBar()
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}