package com.example.musiccatalogueapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.musiccatalogueapp.R
import com.example.musiccatalogueapp.data.model.Song

class SongAdapter: RecyclerView.Adapter<SongAdapter.ItemViewHolder>() {

    private var dataset = mutableListOf<Song>()

    inner class ItemViewHolder(view: View): ViewHolder(view){
        val artwork: ImageView = view.findViewById(R.id.song_artwork_iv)
        val artistName: TextView = view.findViewById(R.id.song_artistname_tv)
        val songTitle: TextView = view.findViewById(R.id.song_title_tv)
    }

    fun submitList(list: MutableList<Song>){
        dataset = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_song, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val song = dataset[position]

        holder.artwork.load(song.artworkUrl100){
            transformations(RoundedCornersTransformation(50f))
            placeholder(R.drawable.round_broken_image_24)
        }
        holder.songTitle.text = song.trackName
        holder.artistName.text = song.artistName
    }
}