package com.example.feature_supervisor_research.aspirantdetails.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.Article
import com.example.feature_supervisor_research.databinding.ItemDetailsResearchBinding

class AspirantDetailsAdapter(
    val acceptListener: (Article, Int) -> Unit,
    val declineListener: (Article, Int) -> Unit,
    val linkListener: (String) -> Unit,
) :
    RecyclerView.Adapter<AspirantDetailsAdapter.ViewHolder>() {

    var listOfArticles = AsyncListDiffer(this, differDetailsCallback)

    inner class ViewHolder(private val binding: ItemDetailsResearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                nameTextView.text = article.name
                statusTextView.text = article.status
                when (article.status) {
                    "Sent" -> statusTextView.setBackgroundResource(com.postgraduate.cabinet.ui.R.drawable.border_text_yellow)
                    "Approve" -> statusTextView.setBackgroundResource(com.postgraduate.cabinet.ui.R.drawable.border_text_green)
                    "Decline" -> statusTextView.setBackgroundResource(com.postgraduate.cabinet.ui.R.drawable.border_text_red)
                }
                acceptButton.setOnClickListener {
                    acceptListener.invoke(article, adapterPosition)
                }
                declineButton.setOnClickListener {
                    declineListener.invoke(article, adapterPosition)
                }
                nameTextView.setOnClickListener {
                    linkListener.invoke(article.url)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =
            ItemDetailsResearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount() = listOfArticles.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfArticles.currentList[position])
    }
}