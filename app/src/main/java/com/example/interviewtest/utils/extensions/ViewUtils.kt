package com.example.interviewtest.utils.extensions

import android.view.View
import androidx.core.content.ContextCompat
import com.example.interviewtest.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


/**
 * show simple snack bar
 */
fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.setBackgroundTint(ContextCompat.getColor(this.context, R.color.purple_500))
    snack.setTextColor(ContextCompat.getColor(this.context, R.color.white))
    snack.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
    snack.show()
}