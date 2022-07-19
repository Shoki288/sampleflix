package com.example.core_cache.di

import android.content.Context
import androidx.room.Room
import com.example.core_cache.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookIInfoModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "book_info"
        ).build()

    @Singleton
    @Provides
    fun provideDao(db: AppDatabase) = db.bookInfoDao()

}