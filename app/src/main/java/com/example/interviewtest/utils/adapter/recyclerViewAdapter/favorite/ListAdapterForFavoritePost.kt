package com.example.interviewtest.utils.adapter.recyclerViewAdapter.favorite


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.interviewtest.R
import com.example.interviewtest.db.PostEntity

class ListAdapterForFavoritePost( ) :
    ListAdapter<PostEntity, ViewHolderForFavoritePost>(DiffUtilForPost()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderForFavoritePost {
        return ViewHolderForFavoritePost(
            itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.post_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolderForFavoritePost, position: Int) {
        holder.bind(item = getItem(position))
    }
}