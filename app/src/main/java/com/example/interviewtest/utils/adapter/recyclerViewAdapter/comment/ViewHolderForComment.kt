package com.example.interviewtest.utils.adapter.recyclerViewAdapter.comment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.interviewtest.model.CommentsResponseItem
import kotlinx.android.synthetic.main.comment_item.view.*

class ViewHolderForComment(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        item: CommentsResponseItem,
        glideRequestManager: RequestManager,
        factory: DrawableCrossFadeFactory
    ) {
        //TODO since we don't provide image url
        val url = "https://miro.medium.com/max/560/1*MccriYX-ciBniUzRKAUsAw.png"

        itemView.name.text = item.name
        itemView.email.text = item.email
        itemView.body.text = item.body
        glideRequestManager.asBitmap()
            .load(url)
            .transition(BitmapTransitionOptions.withCrossFade(factory))
            .into(itemView.avatar)
    }
}