package com.example.dotify.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.DotifyApp
import com.example.dotify.Fragments.NowPlayingFragment
import com.example.dotify.Fragments.ProfileFragment
import com.example.dotify.Fragments.SongListFragment
import com.example.dotify.OnSongClickListener
import com.example.dotify.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(),
    OnSongClickListener {


    private lateinit var tvSong : TextView

    private lateinit var btnShuffle : Button

    private lateinit var clMiniPlayer : ConstraintLayout

    private lateinit var bnNavigation : BottomNavigationView

    private var current : Song? = null

    private lateinit var app : DotifyApp

    companion object {
        private const val SONG = "song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSong = findViewById(R.id.tvSong)
        btnShuffle = findViewById(R.id.btnShuffle)
        clMiniPlayer = findViewById(R.id.clMiniPlayer)
        bnNavigation = findViewById(R.id.bnNavigation)

        app = application as DotifyApp

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
            bnNavigation.visibility = View.GONE
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            clMiniPlayer.visibility = View.VISIBLE
            bnNavigation.visibility = View.VISIBLE
        }

        // Back button and hides mini player during player activity for fragments
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0 && getNowPlayingFragment() != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                clMiniPlayer.visibility = View.GONE
                bnNavigation.visibility = View.GONE
            } else if (supportFragmentManager.backStackEntryCount <= 0 && getNowPlayingFragment() == null){
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                clMiniPlayer.visibility = View.VISIBLE
                bnNavigation.visibility = View.VISIBLE
            }
        }

        bnNavigation.setOnNavigationItemSelectedListener{ item ->
            profileFragmentSetup(item)
            true
        }

        app.apiManager.fetchData { profile ->
            app.profileManager.profile = profile
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

    private fun getProfileFragment() = supportFragmentManager.findFragmentByTag(ProfileFragment.TAG) as? ProfileFragment

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

    // Save song that is selected in mini player
    override fun onSaveInstanceState(outState: Bundle) {
        current?.let { song ->
            outState.putParcelable(SONG, song)
        }

        super.onSaveInstanceState(outState)
    }

    private fun profileFragmentSetup(menuItem : MenuItem) {
        val item = menuItem.toString()
        val fragment = getProfileFragment()

        if (item == "Profile" && fragment == null) { // Start Profile fragment
            val pFragment = ProfileFragment.getInstance()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.flFragment, pFragment, ProfileFragment.TAG)
                .addToBackStack(ProfileFragment.TAG)
                .commit()

            clMiniPlayer.visibility = View.GONE
        } else if (item == "Song List" && fragment != null) { // Return to Song List fragment from Profile fragment
            supportFragmentManager.popBackStack()
            clMiniPlayer.visibility = View.VISIBLE
        }

        // If Profile button is clicked and is already on profile (already exist), it will do nothing (item == "Profile" && fragment != null)

        // If Song List button is clicked and is already on song list (always exist), it will do nothing (item == "Song List" && fragment == null)
    }
}
