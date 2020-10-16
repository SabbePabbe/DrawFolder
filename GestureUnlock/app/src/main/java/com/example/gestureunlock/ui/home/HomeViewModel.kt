package com.example.gestureunlock.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestureunlock.data.File
import com.example.gestureunlock.data.FileDatabaseDao
import kotlinx.coroutines.launch
import javax.sql.CommonDataSource

class HomeViewModel(
        dataSource:FileDatabaseDao,
        application: Application) : ViewModel() {

    /**
     * Hold a reference to FileDatabase via FileDatabaseDao.
     */
    val database = dataSource

    val sharedFiles = database.getSharedFiles(true)
    val allFiles = database.getAllFiles()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text




    /**
     * Variable that tells the Fragment to navigate to a specific [FileEditFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToFile = MutableLiveData<File>()

    /**
     * If this is non-null, immediately navigate to [FileEditFragment] and call [doneNavigating]
     */
    val navigateToFile: LiveData<File>
        get() = _navigateToFile


    /**
     * Call this immediately after navigating to [FileEditFragment]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate
     * twice.
     */
    fun doneNavigating() {
        _navigateToFile.value = null
    }


    private suspend fun insert(file: File) {
        database.insert(file)
    }

    private suspend fun update(file: File) {
        database.update(file)
    }

    private suspend fun clear() {
        database.clear()
    }


    fun onCreateFile(){
        viewModelScope.launch {
            val newFile = File()
            /* TODO: clicking the create button should navigate to new fragment, "createFile" or something
                and that fragment should act like the sleep quality fragment, but also create the file
                the images in that fragment have onclick listeners that run the OnSetSleepQuality fun in
                the sleepQuality viewmodel.
                We should have one fun that runs when users clicks Create, after having set the name and
                openness of the file. these values should be bound to the viewmodel i guess?
             */


        }
    }

}