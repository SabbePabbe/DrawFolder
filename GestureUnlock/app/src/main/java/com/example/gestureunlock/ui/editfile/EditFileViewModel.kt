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
import com.example.gestureunlock.data.File
import com.example.gestureunlock.data.FileDatabaseDao
import kotlinx.coroutines.launch

class EditFileViewModel(
        private val fileKey: Long = 0L,
        dataSource: FileDatabaseDao) : ViewModel() {

    private val database = dataSource

    private val file = database.getFileWithId(fileKey)

    fun getFile(): LiveData<File> {
        return file
    }

    fun onEditFile(content: String) {
        viewModelScope.launch {
            val file = database.get(fileKey)
            file.content = content
            database.update(file)
        }
    }
}
