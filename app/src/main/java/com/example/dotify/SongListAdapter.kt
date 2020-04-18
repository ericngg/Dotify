package com.example.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(private val listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    var onSongClickListener: ((song: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.song, parent, false)
        return SongViewHolder(item)
    }

    override fun getItemCount(): Int = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val songName = listOfSongs[position]
        holder.bind(songName)
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val ivSongCover by lazy {itemView.findViewById<ImageView>(R.id.ivSongCover)}
        private val tvSongTitle by lazy {itemView.findViewById<TextView>(R.id.tvSongTitle)}
        private val tvSongArtist by lazy {itemView.findViewById<TextView>(R.id.tvSongArtist)}

        fun bind(song: Song) {
            ivSongCover.setImageResource(song.smallImageID)
            tvSongTitle.text = song.title
            tvSongArtist.text = song.artist

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }
        }
    }
}