package com.projectbyzakaria.views.ui.activitys

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener
import com.google.android.youtube.player.YouTubePlayerView
import com.projectbyzakaria.views.R
import com.projectbyzakaria.views.utlis.Constent.API_KEY
import com.projectbyzakaria.views.utlis.Constent.KEY_ID_VIDEO

class TrailerActivity : YouTubeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trialer_activity)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        val idYoutube = intent.getStringExtra(KEY_ID_VIDEO)
        val youtube = object  : OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(idYoutube)
            }
            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@TrailerActivity, "Error", Toast.LENGTH_SHORT).show()
            }

        }
        findViewById<YouTubePlayerView>(R.id.youtubeId).initialize(API_KEY,youtube)
    }
}