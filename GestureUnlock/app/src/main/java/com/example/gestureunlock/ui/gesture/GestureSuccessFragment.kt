package com.example.gestureunlock.ui.gesture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gestureunlock.R

class GestureSuccessFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.gesture_success, container, false)

        val img = root.findViewById<ImageView>(R.id.success_image)
        img.setOnClickListener{
            findNavController().navigate(R.id.action_gestureSuccessFragment_to_home_fragment)
        }

        return root
    }
}