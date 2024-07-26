package com.example.meritmatch.userScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meritmatch.AppViewModel

@Composable
fun Account(viewModel: AppViewModel){
    LaunchedEffect(Unit){
        viewModel.userdetails()
        viewModel.fetchTask()
        viewModel.fetchPostedTask()
        viewModel.fetchReservedTask()
        viewModel.fetchCompletedTask()
        viewModel.editNotificationDatabase()
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "profile picture", tint = Color.Green,
            modifier = Modifier.size(100.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = "Username",
                fontFamily = FontFamily.Monospace,
                color = Color.Green,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            viewModel.user.value.username?.let {
                Text(
                    text = it,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = "Karma Points",
                fontFamily = FontFamily.Monospace,
                color = Color.Green,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = viewModel.user.value.karmaPoint.toString(),
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = "Reputations",
                fontFamily = FontFamily.Monospace,
                color = Color.Green,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = viewModel.user.value.reputation.toString(),
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = "Reserved",
                fontFamily = FontFamily.Monospace,
                color = Color.Green,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = viewModel.user.value.reserved.toString(),
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = "Completed",
                fontFamily = FontFamily.Monospace,
                color = Color.Green,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = viewModel.user.value.completed.toString(),
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}