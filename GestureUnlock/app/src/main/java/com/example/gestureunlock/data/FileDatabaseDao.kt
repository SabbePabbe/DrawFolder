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

package com.example.gestureunlock.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface FileDatabaseDao {

    @Insert
    suspend fun insert(file: File)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param file new value to write
     */
    @Update
    suspend fun update(file: File)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from file_table WHERE fileId = :key")
    suspend fun get(key: Long): File


    @Query("SELECT * FROM file_table LIMIT 1")
    suspend fun getAnyFile(): File?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM file_table")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM file_table ORDER BY fileId DESC")
    fun getAllFiles(): LiveData<List<File>>

    /**
     * Selects and returns all rows in the table by whether they are shared or not,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM file_table WHERE owner = :owner OR owner = :shared ORDER BY fileId DESC")
    fun getOwnedFiles(owner: String, shared: String): LiveData<List<File>>

    /**
     * Selects and returns the file with given fileId.
     */
    @Query("SELECT * from file_table WHERE fileId = :key")
    fun getFileWithId(key: Long): LiveData<File>


}
