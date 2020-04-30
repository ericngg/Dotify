package com.example.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class MainActivity : AppCompatActivity(), OnSongClickListener {


    private lateinit var tvSong : TextView

    private lateinit var btnShuffle : Button

    private lateinit var clMiniPlayer : ConstraintLayout

    private var current : Song? = null

    companion object {
        private const val SONG = "song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSong = findViewById(R.id.tvSong)
        btnShuffle = findViewById(R.id.btnShuffle)
        clMiniPlayer = findViewById(R.id.clMiniPlayer)

        // Start up set-up
        initialSetup()
        
        if (savedInstanceState != null) {

            with(savedInstanceState) {
                current = getParcelable(SONG)

                current?.let { song ->
                    tvSong.text = "${song.title} - ${song.artist}"
                }
            }
        }

        // Opens Song screen
        clMiniPlayer.setOnClickListener{
            current?.let{ song ->
                npFragmentSetup(song)
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                clMiniPlayer.visibility = View.INVISIBLE
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                clMiniPlayer.visibility = View.VISIBLE
            }
        }
    }

    // Updates mini player
    override fun onSongClicked(song: Song) {
        current = song
        tvSong.text = "${song.title} - ${song.artist}"
    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onSupportNavigateUp()
    }

    // Song List Fragment
    private fun initialSetup() {
        val slFragment = SongListFragment()
        val slArgumentBundle = Bundle().apply {
            putParcelableArray(SongListFragment.ARG_SONG_LIST, SongDataProvider.getAllSongs().toTypedArray())
        }
        slFragment.arguments = slArgumentBundle

        supportFragmentManager
            .beginTransaction()
            .add(R.id.flFragment, slFragment)
            .commit()

        btnShuffle.setOnClickListener {
            slFragment.shuffleList()
        }
    }

    // Now Playing Fragment
    private fun npFragmentSetup(song: Song) {
        val fragment = getNowPlayingFragment()
        if(fragment == null) {
            val npFragment = NowPlayingFragment()
            val npArgumentBundle = Bundle().apply {
                putParcelable(NowPlayingFragment.ARG_NOW_PLAYING, song)
            }

            npFragment.arguments = npArgumentBundle

            supportFragmentManager
                .beginTransaction()
                .add(R.id.flFragment, npFragment, NowPlayingFragment.TAG)
                .addToBackStack(NowPlayingFragment.TAG)
                .commit()
        } else {
            fragment.updateSong(song)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        current?.let { song ->
            outState.putParcelable(SONG, song)
        }

        super.onSaveInstanceState(outState)
    }
}
