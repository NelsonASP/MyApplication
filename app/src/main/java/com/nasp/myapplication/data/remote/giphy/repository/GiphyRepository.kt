package com.nasp.myapplication.data.remote.giphy.repository

import com.nasp.myapplication.data.local.DbParameters
import com.nasp.myapplication.data.local.Items
import com.nasp.myapplication.data.remote.giphy.datasource.GiphyRemoteDataSource
import com.nasp.myapplication.data.remote.succeeded
import com.nasp.myapplication.SaveItems.mapToGifItem
import com.nasp.myapplication.data.remote.Result

class GiphyRepository(
    private val dao: DbParameters,
    private val remoteSource: GiphyRemoteDataSource
) {

    val favoriteGifs = dao.getAllFavorites()

    suspend fun search(query: String?): Result<List<Items>> {
        val searchResult = remoteSource.search(query)
        return if (searchResult.succeeded) {
            Result.Success((searchResult as Result.Success).data.mapToGifItem())
        } else Result.Error((searchResult as Result.Error).exception)

    }

    suspend fun getTrendingGifs(): Result<List<Items>> {
        val trending = remoteSource.getTrending()
        return if (trending.succeeded) {
            Result.Success((trending as Result.Success).data.mapToGifItem())
        } else Result.Error((trending as Result.Error).exception)
    }

    fun deleteAllFavourites() {
        dao.deleteAllFavourites()
    }

    fun saveFavourite(items: Items) {
        dao.insertAll(items)
    }
}