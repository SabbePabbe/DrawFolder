package com.example.gestureunlock.ui.home

import android.gesture.*
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.snackbar.Snackbar


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

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = FileDatabase.getInstance(application).fileDatabaseDao
        val viewModelFactory = HomeViewModelFactory(dataSource, application)

        homeViewModel =
                ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.homeViewModel = homeViewModel



        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this

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
            //Toast.makeText(context, "${fileId}", Toast.LENGTH_LONG).show()
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
            //Don't know what to do if not working...
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

    override fun onGesturePerformed(overlay: GestureOverlayView?, gesture: Gesture?) {
        val predictions : ArrayList<Prediction>? = gLibrary?.recognize(gesture)

        predictions?.let {
            if (it.size > 0 && it[0].score >1.0){
                val action : String = it[0].name
                Toast.makeText(context, "Recognized $action action", Toast.LENGTH_SHORT).show()

                homeViewModel.setOwner(action)
            }
        }
    }

}