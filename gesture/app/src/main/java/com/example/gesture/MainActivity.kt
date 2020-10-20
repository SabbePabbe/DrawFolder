package com.example.gesture

import android.gesture.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity(), GestureOverlayView.OnGesturePerformedListener {
    private var gLibrary: GestureLibrary? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        gestureSetup()
    }

    private fun gestureSetup() {
        gLibrary = GestureLibraries.fromRawResource(this, R.raw.gesture)

        if (gLibrary?.load()==false){
            finish()
        }

        findViewById<GestureOverlayView>(R.id.gOverlay).addOnGesturePerformedListener(this)
    }

    override fun onGesturePerformed(overlay: GestureOverlayView?, gesture: Gesture?) {
        val predictions : ArrayList<Prediction>? = gLibrary?.recognize(gesture)

        predictions?.let {
            if (it.size > 0 && it[0].score >1.0){
                val action : String = it[0].name
                Toast.makeText(this, action, Toast.LENGTH_SHORT).show()
            }
        }
    }
}