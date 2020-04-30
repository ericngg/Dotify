package com.example.dotify

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song

class NowPlayingFragment : Fragment() {

    private lateinit var btnPlay : ImageView
    private lateinit var ivPrevious : ImageView
    private lateinit var ivNext : ImageView
    private lateinit var ivCover : ImageView

    private lateinit var tvPlayCount : TextView
    private lateinit var tvTitle : TextView
    private lateinit var tvArtist : TextView

    private var count : Int = 0

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val ARG_NOW_PLAYING = "arg_now_playing"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sets number of plays from 1m to 1b
        count = (1000000..1000000000).random()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View?  {

        return layoutInflater.inflate(R.layout.player_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPlay = view?.findViewById(R.id.ivPlay) as ImageView
        ivPrevious = view?.findViewById(R.id.ivPrevious) as ImageView
        ivNext = view?.findViewById(R.id.ivNext) as ImageView
        ivCover = view?.findViewById(R.id.ivSongCover) as ImageView


        tvArtist = view?.findViewById(R.id.tvSongArtist) as TextView
        tvTitle = view?.findViewById(R.id.tvSongTitle) as TextView
        tvPlayCount = view?.findViewById(R.id.tvPlayCount) as TextView

        // Set PlayCount
        tvPlayCount.text = "$count plays"

        arguments?.let { args ->
           args.getParcelable<Song>(ARG_NOW_PLAYING)?.let { song ->
               ivCover.setImageResource(song.largeImageID)
               tvTitle.text = song.title
               tvArtist.text = song.artist
            }
        }

        // Set play button onclick
        btnPlay.setOnClickListener {
            increaseCount();
        }

        // Set previous button onclick
        ivPrevious.setOnClickListener {
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        // Set next button onclick
        ivNext.setOnClickListener {
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        ivCover.setOnLongClickListener {
            changeColor()
            true
        }

    }

    // Increase the count play by 1 for each click
    private fun increaseCount() {
        count = count.inc()
        tvPlayCount.text = "$count plays"
    }

    // Changes color of text
    private fun changeColor() {
        var color : Int = Color.argb(255, (0..255).random(), (0..255).random(), (0..255).random())
        tvTitle.setTextColor(color)
        tvArtist.setTextColor(color)
        tvPlayCount.setTextColor(color)
    }

    fun updateSong(song: Song) {
        ivCover.setImageResource(song.largeImageID)
        tvTitle.text = song.title
        tvArtist.text = song.artist
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("test", "saved")
        current?.let { song ->
            Log.i("test", "hello")
            outState.putParcelable(MainActivity.SONG, song)
        }

        super.onSaveInstanceState(outState)
    }
}