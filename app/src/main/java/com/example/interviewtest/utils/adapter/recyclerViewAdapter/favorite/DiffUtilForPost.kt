package com.example.interviewtest.utils.adapter.recyclerViewAdapter.favorite

import androidx.recyclerview.widget.DiffUtil
import com.example.interviewtest.db.PostEntity

class DiffUtilForPost<T> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return (oldItem as PostEntity).id == (newItem as PostEntity).id
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return (oldItem as PostEntity) == (oldItem as PostEntity)
    }
}