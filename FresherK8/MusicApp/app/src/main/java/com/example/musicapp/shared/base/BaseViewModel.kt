package com.example.musicapp.shared.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.shared.utils.scheduler.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val isLoading = MutableLiveData<Boolean>()
    val exception = MutableLiveData<Exception>()

    protected fun <T> launchTaskSync(
        onRequest: suspend CoroutineScope.() -> DataResult<T>,
        onSuccess: (T) -> Unit = {},
        onFailure: (String) -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) = viewModelScope.launch {
        isLoading.postValue(true)
        when (val asynchronousTasks = onRequest(this)) {
            is DataResult.Success -> onSuccess(asynchronousTasks.data)
            is DataResult.Failure -> onFailure(asynchronousTasks.message)
            is DataResult.Error -> onError(asynchronousTasks.exception)
        }
        isLoading.postValue(false)
    }

}
