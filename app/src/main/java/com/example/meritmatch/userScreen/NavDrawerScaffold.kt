package com.example.meritmatch.userScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meritmatch.AppViewModel
import com.example.meritmatch.ScreenMain
import com.example.meritmatch.dataClass.NavigationItem
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(viewModel: AppViewModel, mainNav: NavController){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val navControllerUser = rememberNavController()

    val navItems = listOf(
        NavigationItem(title = "Your Account",route = com.example.meritmatch.UserScreen.account.route, selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person),
        NavigationItem(title = "Tasks",route = com.example.meritmatch.UserScreen.tasks.route, selectedIcon = Icons.Filled.Email, unselectedIcon = Icons.Outlined.Email),
        NavigationItem(title = "Reserved",route = com.example.meritmatch.UserScreen.reserved.route, selectedIcon = Icons.Filled.ShoppingCart, unselectedIcon = Icons.Outlined.ShoppingCart, badgeCount = viewModel.user.value.reserved),
        NavigationItem(title = "Completed",route = com.example.meritmatch.UserScreen.completed.route, selectedIcon = Icons.Filled.Check, unselectedIcon = Icons.Outlined.Check, badgeCount = viewModel.user.value.completed),
        NavigationItem(title = "Transactions",route = com.example.meritmatch.UserScreen.transactions.route, selectedIcon = Icons.Filled.Refresh, unselectedIcon = Icons.Outlined.Refresh),
        NavigationItem(title = "Uploaded Tasks", route = com.example.meritmatch.UserScreen.upload.route, selectedIcon = Icons.Filled.Star, unselectedIcon = Icons.Outlined.Star),
        NavigationItem(title = "Search", route = com.example.meritmatch.UserScreen.search.route, selectedIcon = Icons.Filled.Search, unselectedIcon = Icons.Outlined.Search ),
        NavigationItem(title = "Log Out",route = com.example.meritmatch.UserScreen.logout.route, selectedIcon = Icons.Filled.ExitToApp, unselectedIcon = Icons.Outlined.ExitToApp),
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                navItems.forEachIndexed{ index, item->
                    NavigationDrawerItem(
                        label = { Text(text = item.title, color = Color.Green) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            navControllerUser.navigate(item.route)
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }},
                        icon = { Icon(
                            imageVector = if(index == selectedItemIndex){ item.selectedIcon }
                            else{item.unselectedIcon},
                            tint = Color.Green,
                            contentDescription = item.title
                        )
                        },
                        badge = { item.badgeCount?.let { Text(text = it.toString()) }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
            TopAppBar(title = { Text(text = "Merit Match", color = Color.Green, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.ExtraBold) },
            navigationIcon = {
                IconButton(onClick = { scope.launch { drawerState.open() } }
                ) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Color.Green)
                }
            })
        },

        ) {
            NavHost(navController = navControllerUser, startDestination = com.example.meritmatch.UserScreen.account.route){
                composable(com.example.meritmatch.UserScreen.account.route){ Account(viewModel)}
                composable(com.example.meritmatch.UserScreen.tasks.route){ Tasks(viewModel)}
                composable(com.example.meritmatch.UserScreen.reserved.route){ Reserved(viewModel)}
                composable(com.example.meritmatch.UserScreen.completed.route){ Completed(viewModel)}
                composable(com.example.meritmatch.UserScreen.transactions.route){ Transactions(viewModel)}
                composable(com.example.meritmatch.UserScreen.upload.route){ Uploads(viewModel)}
                composable(com.example.meritmatch.UserScreen.search.route){ Search(viewModel)}
                composable(com.example.meritmatch.UserScreen.logout.route){ mainNav.navigate(ScreenMain.loginPage.route); viewModel.logout() }
            }
        }
    }
}