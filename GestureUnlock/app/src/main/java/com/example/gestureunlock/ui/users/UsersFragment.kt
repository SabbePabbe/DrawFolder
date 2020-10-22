package com.example.gestureunlock.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.gestureunlock.R

class UsersFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_users, container, false)
        val imageView: ImageView = root.findViewById(R.id.imageView2)
        imageView.setOnClickListener {
            findNavController().navigate(R.id.action_nav_gallery_to_gestureFragment)
        }
        return root
    }

}
