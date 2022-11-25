package com.example.extension

import android.os.Build
import android.os.Bundle

inline fun <reified T> Bundle?.getNotNullParcelable(key: String): T =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        requireNotNull(this?.getParcelable(key, T::class.java)) { "not found $key" }
    } else {
        requireNotNull(this?.getParcelable(key) as? T) { "not found $key" }
    }