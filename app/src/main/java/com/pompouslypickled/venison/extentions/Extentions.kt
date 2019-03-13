package com.pompouslypickled.venison.extentions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes,this, attachToRoot)
}

fun View.visible(isVisible: Boolean) {
    if (isVisible){
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.GONE
    }
}

