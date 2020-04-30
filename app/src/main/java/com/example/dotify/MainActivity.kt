package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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

        // For orientation (mini player song)
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

        // Back button and hides mini player during player activity
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            clMiniPlayer.visibility = View.GONE
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            clMiniPlayer.visibility = View.VISIBLE
        }

        // Back button and hides mini player during player activity for fragments
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                clMiniPlayer.visibility = View.GONE
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

    // Getter method for now playing fragment
    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    // Getter method for song list fragment
    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment

    // Pops top fragment out of stack
    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onSupportNavigateUp()
    }

    // Song List Fragment
    private fun initialSetup() {
        val fragment = getSongListFragment()
        if (fragment == null) {
            val slFragment = SongListFragment.getInstance(SongDataProvider.getAllSongs().toTypedArray())

            supportFragmentManager
                .beginTransaction()
                .add(R.id.flFragment, slFragment, SongListFragment.TAG)
                .commit()

            btnShuffle.setOnClickListener {
                slFragment.shuffleList()
            }
        } else {
            btnShuffle.setOnClickListener {
                fragment.shuffleList()
            }
        }
    }

    // Now Playing Fragment
    private fun npFragmentSetup(song: Song) {
        val fragment = getNowPlayingFragment()
        if(fragment == null) {
            val npFragment = NowPlayingFragment.getInstance(song)

            supportFragmentManager
                .beginTransaction()
                .add(R.id.flFragment, npFragment, NowPlayingFragment.TAG)
                .addToBackStack(NowPlayingFragment.TAG)
                .commit()

            clMiniPlayer.visibility = View.INVISIBLE
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
