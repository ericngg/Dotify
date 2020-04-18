package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.SongDataProvider

class SongListActivity : AppCompatActivity() {

    private lateinit var rvSongList : RecyclerView

    private lateinit var tvSong : TextView

    private lateinit var btnShuffle : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        rvSongList = findViewById(R.id.rvSongList)
        tvSong = findViewById(R.id.tvSong)
        btnShuffle = findViewById(R.id.btnShuffle)

        val listOfSong = SongDataProvider.getAllSongs()

        val songListAdapter = SongListAdapter(listOfSong)

        songListAdapter.onSongClickListener = { song ->
            tvSong.text = "${song.title} - ${song.artist}"
        }

        rvSongList.adapter = songListAdapter
    }
}
