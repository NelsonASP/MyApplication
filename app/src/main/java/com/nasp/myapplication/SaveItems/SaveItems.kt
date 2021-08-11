package com.nasp.myapplication.SaveItems

import com.nasp.myapplication.data.local.Items
import com.nasp.myapplication.data.remote.modelData.GifResult

fun GifResult.mapToGifItem(): List<Items> {
    return this.data.map {
        Items(
            id = it.id,
            title = it.title,
            width = it.images.fixedHeight.width,
            height = it.images.fixedHeight.height,
            gifUrl = it.images.fixedHeight.gifUrl,
            originalGifUrl = it.images.original.gifUrl,
            webpUrl = it.images.fixedHeight.webpUrl
        )
    }
}
