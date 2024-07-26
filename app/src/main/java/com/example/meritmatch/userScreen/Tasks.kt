package com.example.meritmatch.userScreen

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.meritmatch.AppViewModel
import com.example.meritmatch.dataClass.CurrentUser
import com.example.meritmatch.loginScreen.letters
import com.example.meritmatch.retrofit.dataclass.Task

@Composable
fun Tasks(viewModel: AppViewModel){
    var showDialouge by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        viewModel.fetchTask()
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
                    if(viewModel.user.value.tasks != null) {
                        items(viewModel.user.value.tasks!!) { item ->
                            if (!item.reserved) {
                                customCard(item, viewModel, LocalContext.current)
                            }
                        }
                    }
                }
            }
            Column(verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxSize()) {
                FloatingActionButton(
                    onClick = { showDialouge = true },
                    shape = RoundedCornerShape(50),
                    containerColor = Color.Green,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "post task")
                }
            }
        }
    }

    var title by remember{ mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var karmaPoints_string by remember { mutableStateOf("") }
    var karmaPoints by remember { mutableDoubleStateOf(0.0) }

    if(showDialouge){
        AlertDialog(onDismissRequest = { showDialouge=false },
            confirmButton = { Button(onClick = {
                if(title.isNotEmpty() && description.isNotEmpty() && karmaPoints_string.isNotEmpty()) {
                    if(karmaPoints < viewModel.user.value.karmaPoint!!) {
                        showDialouge = false;viewModel.postTask(title, description, location, karmaPoints);
                        title = ""; description = ""; karmaPoints=0.0; karmaPoints_string=""
                    }else{
                        Log.d("loadTask", "Karma Points higher")
                    }
                } },
                colors = ButtonDefaults.buttonColors(Color.Green)) {
                Text(text = "Post Task" , color = Color.Black)
            }
            },
            title = { Text(text = "Post a Task")},
            text = {
                Column {
                    OutlinedTextField(value = title, onValueChange = {if (it.length<15){title = it}},
                        singleLine = true, label = { Text(text = "Title", color = Color.Green)},
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = Color.Green,
                            focusedLeadingIconColor = Color.Green,
                            focusedBorderColor = Color.Green
                        ),
                        modifier = Modifier.padding(16.dp)
                        )
                    OutlinedTextField(value = description, onValueChange = {if (it.length<25){description = it}},
                        singleLine = true, label = { Text(text = "Description", color = Color.Green)},
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = Color.Green,
                            focusedLeadingIconColor = Color.Green,
                            focusedBorderColor = Color.Green
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                    OutlinedTextField(value = location, onValueChange = {if (it.length<25){location = it}},
                        singleLine = true, label = { Text(text = "Location", color = Color.Green)},
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = Color.Green,
                            focusedLeadingIconColor = Color.Green,
                            focusedBorderColor = Color.Green
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                    OutlinedTextField(value = karmaPoints_string, onValueChange = { karmaPoints_string = it;
                        karmaPoints = it.toDouble()},
                        singleLine = true, label = { Text(text = "Karma Points", color = Color.Green)},
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = Color.Green,
                            focusedLeadingIconColor = Color.Green,
                            focusedBorderColor = Color.Green
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        )
    }
}

@Composable
fun customCard(detail : Task, viewModel: AppViewModel, context:Context){
    var buttonText = remember { mutableStateOf("Reserve") }

    val text = " Title : ${detail.title} , Description : ${detail.description}, KarmaPoints : ${detail.karmaPoints}, Reserved: ${detail.reserved}, Completed: ${detail.completed}"
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Text(text = detail.title, color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(6.dp))
        Text(text = detail.description, color = Color.White, fontSize = 14.sp,
            modifier = Modifier.padding(9.dp))
        Text(text = "Location: ${detail.location}", color = Color.White, fontSize = 14.sp,
            modifier = Modifier.padding(9.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(text = detail.karmaPoints.toString(), color = Color.White, fontSize = 20.sp,
                modifier = Modifier.padding(6.dp))
            Button(onClick = {startActivity(context, shareIntent, null) },
                colors = ButtonDefaults.buttonColors(Color.Green)) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = Color.White)
                Text(text = "Share", color = Color.White)
            }
            if(!detail.reserved) {
                Button(onClick = { buttonText.value = "Reserved"; viewModel.reserve(detail.id) },
                    colors = ButtonDefaults.buttonColors(Color.Green)) {
                    Text(text = buttonText.value, color = Color.White)
                }
            }
        }
    }
}