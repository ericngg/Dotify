package com.example.dotify.Managers

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.dotify.Profile
import com.google.gson.Gson

class ApiManager(context: Context) {

    private val queue: RequestQueue = Volley.newRequestQueue(context)


    companion object {
        const val TAG: String = "http_request"
    }

    fun fetchData(onDataReady: (Profile) -> Unit) {
        val url = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/user_info.json"

        val request = StringRequest(Request.Method.GET, url, { response ->
            // Success
            val gson = Gson()
            val data = gson.fromJson(response, Profile::class.java)

            onDataReady(data)
        }, { error ->
            // error
            Log.e(TAG, "Error occurred: $error")
        })

        queue.add(request)
    }



}