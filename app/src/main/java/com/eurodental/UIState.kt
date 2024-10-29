package com.eurodental

data class UIState<T>(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val data : List<T> = emptyList()
)
