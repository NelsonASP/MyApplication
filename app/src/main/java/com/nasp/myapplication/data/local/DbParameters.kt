package com.nasp.myapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DbParameters {

    @Query("SELECT * FROM Favorites")
    fun getAll(): LiveData<List<Items>>

    @Query("SELECT * FROM Favorites WHERE isFavorites == 1")
    fun getAllFavorites(): LiveData<List<Items>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg gif: Items)

    @Update
    fun update(vararg gif: Items)

    @Delete
    fun delete(gif: Items)

    @Query("DELETE FROM Favorites WHERE isFavorites == 1")
    fun deleteAllFavourites()

}