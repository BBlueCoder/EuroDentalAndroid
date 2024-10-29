package com.eurodental.features.stocks.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun StockScreen(
    navHostController: NavHostController,
    viewModel: StockScreenViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        ReferenceSearchBar()


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReferenceSearchBar(modifier: Modifier = Modifier) {
    val searchFieldState = rememberTextFieldState()
    SearchBar(inputField = {
        SearchBarDefaults.InputField(
            state = searchFieldState,
            onSearch = {},
            expanded = false,
            onExpandedChange = {},
            placeholder = { Text(text = "Search Reference")}
        )
    }, expanded = false, onExpandedChange = {}) {

    }
}