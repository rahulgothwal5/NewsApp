package com.warlock.newsapp.ui.fragment.top_headlines

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.warlock.newsapp.R
import com.warlock.newsapp.databinding.ListItemHeadlineBinding
import com.warlock.newsapp.model.Article

class HeadlineAdapter(
    val list: ArrayList<Article>,
    val listener: (item: Article) -> Unit
) :
    ListAdapter<Article, HeadlineAdapter.ArticleViewHolder>(
        DiffCallback()
    ), Parcelable {

    class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem.publishedAt == newItem.publishedAt
        }

        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem.publishedAt == newItem.publishedAt
        }
    }

    inner class ArticleViewHolder(private val binding: ListItemHeadlineBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(result: Article) {
            binding.headline = result
            binding.root.setOnClickListener {
                listener(result)
            }
        }
    }

    constructor(parcel: Parcel) : this(
        TODO("list"),
        TODO("listener")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_headline,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HeadlineAdapter> {
        override fun createFromParcel(parcel: Parcel): HeadlineAdapter {
            return HeadlineAdapter(parcel)
        }

        override fun newArray(size: Int): Array<HeadlineAdapter?> {
            return arrayOfNulls(size)
        }
    }
}