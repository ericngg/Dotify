package com.example.dotify

import android.app.Application
import com.example.dotify.Managers.ApiManager
import com.example.dotify.Managers.MusicManager
import com.example.dotify.Managers.ProfileManager

class DotifyApp: Application() {

    lateinit var musicManager: MusicManager
    lateinit var apiManager: ApiManager
    lateinit var profileManager: ProfileManager

    override fun onCreate() {
        super.onCreate()

        musicManager = MusicManager()
        apiManager = ApiManager(this)
        profileManager = ProfileManager()
    }
}