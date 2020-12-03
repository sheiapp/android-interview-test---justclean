package com.example.interviewtest.utils.adapter.recyclerViewAdapter.Favorite


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.interviewtest.R
import com.example.interviewtest.db.FavoriteEntity
import com.example.interviewtest.db.PostEntity

class ListAdapterForFavoritePost( ) :
    ListAdapter<FavoriteEntity, ViewHolderForFavoritePost>(DiffUtilForFavoritePost()) {
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