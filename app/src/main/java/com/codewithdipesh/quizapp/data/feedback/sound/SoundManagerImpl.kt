package com.codewithdipesh.quizapp.data.feedback.sound

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.codewithdipesh.quizapp.R

class SoundManagerImpl(
    private val context: Context
) : SoundManager {

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(3)
        .build()

    private val tap = soundPool.load(context, R.raw.tap_sound, 1)
    private val wrong = soundPool.load(context, R.raw.denied_sound, 1)


    override fun playTap() {
        soundPool.play(tap,1f,1f,1,0,1f)
    }
    override fun playWrong() {
        soundPool.play(wrong,1f,1f,1,0,1f)
    }

}