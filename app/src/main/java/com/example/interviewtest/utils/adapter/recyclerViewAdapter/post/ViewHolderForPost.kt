package com.example.interviewtest.utils.adapter.recyclerViewAdapter.post

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtest.db.PostEntity
import kotlinx.android.synthetic.main.post_item.view.*

class ViewHolderForPost(itemView: View):RecyclerView.ViewHolder(itemView) {
    fun bind(item: PostEntity) {
        itemView.title.text=item.title
        itemView.body.text=item.body
    }
}