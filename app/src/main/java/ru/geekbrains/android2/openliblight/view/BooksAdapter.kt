package ru.geekbrains.android2.openliblight.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import ru.geekbrains.android2.openliblight.R
import ru.geekbrains.android2.openliblight.model.Work
import ru.geekbrains.android2.openliblight.utils.setStartDrawableImageFromUri
import ru.geekbrains.android2.openliblight.view.BooksAdapter.SearchResultViewHolder

internal class BooksAdapter : RecyclerView.Adapter<SearchResultViewHolder>() {

    private var results: List<Work> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultViewHolder {
        return SearchResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, null)
        )
    }

    override fun onBindViewHolder(
        holder: SearchResultViewHolder,
        position: Int
    ) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }

    fun updateResults(results: List<Work>) {
        this.results = results
        notifyDataSetChanged()
    }

    internal class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(searchResult: Work) {
            itemView.bookTitle.setStartDrawableImageFromUri("https://covers.openlibrary.org/b/ID/${searchResult.coverId}-M.jpg")
            itemView.bookTitle.text = searchResult.title
        }
    }
}