package com.example.interviewtest.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.bumptech.glide.RequestManager
import com.example.interviewtest.R
import com.example.interviewtest.databinding.FragmentDetailScreenBinding
import com.example.interviewtest.utils.adapter.recyclerViewAdapter.comment.ListAdapterForComment
import com.example.interviewtest.utils.extensions.Status
import com.example.interviewtest.utils.Constants.argKey
import com.example.interviewtest.viewModel.MainViewModel
import com.example.interviewtest.worker.MainWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailScreen : Fragment(R.layout.fragment_detail_screen) {

    @Inject
    lateinit var glideRequestManager: RequestManager
    @Inject
    lateinit var mWorkManager: WorkManager
    private var _binding: FragmentDetailScreenBinding? = null
    private val binding get() = _binding!!
    private val mAdapter: ListAdapterForComment by lazy {
        ListAdapterForComment(glideRequestManager)
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
        observeWorkRequest()
        observeCommentResponse()
        setOnClickAction()

    }

    private fun observeWorkRequest() {
        viewModel.shouldScheduleWork.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                scheduleWork()
            }
        }
    }

    private fun scheduleWork() {
        val workConstraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest =
            OneTimeWorkRequestBuilder<MainWorker>().setConstraints(workConstraints).build()
        //val workStatus = mWorkManager.getWorkInfoByIdLiveData(workRequest.id)
        mWorkManager.enqueue(workRequest)
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
            viewModel.getCommentsFromAPI(postID!!).observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        mAdapter.submitList(it.data)
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
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}