package com.example.interviewtest.utils.adapter.recyclerViewAdapter.comment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.interviewtest.model.CommentsResponseItem
import com.example.interviewtest.utils.Constants.DummyImageUrl
import kotlinx.android.synthetic.main.comment_item.view.*

class ViewHolderForComment constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    fun bind(
        item: CommentsResponseItem, glideRequestManager: RequestManager
    ) {
        itemView.name.text = item.name
        itemView.email.text = item.email
        itemView.body.text = item.body
        glideRequestManager
            .load(DummyImageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(itemView.avatar)
    }
}