package com.nasp.myapplication.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Favorites")
data class Items(
    @PrimaryKey val id: String,
    val title: String,
    val gifUrl: String,
    val originalGifUrl: String,
    val webpUrl: String,
    val width: Int,
    val height: Int,
    var isFavorites: Boolean = false
) : Parcelable