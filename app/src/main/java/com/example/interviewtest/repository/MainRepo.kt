package com.example.interviewtest.repository

import androidx.lifecycle.LiveData
import com.example.interviewtest.db.PostEntity
import com.example.interviewtest.model.CommentsResponseItem
import com.example.interviewtest.network.AppServices
import com.example.interviewtest.network.NetworkConnectionStatus
import com.example.interviewtest.utils.extensions.Resource
import com.example.interviewtest.utils.extensions.apiValidator
import javax.inject.Inject

class MainRepo @Inject constructor(
    private val appServices: AppServices,
    private val networkConnectionStatus: NetworkConnectionStatus
) : MainRepository {

    override suspend fun getPosts(): Resource<List<PostEntity>> =
        apiValidator { appServices.getPost() }


    override suspend fun getComments(post_id: Int): Resource<List<CommentsResponseItem>> =
        apiValidator { appServices.getComments(post_id) }


    override fun getNetworkConnectionStatus(): LiveData<Boolean> {
        return networkConnectionStatus
    }
}