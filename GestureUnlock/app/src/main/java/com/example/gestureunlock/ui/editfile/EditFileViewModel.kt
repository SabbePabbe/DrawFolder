/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.gestureunlock.ui.editfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestureunlock.data.FileDatabaseDao
import kotlinx.coroutines.launch

/**
 * ViewModel for SleepQualityFragment.
 *
 * @param sleepNightKey The key of the current night we are working on.
 */
class EditFileViewModel(
        private val sleepNightKey: Long = 0L,
        dataSource: FileDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its SleepDatabaseDao.
     */
    val database = dataSource

    /**
     * Variable that tells the fragment whether it should navigate to [HomeFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToHome = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [HomeFragment]
     */
    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    /**
     * Call this immediately after navigating to [HomeFragment]
     */
    fun doneNavigating() {
        _navigateToHome.value = null
    }

    /**
     * Sets the sleep quality and updates the database.
     *
     * Then navigates back to the SleepTrackerFragment.
     *
     * TODO must be extensively changed
     */
    fun onSetSleepQuality(quality: Int) {
        viewModelScope.launch {
            // IO is a thread pool for running operations that access the disk, such as
            // our Room database.

            /* TODO: get opened file, edit file, update database
            val file = database.get(fileKey)
            file.content = "new content" //todo find out how to set new text. Maybe save on exiting the frag?
            database.update(file)
            */
            // Setting this state variable to true will alert the observer and trigger navigation.
            _navigateToHome.value = true
        }
    }
}
