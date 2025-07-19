package com.example.fitnest

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.jvm.java

class ArticleAdapter (private val articles: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){
    var onItemClick: ((Article) -> Unit)? = null
    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgArticle: ImageView = itemView.findViewById(R.id.imgArticle)
        val tvArticleTitle: TextView = itemView.findViewById(R.id.tvArticleTitle)

        init {
            // ItemView tıklanırsa burası tetiklenir
            itemView.setOnClickListener {
                onItemClick?.invoke(articles[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.makale_card, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.imgArticle.setImageResource(article.imageResId)
        holder.tvArticleTitle.text = article.title
    }

    override fun getItemCount(): Int = articles.size
}