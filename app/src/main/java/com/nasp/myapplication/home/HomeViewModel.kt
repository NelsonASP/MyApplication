package com.nasp.myapplication.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasp.myapplication.data.local.Items
import com.nasp.myapplication.data.remote.giphy.repository.GiphyRepository
import com.nasp.myapplication.data.remote.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val giphyRepository: GiphyRepository
) : ViewModel() {

    private val _gifs = MutableLiveData<Result<List<Items>>>()
    val gifs: LiveData<Result<List<Items>>>
        get() = _gifs

    val favouriteGifs = giphyRepository.favoriteGifs

    init {
        getTrending()
    }

    private fun getTrending() {
        viewModelScope.launch {
            val trendingGifs = withContext(Dispatchers.IO) { giphyRepository.getTrendingGifs() }
            withContext(Dispatchers.Main) {
                _gifs.postValue(trendingGifs)
            }
        }
    }

    fun searchGif(query: String?) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                if (!query.isNullOrEmpty()) giphyRepository.search(query) else giphyRepository.getTrendingGifs()
            }
            withContext(Dispatchers.Main) {
                _gifs.postValue(result)
            }
        }
    }

    fun saveFavorites(items: Items) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                giphyRepository.saveFavourite(items)
            }
        }
    }

}