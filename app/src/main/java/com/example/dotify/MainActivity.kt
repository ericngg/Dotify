package com.example.dotify

import android.content.Context
import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*

class MainActivity : AppCompatActivity() {

    lateinit var btnChange : Button

    lateinit var btnPlay : ImageView
    lateinit var ivPrevious : ImageView
    lateinit var ivNext : ImageView
    lateinit var ivCover : ImageView

    lateinit var tvPlayCount : TextView
    lateinit var tvUser : TextView
    lateinit var tvTitle : TextView
    lateinit var tvArtist : TextView
    lateinit var etUser : TextView

    lateinit var vsUser : ViewSwitcher

    private var count : Int = (1000000..1000000000).random()
    private var username : String = "Illenium"
    private var isEdit : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay = findViewById(R.id.ivPlay)
        tvPlayCount = findViewById(R.id.tvPlayCount)
        ivPrevious = findViewById(R.id.ivPrevious)
        ivNext = findViewById(R.id.ivNext)
        btnChange = findViewById(R.id.btnChange)
        tvUser = findViewById(R.id.tvUser)
        etUser = findViewById(R.id.etUser)
        vsUser = findViewById(R.id.vsUser)
        ivCover = findViewById(R.id.ivCover)
        tvArtist = findViewById(R.id.tvArtist)
        tvTitle = findViewById(R.id.tvTitle)


        // Sets number of plays from 1m to 1b
        tvPlayCount.text = "$count plays"

        // Set username, default username is Illenium
        tvUser.text = username

        // Set play button onclick
        btnPlay.setOnClickListener {
            increaseCount();
        }

        // Set previous button onclick
        ivPrevious.setOnClickListener {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        // Set next button onclick
        ivNext.setOnClickListener {
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }


        // Set change user button onclick
        btnChange.setOnClickListener {
            changeUsername()
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

    // Changes the username
    private fun changeUsername() {
        // If change username is pressed
        if(!isEdit) {
            etUser.text = username
            btnChange.text = "Apply"

            vsUser.showNext();
            openKeyboard()

        } else { // In edit state for username
            if(TextUtils.isEmpty(etUser.text.toString().trim())) {
                etUser.error = "The username cannot be empty"
                return
            }

            username = etUser.text.toString()
            tvUser.text = username

            btnChange.text = "Change User"
            closeKeyboard()

            vsUser.showNext();
        }

        isEdit = !isEdit
    }

    // Opens keyboard when changing username
    private fun openKeyboard() {
        etUser.requestFocus()
        var imm : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etUser, InputMethodManager.SHOW_IMPLICIT)

    }

    // Closes keyboard when changing username
    private fun closeKeyboard() {
        var view = this.currentFocus
        if (view != null) {
            var imm : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }
    }

    // Changes color of text
    private fun changeColor() {
        var color : Int = Color.argb(255, (0..255).random(), (0..255).random(), (0..255).random())
        tvUser.setTextColor(color)
        etUser.setTextColor(color)
        tvTitle.setTextColor(color)
        tvArtist.setTextColor(color)
        tvPlayCount.setTextColor(color)
    }


}
