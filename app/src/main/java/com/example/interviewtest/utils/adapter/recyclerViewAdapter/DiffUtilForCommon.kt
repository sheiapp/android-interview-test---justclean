package com.example.interviewtest.utils.adapter.recyclerViewAdapter

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.DiffUtil

class DiffUtilForCommon<T> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        Log.d("diff", oldItem?.equals(newItem).toString())
        return oldItem?.equals(newItem)!!
    }


    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        Log.d("diff", (oldItem==newItem).toString())
        return oldItem==newItem
    }
}