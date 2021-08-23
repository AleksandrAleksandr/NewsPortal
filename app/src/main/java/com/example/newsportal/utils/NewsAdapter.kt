package com.example.newsportal.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsportal.R
import com.example.newsportal.domain.model.Article
import com.squareup.picasso.Picasso

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ItemViewHolder>() {

    private var data = listOf<Article>()

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.title
        if (item.urlToImage.isEmpty()){

        }
        else {
            Picasso.get().load(item.urlToImage).into(holder.imageView)
        }

    }

    override fun getItemCount() = data.size

    fun setData(heroes: List<Article>) {
        data = heroes
        notifyDataSetChanged()
    }
}