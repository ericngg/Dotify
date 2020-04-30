package com.example.dotify

import com.ericchee.songdataprovider.Song

interface OnSongClickListener {
    fun onSongClicked(song: Song)
}