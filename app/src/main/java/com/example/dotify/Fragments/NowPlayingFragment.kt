package com.example.dotify.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.example.dotify.DotifyApp
import com.example.dotify.Managers.MusicManager
import com.example.dotify.R

class NowPlayingFragment : Fragment() {

    private lateinit var btnPlay : ImageView
    private lateinit var ivPrevious : ImageView
    private lateinit var ivNext : ImageView
    private lateinit var ivCover : ImageView

    private lateinit var tvPlayCount : TextView
    private lateinit var tvTitle : TextView
    private lateinit var tvArtist : TextView

    private lateinit var musicManager: MusicManager


    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val ARG_NOW_PLAYING = "arg_now_playing"
        private const val COUNT = "count"

        fun getInstance(song: Song): NowPlayingFragment = NowPlayingFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_NOW_PLAYING, song)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        musicManager = (context?.applicationContext as DotifyApp).musicManager
        // Sets number of plays from 1m to 1b
        musicManager.playCount = (1000000..1000000000).random()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View?  {
        return layoutInflater.inflate(R.layout.player_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPlay = view.findViewById(R.id.ivPlay) as ImageView
        ivPrevious = view.findViewById(R.id.ivPrevious) as ImageView
        ivNext = view.findViewById(R.id.ivNext) as ImageView
        ivCover = view.findViewById(R.id.ivSongCover) as ImageView

        tvArtist = view.findViewById(R.id.tvSongArtist) as TextView
        tvTitle = view.findViewById(R.id.tvSongTitle) as TextView
        tvPlayCount = view.findViewById(R.id.tvPlayCount) as TextView

        // Set PlayCount
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                musicManager.playCount = getInt(COUNT)
            }
        }
        tvPlayCount.text = "${musicManager.playCount} plays"

        arguments?.let { args ->
           args.getParcelable<Song>(ARG_NOW_PLAYING)?.let { song ->
               ivCover.setImageResource(song.largeImageID)
               tvTitle.text = song.title
               tvArtist.text = song.artist
            }
        }

        // Set play button onclick
        btnPlay.setOnClickListener {
            musicManager.increaseCount();
            tvPlayCount.text = "${musicManager.playCount} plays"
        }

        // Set previous button onclick
        ivPrevious.setOnClickListener {
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        // Set next button onclick
        ivNext.setOnClickListener {
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        // Changes text color to a random color
        ivCover.setOnLongClickListener {
            musicManager.changeColor()
            tvTitle.setTextColor(musicManager.color)
            tvArtist.setTextColor(musicManager.color)
            tvPlayCount.setTextColor(musicManager.color)

            true
        }

    }

    // public method for setting song for playlist
    fun updateSong(song: Song) {
        ivCover.setImageResource(song.largeImageID)
        tvTitle.text = song.title
        tvArtist.text = song.artist
    }

    // Saves count in state
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(COUNT, musicManager.playCount)
        super.onSaveInstanceState(outState)
    }
}