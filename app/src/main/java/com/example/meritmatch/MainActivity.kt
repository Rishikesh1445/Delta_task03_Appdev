package com.example.meritmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meritmatch.loginScreen.loginPage
import com.example.meritmatch.retrofit.dataclass.Task
import com.example.meritmatch.ui.theme.MeritMatchTheme
import com.example.meritmatch.userScreen.UserScreen
import com.example.meritmatch.userScreen.recyclerVIew.TaskAdapter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeritMatchTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = AppViewModel()
                    val navControllerMain = rememberNavController()
                    val context = LocalContext.current

                    NavHost(navController = navControllerMain, startDestination = ScreenMain.loginPage.route){
                        composable(ScreenMain.loginPage.route){
                            loginPage(navControllerMain, context, viewModel)
                        }
                        composable(ScreenMain.userScreen.route){
                            UserScreen(viewModel, navControllerMain)
                        }
                    }
                }
            }
        }
    }
}