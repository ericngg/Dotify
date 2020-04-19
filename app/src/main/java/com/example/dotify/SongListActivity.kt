package com.example.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class SongListActivity : AppCompatActivity() {

    private lateinit var rvSongList : RecyclerView

    private lateinit var tvSong : TextView

    private lateinit var btnShuffle : Button

    private lateinit var clMiniPlayer : ConstraintLayout

    private lateinit var listOfSong : List<Song>

    private var current : Song? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        rvSongList = findViewById(R.id.rvSongList)
        tvSong = findViewById(R.id.tvSong)
        btnShuffle = findViewById(R.id.btnShuffle)
        clMiniPlayer = findViewById(R.id.clMiniPlayer)

        // Initialize RecyclerView
        listOfSong = SongDataProvider.getAllSongs()
        var songListAdapter = SongListAdapter(listOfSong)

        // Initialize onClick and onLongClick on start up
        setOnClick(songListAdapter)

        rvSongList.adapter = songListAdapter

        // Shuffles the playlist and recreates the recycler view
        btnShuffle.setOnClickListener {
            // Shuffles playlist
            val newList = listOfSong.shuffled()

            // Handles difference
            val callback = PersonDiffCallback(listOfSong, newList)
            val result = DiffUtil.calculateDiff(callback)
            result.dispatchUpdatesTo(rvSongList.adapter as SongListAdapter)
        }

        // Opens Song screen
        clMiniPlayer.setOnClickListener{
            current?.let{
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("song", it)

                startActivity(intent)
            }
        }
    }

    // Set onClick and onLongClick for songs in playlist
    private fun setOnClick(adapter: SongListAdapter) {
        adapter.onSongClickListener = { song ->
            current = song
            tvSong.text = "${song.title} - ${song.artist}"
        }

        adapter.onSongLongClickListener = { song, position ->
            handleRemove(position)
            adapter.notifyItemRemoved(position)
            adapter.notifyItemRangeChanged(position, listOfSong.size)
            Toast.makeText(this, "${song.title} was deleted!", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun handleRemove(position: Int) {
        var temp: MutableList<Song> = listOfSong as MutableList<Song>
        temp.removeAt(position)
        listOfSong = temp
    }
}
