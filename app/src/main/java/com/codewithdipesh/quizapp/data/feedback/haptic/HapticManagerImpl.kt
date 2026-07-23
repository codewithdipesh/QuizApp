package com.codewithdipesh.quizapp.data.feedback.haptic

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresPermission

class HapticManagerImpl(
    private val context : Context
) : HapticManager {

    private val vibrator = context.getSystemService(Vibrator::class.java)

    @RequiresPermission(Manifest.permission.VIBRATE)
    override fun wrongHaptic() {
        vibrator ?: return

        vibrator.vibrate(
            VibrationEffect.createOneShot(120, VibrationEffect.DEFAULT_AMPLITUDE)
        )
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    override fun correctHaptic() {
        vibrator ?: return

        vibrator.vibrate(
            VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE)
        )
    }
}