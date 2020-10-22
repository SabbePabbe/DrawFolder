package com.example.gestureunlock.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.gestureunlock.data.File
import com.example.gestureunlock.data.FileDatabaseDao
import kotlinx.coroutines.launch


class HomeViewModel(
    dataSource: FileDatabaseDao,
    application: Application
) : ViewModel() {

    val database = dataSource

    private val allFiles = database.getAllFiles()

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

    private suspend fun getAny(): File? {
        return database.getAnyFile()
    }

    /* Use this method to set up fake files in the database.  */
    private fun setUpDatabase(){
        viewModelScope.launch {
            val file1 : File? = getAny()
            if(file1 == null){
                clear()
                for (i in 1..15){
                    val f : File = File()
                    f.fileName = "Example file $i"
                    f.owner = when (i){
                        12, 15 -> "yes"
                        14, 10 -> "ok"
                        else -> "shared"
                    }
                    f.createdTimeMilli = 1600349125373 + 200000000*i
                    f.content = "This is an example file, number $i"
                    insert(f)
                }
            }
        }
    }

    fun onFileClicked(id: Long){
        _navigateToFile.value = id
    }

    fun onCreateFile(){
        viewModelScope.launch {
            val newFile = File()
            insert(newFile)
        }
    }

}