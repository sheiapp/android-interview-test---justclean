package com.example.interviewtest.utils.adapter.recyclerViewAdapter.comment

import androidx.recyclerview.widget.DiffUtil
import com.example.interviewtest.model.CommentsResponseItem

class DiffUtilForComment : DiffUtil.ItemCallback<CommentsResponseItem>() {
    override fun areItemsTheSame(
        oldItem: CommentsResponseItem,
        newItem: CommentsResponseItem
    ): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(
        oldItem: CommentsResponseItem,
        newItem: CommentsResponseItem
    ): Boolean {
        return newItem == oldItem
    }
}