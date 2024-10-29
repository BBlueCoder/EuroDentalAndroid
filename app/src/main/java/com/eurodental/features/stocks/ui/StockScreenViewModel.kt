package com.eurodental.features.stocks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurodental.UIState
import com.eurodental.features.stocks.data.StockRepoBase
import com.eurodental.features.stocks.data.models.Product
import com.eurodental.features.tasks.data.models.Task
import com.eurodental.features.tasks.ui.TaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StockScreenViewModel @Inject constructor(
    private val stockRepoBase: StockRepoBase
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)
    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    private val _data: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())

    val uiState: StateFlow<UIState<Product>> = combine(
        _isLoading, _isError, _errorMessage, _data
    ) { isLoading, isError, errorMessage, data ->
        UIState(isLoading, isError, errorMessage, data)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UIState()
    )

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            val res = stockRepoBase.getAllProducts()
            _isLoading.value = false
            if (res.isSuccess) {
                _isError.value = false
                _data.value = res.getOrNull() ?: emptyList()
            } else {
                _isError.value = true
                _errorMessage.value = res.exceptionOrNull()?.message
            }
        }
    }
}