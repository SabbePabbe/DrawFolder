package com.example.gestureunlock.ui.gesture

import android.gesture.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.gestureunlock.R
import kotlinx.android.synthetic.main.gesture.*
import java.util.*
import kotlin.collections.ArrayList


class GestureFragment : Fragment(), GestureOverlayView.OnGesturePerformedListener {

    private var gLibrary: GestureLibrary? = null
    private var gestureCount = 0;
    private var latestgesture: String = "none"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.gesture, container, false)
        val progressBar: ProgressBar = root.findViewById(R.id.progressBar)

        val btn1 : Button = root.findViewById(R.id.button)
        btn1.setOnClickListener {
            progressBar.progress = 25
        }

        val btn2 : Button = root.findViewById(R.id.button2)
        btn2.setOnClickListener {
            progressBar.progress = 100
        }

        gLibrary = GestureLibraries.fromRawResource(context, R.raw.gesture)

        if (gLibrary?.load()==false){
            //Don't know what to do if not working...
        }
        val gOverlay : GestureOverlayView = root.findViewById(R.id.setGestureOverlay)
        gOverlay.addOnGesturePerformedListener(this)

        return root
    }

    override fun onGesturePerformed(view: GestureOverlayView?, gesture: Gesture?) {

        val predictions : ArrayList<Prediction>? = gLibrary?.recognize(gesture)

        predictions?.let {
            if (it.size > 0 && it[0].score >1.0){
                val action : String = it[0].name

                if(gestureCount == 0){
                    latestgesture = action
                    gestureCount++
                    progressBar.progress = 40
                }else if (gestureCount == 1){
                    if (action == latestgesture){
                        gestureCount++
                        progressBar.progress = 100

                        findNavController().navigate(R.id.action_gestureFragment_to_gestureSuccessFragment)
                    } else {
                        gestureCount = 0
                        progressBar.progress = 0
                        Toast.makeText(context, "The gesture did not match", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}