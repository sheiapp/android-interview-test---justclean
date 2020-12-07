package com.example.interviewtest.utils.adapter.recyclerViewAdapter.post


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.interviewtest.R
import com.example.interviewtest.db.PostEntity
import com.example.interviewtest.utils.adapter.recyclerViewAdapter.favorite.DiffUtilForPost

class ListAdapterForPost(private val clickEventData:(Int) ->Unit ) :
    ListAdapter<PostEntity, ViewHolderForPost>(DiffUtilForPost()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderForPost {
        return ViewHolderForPost(
            itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.post_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolderForPost, position: Int) {
        holder.bind(item = getItem(position))
        //currentList
        holder.itemView.setOnClickListener {
            clickEventData(getItem(position).id)
        }
    }
}