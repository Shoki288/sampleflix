package com.example.core_cache.favorite.di

import android.content.Context
import androidx.room.Room
import com.example.core_cache.favorite.FavoriteListDatabase
import com.example.core_cache.favorite.dao.FavoriteListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteListModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): FavoriteListDatabase =
        Room.databaseBuilder(
            context,
            FavoriteListDatabase::class.java,
            "favoriteList"
        ).build()

    @Singleton
    @Provides
    fun provideDao(db: FavoriteListDatabase): FavoriteListDao = db.favoriteListDao()

}