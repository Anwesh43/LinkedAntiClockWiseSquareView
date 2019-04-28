package com.anwesh.uiprojects.linkedanticlockwisesquareview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.anticlockwiseview.AntiClockWiseSquareView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AntiClockWiseSquareView.create(this)
    }
}
