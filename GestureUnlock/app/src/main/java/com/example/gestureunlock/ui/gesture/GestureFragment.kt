package com.example.gestureunlock.ui.gesture

import android.gesture.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gestureunlock.R
import kotlinx.android.synthetic.main.gesture.*
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

        gLibrary = GestureLibraries.fromRawResource(context, R.raw.gesture)
        if (gLibrary?.load()==false){
            //If the gLibrary doesn't load the app doesn't work
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