package com.example.gestureunlock.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.gestureunlock.data.File
import com.example.gestureunlock.data.FileDatabaseDao
import com.example.gestureunlock.formatFiles
import kotlinx.coroutines.launch


class HomeViewModel(
    dataSource: FileDatabaseDao,
    application: Application
) : ViewModel() {

    val database = dataSource

    private val owner = MutableLiveData<String>()

    // Instance variable that stores the current list of files. This will automatically change when owner value changes.
    private val files: LiveData<List<File>> = Transformations.switchMap<String, List<File>>(
        owner
    ) { owner: String? ->
        owner?.let {
            database.getOwnedFiles(
                it, "shared"
            )
        }
    }

    // Set new dictionaryId
    fun setOwner(newOwner: String) {
        owner.postValue(newOwner)
    }

    fun getFiles(): LiveData<List<File>> {
        return files
    }

    //var viewableFiles = database.getOwnedFiles("shared", "shared")

    //val allFiles = database.getAllFiles()

    //val filesString = Transformations.map(allFiles) { files ->
    //    formatFiles(files, application.resources)
    //}

    init {
        setUpDatabase()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    private val _navigateToFile = MutableLiveData<Long>()
    val navigateToFile: LiveData<Long>
        get() = _navigateToFile


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

//    fun accessFiles(owner: String){
//        viewableFiles = database.getOwnedFiles(owner, "shared")
//
//    }

    /* Use this method to set up fake files in the database.
    * TODO: set names and accesses to them */
    private fun setUpDatabase(){
        viewModelScope.launch {
            clear()
            insert(File())
            insert(File())
            insert(File())
            insert(File())
            insert(File())
            insert(File())
            insert(File())
            insert(File())
            insert(File())
            val f : File = File()
            f.fileName = "hidden"
            f.owner = "yes"
            insert(f)
            insert(File())

        }
    }

    fun onFileClicked(id: Long){
        _navigateToFile.value = id
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