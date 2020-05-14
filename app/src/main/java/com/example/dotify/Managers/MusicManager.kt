package com.example.dotify.Managers

import android.content.Context
import android.graphics.Color
import android.widget.Toast

class MusicManager {
    var playCount : Int = 0
    var color : Int = 0


    // Increase the count play by 1 for each click
    fun increaseCount() {
        playCount = playCount.inc()
    }

    // Changes color of text
    fun changeColor() {
        color = Color.argb(255, (0..255).random(), (0..255).random(), (0..255).random())
    }
}