package com.berkeerkec.myartgallery.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(art : Art)

    @Delete
    suspend fun delete(art : Art)

    @Query("SELECT * FROM arts")
    fun obserArts() : LiveData<List<Art>>
}