package com.example.images.view.albumslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.images.R
import com.example.images.entities.Album
import kotlinx.android.synthetic.main.item_album_title.view.*

class AlbumsListAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<AlbumsListAdapter.ViewHolder>() {

    private val list = arrayListOf<Album>()

    fun addToList(list: List<Album>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_album_title, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    interface Listener {
        fun onItemClicked(albumId: Int)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val id = view.item_id
        private val title = view.item_title

        fun bind(position: Int) {
            val item = list[position]
            id?.text = item.id?.toString()
            title?.text = item.title
            if (item.id != null) {
                itemView.setOnClickListener { listener.onItemClicked(item.id) }
            }
        }
    }
}