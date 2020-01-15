package com.example.images.view.albumpreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.images.R
import com.example.images.entities.Album
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_album_preview.view.*

class AlbumPreviewAdapter(
    private val listener: Listener
): RecyclerView.Adapter<AlbumPreviewAdapter.ViewHolder>() {

    private val list = arrayListOf<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album_preview, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    fun addToList(list: List<Album>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    interface Listener {
        fun onItemClicked(imageUrl: String)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val imageView = view.item_image

        fun bind(position: Int) {
            val item = list[position]
            Picasso.get()
                .load(item.thumbnailUrl)
                .into(imageView)

            if (item.url != null) {
                itemView.setOnClickListener { listener.onItemClicked(item.url) }
            }
        }
    }
}