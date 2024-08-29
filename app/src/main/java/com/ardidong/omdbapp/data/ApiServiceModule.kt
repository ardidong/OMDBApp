package com.ardidong.omdbapp.data

import com.ardidong.omdbapp.data.library.network.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ApiServiceModule {
    @Provides
    fun bindsMediaApiService(
        networkClient: NetworkClient
    ): MediaApiService {
        return networkClient.create(MediaApiService::class.java)
    }
}