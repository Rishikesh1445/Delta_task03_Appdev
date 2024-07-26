package com.example.meritmatch.userScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meritmatch.AppViewModel
import com.example.meritmatch.dataClass.CurrentUser

@Composable
fun Reserved(viewModel: AppViewModel){
    LaunchedEffect(Unit){
        viewModel.fetchReservedTask()
    }
    when (viewModel.user.value.uiState) {
        CurrentUser.UiState.Loading -> {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Loading Data...",
                    color = Color.Green,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace,
                )
            }

        }
        CurrentUser.UiState.Success -> {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.padding(40.dp))
                LazyColumn(contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)){
                    if(viewModel.user.value.rtasks != null) {
                        items(viewModel.user.value.rtasks!!) { item ->
                            customCard(item, viewModel, LocalContext.current)
                        }
                    }
                }
            }
        }
    }
}