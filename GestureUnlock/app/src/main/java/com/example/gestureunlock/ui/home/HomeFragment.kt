package com.example.gestureunlock.ui.home

import android.gesture.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gestureunlock.MyFragmentListener
import com.example.gestureunlock.R
import com.example.gestureunlock.data.FileDatabase
import com.example.gestureunlock.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), GestureOverlayView.OnGesturePerformedListener {

    private lateinit var homeViewModel: HomeViewModel
    private var gLibrary: GestureLibrary? = null

    var listener: MyFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = FileDatabase.getInstance(application).fileDatabaseDao
        val viewModelFactory = HomeViewModelFactory(dataSource, application)

        homeViewModel =
                ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.homeViewModel = homeViewModel


        homeViewModel.navigateToFile.observe(viewLifecycleOwner, Observer { file ->
            file?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections
                        .actionHomeFragmentToEditFileFragment(file))
                homeViewModel.doneNavigating()
            }
        })

        val adapter = FileAdapter(FileListener { fileId ->
            homeViewModel.onFileClicked(fileId)
        })

        binding.fileList.adapter = adapter

        homeViewModel.setOwner("shared");
        homeViewModel.getFiles().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


        gLibrary = GestureLibraries.fromRawResource(context, R.raw.gesture)

        if (gLibrary?.load()==false){
            //if the g library doesn't load the app might as well not do anything
        }

        binding.gOverlay.addOnGesturePerformedListener(this)

        return binding.root
    }

    /*doesn't work?*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /* maybe doesn't work */
        val fab = listener?.getFab()
        fab?.setOnClickListener {
            homeViewModel.onCreateFile()
        }
    }

    /*Called when a gesture is drawn on the bottom sheet*/
    override fun onGesturePerformed(overlay: GestureOverlayView?, gesture: Gesture?) {
        val predictions : ArrayList<Prediction>? = gLibrary?.recognize(gesture)

        predictions?.let {
            if (it.size > 0 && it[0].score >1.0){
                val action : String = it[0].name

                Toast.makeText(context, when(action){
                    "yes" -> "Recognized Alice's pattern"
                    "ok" -> "Recognized Bob's pattern"
                    else -> "Did not recognize pattern"
                }, Toast.LENGTH_SHORT).show()
                homeViewModel.setOwner(action)
            } else {
                homeViewModel.setOwner("shared")
            }
        }
    }
}