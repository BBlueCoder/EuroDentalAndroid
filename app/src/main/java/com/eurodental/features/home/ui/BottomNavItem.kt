package com.eurodental.features.home.ui

import kotlin.reflect.KClass

data class BottomNavItem<T: Any>(
    val route : T,
    val label : String,
    val icon : Int
)


