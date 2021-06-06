package com.warlock.newsapp.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageUrl(view: ImageView, url: String?) {
        if (url.isNullOrEmpty().not())
            Glide.with(view).load(url).into(view)
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("fancyDate")
    fun getFancyDate(view: TextView, date: String?) {
        try {
            return if (date.isNullOrEmpty().not()) {

                val mDate =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault()).parse(
                        date ?: ""
                    )
                val currentDate = Calendar.getInstance().time

                val diff: Long = currentDate.time - mDate!!.time
                val seconds = diff / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24

                when {
                    days > 0 -> {
                        view.text = "$days Days ago"
                    }
                    hours > 0 -> {
                        view.text = "$hours Hours ago"
                    }
                    minutes > 0 -> {
                        view.text = "$minutes Minutes ago"
                    }
                    else -> view.text = "Recently"
                }
            } else view.text = ""
        } catch (e: Exception) {
            view.text = ""
        }


    }

}
