package com.example.internet_test.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.internet_test.databinding.ItemLayoutBinding
import com.example.internet_test.model.BookItem
import com.example.internet_test.model.toast
import java.lang.Exception

/*class Books_Adapter(private val items: List<BookItem>, val context: Context) :
    RecyclerView.Adapter<Books_Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let { holder.bindItems(it, context) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLayoutBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bindItems(dato: BookItem, context: Context) {
            try {
                binding.title.text = "Title: ${dato.volumeInfo.title}"
                binding.author.text = "Author: ${dato.volumeInfo.authors[0]}"

            } catch (e: Exception) {
                context.toast(e.toString())
            }
        }
    }
}*/

class Books_Adapter(private val items: List<BookItem>, val context: Context) : RecyclerView.Adapter<Books_Adapter.BooksViewHolder>() {

    class BooksViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        return BooksViewHolder(
                ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        try {
            holder.binding.title.text = "Title: ${items[position].volumeInfo.title}"
            holder.binding.author.text = "Author: ${items[position].volumeInfo.authors[0]}"

        } catch (e: Exception) {
            context.toast(e.toString())
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}