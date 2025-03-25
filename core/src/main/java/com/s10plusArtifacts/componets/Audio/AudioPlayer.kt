package com.s10plusArtifacts.componets.Audio

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun AudioPlayer(url:String){
    val ctx = LocalContext.current
    val mediaPlayer = MediaPlayer()
    var audioUrl = url
    //var audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

    // on below line we are running a try and catch block
    // for our media player.
    try {
        // on below line we are setting audio source
        // as audio url on below line.
        mediaPlayer.setDataSource(audioUrl)

        // on below line we are preparing
        // our media player.
        mediaPlayer.prepare()

        // on below line we are starting
        // our media player.
        mediaPlayer.start()

    } catch (e: Exception) {

        // on below line we are
        // handling our exception.
        e.printStackTrace()
    }
}