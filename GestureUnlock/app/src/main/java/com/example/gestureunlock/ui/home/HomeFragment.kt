package com.example.gestureunlock.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gestureunlock.R
import com.example.gestureunlock.data.FileDatabase
import com.example.gestureunlock.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container,false
        )

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = FileDatabase.getInstance(application).fileDatabaseDao
        val viewModelFactory = HomeViewModelFactory(dataSource, application)

        homeViewModel =
                ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.homeViewModel = homeViewModel

        //registers listener to clicking the file in the view
//        val adapter = FileAdapter(FileListener { nightId ->
//            //Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
//            homeViewModel.onFileClicked(nightId)
//        })
//        binding.sleepList.adapter = adapter

        homeViewModel.allFiles.observe(viewLifecycleOwner, Observer {
            it?.let {
                //adapter.addHeaderAndSubmitList(it)
            }
        })

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.setLifecycleOwner(this)

        homeViewModel.navigateToFile.observe( viewLifecycleOwner, Observer { file ->
            file?.let{
                //this.findNavController().navigate()
                // todo navigate to editfilefragment then call donenavigating:
                /*
                this.findNavController().navigate(
                        SleepTrackerFragmentDirections
                                .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                sleepTrackerViewModel.doneNavigating()
                 */
            }
        })


//        val textView: TextView = binding.R.id.text_home
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })
        return binding.root
    }
}