package com.example.dotify.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import com.example.dotify.OnSongClickListener
import com.example.dotify.PersonDiffCallback
import com.example.dotify.R
import com.example.dotify.SongListAdapter


class SongListFragment : Fragment() {

    private lateinit var rvSongList: RecyclerView

    private lateinit var listOfSong: MutableList<Song>

    private var onSongClickListener: OnSongClickListener? = null

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
        const val ARG_SONG_LIST = "arg_song_list"

        fun getInstance(songList: Array<Song>): SongListFragment = SongListFragment().apply {
            arguments = Bundle().apply {
                putParcelableArray(ARG_SONG_LIST, songList)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View?  {

        return layoutInflater.inflate(R.layout.activity_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { args ->
            args.getParcelableArray(ARG_SONG_LIST)?.let { list ->
                listOfSong = mutableListOf()

                for (song in list) {
                    listOfSong.add(song as Song)
                }
            }
        }

        rvSongList = view?.findViewById(R.id.rvSongList) as RecyclerView

        // Initialize RecyclerView
        var songListAdapter = SongListAdapter(listOfSong)

        // Initialize onClick and onLongClick on start up
        setOnClick(songListAdapter)
        rvSongList.adapter = songListAdapter

        // Shuffles the playlist and recreates the recycler view
    }

    // Set onClick and onLongClick for songs in playlist
    private fun setOnClick(adapter: SongListAdapter) {
        // onClick
        adapter.onSongClickListener = { song ->
            onSongClickListener?.onSongClicked(song)
        }

        // onLongClick
        adapter.onSongLongClickListener = { song, position ->
            handleRemove(position)
            adapter.notifyItemRemoved(position)
            adapter.notifyItemRangeChanged(position, listOfSong.size)
            Toast.makeText(context, "${song.title} was deleted!", Toast.LENGTH_SHORT).show()
            true
        }
    }

    // Helper method for remove function
    private fun handleRemove(position: Int) {
        listOfSong.removeAt(position)
    }

    fun shuffleList() {
        // Shuffles playlist
        val newList = listOfSong.shuffled()

        // Handles difference
        val callback = PersonDiffCallback(listOfSong, newList)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(rvSongList.adapter as SongListAdapter)
    }
}
