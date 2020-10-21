package com.example.gestureunlock.ui.gesture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gestureunlock.R
//import com.example.gestureunlock.ui.gesture.GestureViewModel

class GestureFragment : Fragment() {

//    private lateinit var gestureViewModel: GestureViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        galleryViewModel =
//            ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.gesture, container, false)
        val progressBar: ProgressBar = root.findViewById(R.id.progressBar)

        val btn1 : Button = root.findViewById(R.id.button)
        btn1.setOnClickListener {
            progressBar.setProgress(25)
        }

        val btn2 : Button = root.findViewById(R.id.button2)
        btn2.setOnClickListener {
            progressBar.setProgress(100)
        }



//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        gestureViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }
}