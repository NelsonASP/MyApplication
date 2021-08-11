package com.nasp.myapplication.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasp.myapplication.data.local.Items
import com.nasp.myapplication.data.remote.giphy.repository.GiphyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    private val giphyRepository: GiphyRepository
) : ViewModel() {

    val favouriteGifs = giphyRepository.favoriteGifs

    fun saveFavorite(items: Items) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                giphyRepository.saveFavourite(items)
            }
        }
    }

    fun deleteAllFavorites() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                giphyRepository.deleteAllFavourites()
            }
        }
    }
}