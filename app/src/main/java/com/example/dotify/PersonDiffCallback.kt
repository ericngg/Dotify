package com.example.dotify

import androidx.recyclerview.widget.DiffUtil
import com.ericchee.songdataprovider.Song

class PersonDiffCallback(
    private val oldList: List<Song>,
    private val newList: List<Song>
): DiffUtil.Callback() {

    // Old size
    override fun getOldListSize(): Int = oldList.size

    // New size
    override fun getNewListSize(): Int = newList.size

    // compare if songs are the same
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSong = oldList[oldItemPosition]
        val newSong = newList[newItemPosition]

        return oldSong.id == newSong.id
    }

    // compare if song content are the same
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSong = oldList[oldItemPosition]
        val newSong = oldList[newItemPosition]

        return oldSong.title == newSong.title
    }
}