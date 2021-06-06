package com.warlock.newsapp.ui.fragment.news_source_listing

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.warlock.newsapp.R
import com.warlock.newsapp.databinding.ListItemNewsSourceBinding
import com.warlock.newsapp.model.NewsSource

class NewsSourceAdapter(
    val list: ArrayList<NewsSource>,
    val listener: (item: NewsSource,adapterPosition:Int) -> Unit
) :
    ListAdapter<NewsSource, NewsSourceAdapter.NewsSourceViewHolder>(
        DiffCallback()
    ), Parcelable {

    class DiffCallback : DiffUtil.ItemCallback<NewsSource>() {
        override fun areItemsTheSame(
            oldItem: NewsSource,
            newItem: NewsSource
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NewsSource,
            newItem: NewsSource
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class NewsSourceViewHolder(private val binding: ListItemNewsSourceBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(result: NewsSource) {
            binding.newsSource = result
            binding.root.setOnClickListener {
                result.isSelected = !result.isSelected
                notifyItemChanged(adapterPosition)
                listener(result,adapterPosition)
            }
        }
    }

    constructor(parcel: Parcel) : this(
        TODO("list"),
        TODO("listener")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSourceViewHolder {
        return NewsSourceViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_news_source,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsSourceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsSourceAdapter> {
        override fun createFromParcel(parcel: Parcel): NewsSourceAdapter {
            return NewsSourceAdapter(parcel)
        }

        override fun newArray(size: Int): Array<NewsSourceAdapter?> {
            return arrayOfNulls(size)
        }
    }
}