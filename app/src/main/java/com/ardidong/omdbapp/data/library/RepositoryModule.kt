package com.ardidong.omdbapp.data.library

import com.ardidong.omdbapp.data.MediaRepositoryImpl
import com.ardidong.omdbapp.domain.MediaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsMediaRepository(
        mediaRepositoryImpl: MediaRepositoryImpl
    ) : MediaRepository
}