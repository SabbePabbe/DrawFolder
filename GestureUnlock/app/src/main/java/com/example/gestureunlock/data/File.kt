package com.example.gestureunlock.data

import androidx.room.*

@Entity(tableName = "file_table")
data class File(

    @PrimaryKey(autoGenerate = true)
    var fileId: Long = 0L,

    @ColumnInfo(name = "created_time_milli")
    var createdTimeMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "file_name")
    var fileName: String = "New file",

    @ColumnInfo(name= "owner")
    var owner: String = "shared",

    @ColumnInfo(name ="content")
    var content: String = ""


)

/*  Below is a more complicated, but more real, version of the database, wait before including it */
//@Entity
//data class User(
//    @PrimaryKey (autoGenerate = true)
//    var userId: Long = 0L,
//
//    @ColumnInfo(name = "user_name")
//    var userName: String = "name"
//
//)
//
//
///*
//*  This cross ref is what decides whether a user can see a file or not. If it exists, the user
//*  can see the file, so always create a row here when creating a file
//*
//*  On the other hand I don't know what to do about shared files...
//*
//*/
//@Entity(primaryKeys = ["fileId", "userId"])
//data class FileUserCrossRef(
//    val fileId: Long,
//    val userId: Long
//)
//
//data class UserWithFiles(
//    @Embedded val user: User,
//    @Relation(
//        parentColumn = "userId",
//        entityColumn = "fileId",
//        associateBy = Junction(FileUserCrossRef::class)
//    )
//    val files: List<File>
//)
