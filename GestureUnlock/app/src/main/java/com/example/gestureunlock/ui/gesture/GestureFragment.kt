package com.example.gestureunlock.ui.gesture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        gestureViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }
}