package com.example.dotify.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.dotify.DotifyApp
import com.example.dotify.Managers.ApiManager
import com.example.dotify.Managers.ProfileManager
import com.example.dotify.R
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var profileManager : ProfileManager

    private lateinit var tvUsername : TextView
    private lateinit var tvFirstName : TextView
    private lateinit var tvLastName : TextView
    private lateinit var tvNose : TextView
    private lateinit var tvPlatform : TextView
    private lateinit var ivProfilePicture : ImageView


    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName
        const val ARG_PROFILE = "arg_profile"

        fun getInstance(): ProfileFragment = ProfileFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        profileManager = (context?.applicationContext as DotifyApp).profileManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View?  {

        return layoutInflater.inflate(R.layout.activity_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvUsername = view.findViewById(R.id.tvUsername)
        tvFirstName = view.findViewById(R.id.tvFirstName)
        tvLastName = view.findViewById(R.id.tvLastName)
        tvNose = view.findViewById(R.id.tvNose)
        tvPlatform = view.findViewById(R.id.tvPlatform)
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture)

        // Populate text with data from HTTP call
        initialize()
    }

    private fun initialize() {
        tvUsername.text = profileManager.profile.username
        tvFirstName.text = profileManager.profile.firstName
        tvLastName.text = profileManager.profile.lastName
        tvNose.text = profileManager.profile.hasNose.toString()
        tvPlatform.text = profileManager.profile.platform.toString()
        Picasso.get().load(profileManager.profile.profilePicURL).into(ivProfilePicture)
    }

}