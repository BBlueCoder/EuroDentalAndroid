package com.eurodental.features.auth.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurodental.features.auth.data.LoginRepositoryBase
import com.eurodental.features.auth.data.models.LoginCredentials
import com.eurodental.features.auth.data.models.Tokens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

data class LoginUIState(
    val isLoginSuccess : Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val isUserLoggedIn : Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepo: LoginRepositoryBase): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    private val _isError = MutableStateFlow(false)
    private val _isLoginSuccess = MutableStateFlow(false)
    private val _isUserLoggedIn = MutableStateFlow(false)

    private val _credentials = mutableStateOf(LoginCredentials("",""))
    val credentials : State<LoginCredentials>
        get() = _credentials

    init {
        viewModelScope.launch {
             loginRepo.isUserLoggedIn().collectLatest { value ->
                 _isUserLoggedIn.value = value
            }
        }
    }

    val uiState: StateFlow<LoginUIState> = combine(
        _isLoginSuccess,_isLoading, _errorMessage, _isError, _isUserLoggedIn
    ) { isLoginSuccess, isLoading, errorMessage, isError, isUserLoggedIn ->
        LoginUIState(isLoginSuccess = isLoginSuccess,isLoading = isLoading, isError = isError, errorMessage = errorMessage, isUserLoggedIn=isUserLoggedIn)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LoginUIState()
    )

    fun updateCredentials(email : String?, password: String?) {
        _credentials.value = _credentials.value.copy(
            email= email ?: _credentials.value.email,
            password= password?: _credentials.value.password
        )
    }

    fun login() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = loginRepo.login(_credentials.value)
            _isLoading.value = false
            if(result.isSuccess) {
                _isLoginSuccess.value = true
                _isError.value = false
            }else {
                _isError.value = true
                _errorMessage.value = result.exceptionOrNull()!!.message
            }
        }
    }
}