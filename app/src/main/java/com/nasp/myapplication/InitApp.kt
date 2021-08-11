package com.nasp.myapplication

import android.app.Application

import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.nasp.myapplication.ModuleInitialization.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InitApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    giphyModule,
                    mainModule,
                    mainModule,
                    networkModule,
                    dbModule,
                    homeModule,
                    favoriteM
                )
            )
        }
        initializeFresco()
    }

    private fun initializeFresco() {
        val previewsDiskConfig = DiskCacheConfig.newBuilder(this)
            .setMaxCacheSize(250L * ByteConstants.MB).build()
        val qualityDiskConfig = DiskCacheConfig.newBuilder(this)
            .setMaxCacheSize(250L * ByteConstants.MB).build()
        val config = ImagePipelineConfig.newBuilder(this)
            .setSmallImageDiskCacheConfig(previewsDiskConfig)
            .setMainDiskCacheConfig(qualityDiskConfig)
            .build()

        Fresco.initialize(this, config)
    }
}