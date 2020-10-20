package com.example.gestureunlock.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.gestureunlock.data.File
import com.example.gestureunlock.data.FileDatabaseDao
import com.example.gestureunlock.formatFiles
import kotlinx.coroutines.launch
import javax.sql.CommonDataSource

class HomeViewModel(
        dataSource:FileDatabaseDao,
        application: Application) : ViewModel() {

    val database = dataSource

    val sharedFiles = database.getSharedFiles(true)
    val allFiles = database.getAllFiles()

    val filesString = Transformations.map(allFiles) { files ->
        formatFiles(files, application.resources)
    }

    init {
        setUpDatabase()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    /**
     * Variable that tells the Fragment to navigate to a specific [FileEditFragment]
     */
    private val _navigateToFile = MutableLiveData<File>()

    /**
     * If this is non-null, immediately navigate to [FileEditFragment] and call [doneNavigating]
     */
    val navigateToFile: LiveData<File>
        get() = _navigateToFile


    /**
     * Call this immediately after navigating to [FileEditFragment]
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


    /* Use this method to set up fake files in the database.
    * TODO: set names and accesses to them */
    private fun setUpDatabase(){
        viewModelScope.launch {
            clear()
            insert(File())
            insert(File())
            insert(File())
        }
    }



    fun onCreateFile(){
        viewModelScope.launch {
            val newFile = File()
            insert(newFile)
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