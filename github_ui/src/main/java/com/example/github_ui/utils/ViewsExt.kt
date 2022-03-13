package com.example.github_ui.utils

import android.content.Context
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .fitCenter()
        .into(this)

}


fun EditText.textChange(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun afterTextChanged(p0: Editable?) = Unit
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                offer(text)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }
}

fun RecyclerView.observeRecycler(): Flow<Boolean> {
    return callbackFlow {
        val listener = object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!canScrollVertically(1)
                    && dy > 0 && isLastItemDisplaying
                ) {
                    offer(true)
                }
            }
        }
        addOnScrollListener(listener)
        awaitClose { removeOnScrollListener(listener) }

    }
}

val RecyclerView.isLastItemDisplaying: Boolean
    get() {
        adapter?.let {
            if (it.itemCount != 0) {
                val lastVisibleItemPosition: Int = getLastVisibleItemPosition
                return lastVisibleItemPosition != RecyclerView.NO_POSITION &&
                        lastVisibleItemPosition == it.itemCount - 1
            }
        }
        return false
    }

private val RecyclerView.getLastVisibleItemPosition: Int
    get() = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

fun Context.showToast(message: String, duration: Int, action: (() -> Unit)? = null) {
    Toast.makeText(this, message, duration).show()
    action?.let {
        val handler = Handler()
        handler.postDelayed({
            it.invoke()
        }, duration.toLong())
    }
}