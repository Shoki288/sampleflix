package com.example.core_cache.cache_home.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.core_cache.cache_home.HomeRecommendDatabase
import com.example.core_cache.cache_home.dao.HomeRecommendDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeRecommendModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): HomeRecommendDatabase =
        Room.databaseBuilder(
            context,
            HomeRecommendDatabase::class.java,
            "bookRecommend"
        ).addMigrations(MIGRATION_1_2)
            .build()

    @Singleton
    @Provides
    fun provideDao(db: HomeRecommendDatabase): HomeRecommendDao = db.homeRecommendDao()


    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE CacheBookInfo ADD isFavorite INTEGER NOT NULL DEFAULT(0)")
        }
    }
}