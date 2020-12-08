package com.example.interviewtest.utils.adapter.recyclerViewAdapter.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.example.interviewtest.R
import com.example.interviewtest.model.CommentsResponseItem
import com.example.interviewtest.utils.adapter.recyclerViewAdapter.DiffUtilForCommon

class ListAdapterForComment(private var glideRequestManager: RequestManager) :
    ListAdapter<CommentsResponseItem, ViewHolderForComment>(DiffUtilForCommon()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderForComment {
        return ViewHolderForComment(
            itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.comment_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolderForComment, position: Int) {
        holder.bind(item=getItem(position),glideRequestManager)
    }
}