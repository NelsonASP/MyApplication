package com.nasp.myapplication.data.remote.giphy.datasource

import com.nasp.myapplication.base.BaseDataSource
import com.nasp.myapplication.data.remote.giphy.ConfigURL

class GiphyRemoteDataSource(private val configURL: ConfigURL) : BaseDataSource() {
    suspend fun getTrending() = getResult { configURL.getTrending() }

    suspend fun search(query: String?) = getResult {
        configURL.search(query = query)
    }
}