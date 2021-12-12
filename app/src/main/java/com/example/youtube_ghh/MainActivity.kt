package com.example.youtube_ghh

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube_ghh.databinding.ActivityMainBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : MainAdapter
    private lateinit var player: YouTubePlayer

    private val list: Array<Array<String>> = arrayOf(
        arrayOf("Numbers Game", "CiFyTc1SwPw"),
        arrayOf("Calculator", "ZbZFMDk69IA"),
        arrayOf("Guess the Phrase", "DU1qMhyKv8g"),
        arrayOf("Username and Password", "G_XYXuC8b9M"),
        arrayOf("GUI Username and Password", "sqJWyPhZkDw"),
        arrayOf("Country Capitals", "yBkRLhoVTmc"),
        arrayOf("Database Module", "E-Kb6FgMbVw"))
    private var currentVideo = 0
    private var timeStamp = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.youtubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                checkInternet()
                player = youTubePlayer
                player.loadVideo(list[currentVideo][1], timeStamp)
                recyclerViewSetUp()
            }
        })


    }
fun recyclerViewSetUp(){
    binding.RVmain.adapter = MainAdapter(list,player)
    binding.RVmain.layoutManager = LinearLayoutManager(this)
    binding.RVmain.setHasFixedSize(true)

}


    private fun checkInternetConnection(): Boolean{
        val conn = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activityNetwork: NetworkInfo? = conn.activeNetworkInfo
        return activityNetwork?.isConnectedOrConnecting == true
    }

    private fun checkInternet(){
        if(!checkInternetConnection()){
            AlertDialog.Builder(this@MainActivity)
                .setTitle("NO Internet Connection")
                .setPositiveButton("RETRY"){_, _ -> checkInternet()}
                .show()
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.youtubePlayerView.enterFullScreen()
        }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            binding.youtubePlayerView.exitFullScreen()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentVideo", currentVideo)
        outState.putFloat("timeStamp", timeStamp)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        currentVideo = savedInstanceState.getInt("currentVideo", 0)
        timeStamp = savedInstanceState.getFloat("timeStamp", 0f)
    }
}