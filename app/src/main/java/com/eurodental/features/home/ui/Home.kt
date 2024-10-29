package com.eurodental.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eurodental.R
import com.eurodental.navigation.ClientScreen
import com.eurodental.navigation.MainGraph
import com.eurodental.navigation.StockScreen
import com.eurodental.navigation.TaskScreen

@Composable
fun Home(
    navigateToTaskDetails : (taskId : Int) -> Unit,
    navigateToAuth : () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val isUserLoggedInState by homeViewModel.isUserLoggedIn.collectAsStateWithLifecycle()

    if(!isUserLoggedInState) {
        navigateToAuth()
    }

    var testGo by remember {
        mutableStateOf(false)
    }
    
    if (testGo) {
        val bottomNavItems = listOf(
            BottomNavItem(TaskScreen,"Task", R.drawable.baseline_format_list_bulleted_24),
            BottomNavItem(StockScreen,"Stock", R.drawable.baseline_layers_24),
            BottomNavItem(ClientScreen,"Client", R.drawable.baseline_people_24),
        )

        val navController = rememberNavController()


        Scaffold(
            bottomBar =
            {BottomNav(navController = navController, items = bottomNavItems)}
        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                MainGraph(navController = navController, navigateToTaskDetails = navigateToTaskDetails)
            }
        }
    }else {
        Box(modifier = Modifier.padding(50.dp)) {
            Button(onClick = {
                testGo = true 
            }) {
                Text(text = "Go!")
            }
        }
    }
    
}

@Composable
fun BottomNav(navController: NavController, items: List<BottomNavItem<out Any>>) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(selected = selectedTabIndex == index, onClick = {
                selectedTabIndex = index
                navController.navigate(item.route)
            }, icon = {
                Icon(painter = painterResource(id = item.icon), contentDescription = "Icon" )
            }, label = {
                Text(text = item.label)
            })
        }
    }
}