package com.example.internet_test.model

import android.content.Context
import android.graphics.Bitmap
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


fun View.snackbar(message:String, duration: Int = Snackbar.LENGTH_LONG){
    Snackbar.make(this, message, duration).show()
}

fun Context.toast(message:String, duration: Int = Toast.LENGTH_LONG){
    Toast.makeText(this, message, duration).show()
}

fun View.fadeVisibility(visibility: Int, duration: Long = 300) {
    val transition: Transition = Fade()
    transition.duration = duration
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    this.visibility = visibility
}

fun RecyclerView.layoutManagerCustom(){
    this.setHasFixedSize(true)
    this.layoutManager =  LinearLayoutManager(this.context)
}


