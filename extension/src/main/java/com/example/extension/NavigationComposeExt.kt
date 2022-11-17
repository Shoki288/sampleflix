package com.example.extension

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

inline fun <reified T : Parcelable> createCustomNavType(): NavType<T> {
    return object : NavType<T>(isNullableAllowed = true) {
        override fun get(bundle: Bundle, key: String) =
            bundle.getNotNullParcelable<T>(key)

        override fun parseValue(value: String): T {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val adapter = moshi.adapter(T::class.java)
            return adapter.fromJson(value)!!
        }

        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putParcelable(key, value)
        }
    }
}

inline fun <reified T : Parcelable> convertArgumentToJson(arg: T): String =
    Moshi.Builder().add(KotlinJsonAdapterFactory()).build().let { moshi ->
        Uri.encode(moshi.adapter(T::class.java).toJson(arg))
    }