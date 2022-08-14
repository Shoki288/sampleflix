package com.example.core_cache

import androidx.room.RoomDatabase
import androidx.room.withTransaction

internal interface DBTransaction {
    val database: RoomDatabase
}

suspend fun <R, T: DBTransaction> T.withTransaction(block: suspend T.() -> R): R {
    return database.withTransaction {
        block(this)
    }
}