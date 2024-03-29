package com.example.core_unit_test

import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import io.mockk.spyk
import kotlinx.coroutines.flow.Flow

fun <T> Flow<T>.asTestObserver(): Observer<T> {
    val mockObserver = spyk<Observer<T>>()
    this.asLiveData().observeForever(mockObserver)
    return mockObserver
}