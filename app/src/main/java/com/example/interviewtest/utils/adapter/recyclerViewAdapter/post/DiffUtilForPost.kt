package com.example.interviewtest.utils.adapter.recyclerViewAdapter.post

import androidx.recyclerview.widget.DiffUtil
import com.example.interviewtest.db.PostEntity

class DiffUtilForPost : DiffUtil.ItemCallback<PostEntity>() {
    override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return newItem == oldItem
    }

}