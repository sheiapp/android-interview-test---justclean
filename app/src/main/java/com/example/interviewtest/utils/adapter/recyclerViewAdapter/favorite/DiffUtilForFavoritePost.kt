package com.example.interviewtest.utils.adapter.recyclerViewAdapter.favorite

import androidx.recyclerview.widget.DiffUtil
import com.example.interviewtest.db.FavoriteEntity

class DiffUtilForFavoritePost : DiffUtil.ItemCallback<FavoriteEntity>() {
    override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
        return newItem == oldItem
    }

}