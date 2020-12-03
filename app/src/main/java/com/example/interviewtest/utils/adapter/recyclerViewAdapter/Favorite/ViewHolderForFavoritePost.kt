package com.example.interviewtest.utils.adapter.recyclerViewAdapter.Favorite

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtest.db.FavoriteEntity
import kotlinx.android.synthetic.main.post_item.view.*

class ViewHolderForFavoritePost(itemView: View):RecyclerView.ViewHolder(itemView) {
    fun bind(item: FavoriteEntity) {
        itemView.title.text=item.title
        itemView.body.text=item.body
    }
}