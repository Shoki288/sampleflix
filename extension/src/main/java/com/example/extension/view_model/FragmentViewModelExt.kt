package com.example.extension.view_model

import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras

inline fun <reified VM : ViewModel> Fragment.assistedViewModels(
    viewModelStoreOwner: ViewModelStoreOwner = this,
    crossinline factoryProducer: () -> VM
): Lazy<VM> {
    val owner by lazy(LazyThreadSafetyMode.NONE) { viewModelStoreOwner }
    return createViewModelLazy(
        viewModelClass = VM::class,
        storeProducer = { owner.viewModelStore },
        extrasProducer = {
            (owner as? HasDefaultViewModelProviderFactory)?.defaultViewModelCreationExtras
                ?: CreationExtras.Empty
        },
        factoryProducer = {
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>) = factoryProducer() as T
            }
        }
    )
}