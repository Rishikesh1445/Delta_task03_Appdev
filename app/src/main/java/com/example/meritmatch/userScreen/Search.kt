package com.example.meritmatch.userScreen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.meritmatch.AppViewModel
import com.example.meritmatch.retrofit.dataclass.Task

@Composable
fun Search(viewModel: AppViewModel){
    LaunchedEffect(Unit){
        viewModel.fetchTask()
        viewModel.fetchReservedTask()
        viewModel.fetchCompletedTask()
        viewModel.fetchPostedTask()
        viewModel.stateFLowTasks()
    }

    val searchtext by viewModel.searchText.collectAsState()
    val tasktoshow by viewModel.taskstoshow.collectAsState()

    Column(
        Modifier.padding(16.dp)){
        Spacer(modifier = Modifier.padding(40.dp))
        TextField(
            value = searchtext,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search")}
            )
        Row(modifier = Modifier.padding(8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            filterChipsLocation(name = "location", viewModel)
            filterChipsKarmaPoints(name = "KarmaPoints", viewModel = viewModel)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            items(tasktoshow){task->
                if(viewModel.user.value.karmaFilter.second == null) {
                    searchCustomCard(task, LocalContext.current)
                }else{
                    if((viewModel.user.value.karmaFilter.first?.rangeTo(viewModel.user.value.karmaFilter.second!!))?.contains(task.karmaPoints.toInt()) == true){
                        searchCustomCard(task, LocalContext.current)
                    }
                }
            }
        }
    }
}

@Composable
fun searchCustomCard(detail : Task, context: Context){
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
        Text(text = " karmaPoints : ${detail.karmaPoints}", color = Color.White, fontSize = 15.sp,
            modifier = Modifier.padding(6.dp))
        Text(text = "Reserved: ${detail.reserved}", color = Color.White, fontSize = 15.sp,
            modifier = Modifier.padding(6.dp))
        Text(text = "Completed: ${detail.completed}", color = Color.White, fontSize = 15.sp,
            modifier = Modifier.padding(6.dp))
        Button(onClick = { ContextCompat.startActivity(context, shareIntent, null) },
            colors = ButtonDefaults.buttonColors(Color.Green), modifier = Modifier.padding(8.dp)) {
            Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = Color.White)
            Text(text = "Share", color = Color.White)
        }
    }
}

@Composable
fun filterChipsLocation(
    name: String,
    viewModel: AppViewModel,

){
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    Card(
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .onSizeChanged {
                itemHeight = with(density){it.height.toDp()}
            }

    ) {
        Box(
            modifier = Modifier
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
                        onPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        }
                    )
                }
                .padding(8.dp)
        ){
            Row(horizontalArrangement = Arrangement.Start) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = name,
                    tint = Color.Green,
                    modifier = Modifier.padding(8.dp)
                )
                Text(text = name, fontFamily = FontFamily.Monospace, color = Color.Green, modifier = Modifier.padding(8.dp))
            }
        }

        DropdownMenu(expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            for( location in viewModel.user.value.locations){
                DropdownMenuItem(
                    text = { Text(text = location) },
                    onClick = { isContextMenuVisible = false; viewModel.onSearchTextChange(location) })
            }
            DropdownMenuItem(text = { Text(text = "Clear Filter") }, onClick = { isContextMenuVisible = false; viewModel.onSearchTextChange("")  })
        }
    }
}

@Composable
fun filterChipsKarmaPoints(
    name: String,
    viewModel: AppViewModel,

    ){
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    Card(
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .onSizeChanged {
                itemHeight = with(density){it.height.toDp()}
            }

    ) {
        Box(
            modifier = Modifier
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
                        onPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        }
                    )
                }
                .padding(8.dp)
        ){
            Row(horizontalArrangement = Arrangement.Start) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = name,
                    tint = Color.Green,
                    modifier = Modifier.padding(8.dp)
                )
                Text(text = name, fontFamily = FontFamily.Monospace, color = Color.Green, modifier = Modifier.padding(8.dp))
            }
        }

        DropdownMenu(expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            DropdownMenuItem(text = { Text(text = "<100") }, onClick = { isContextMenuVisible=false; viewModel.changeKarmaFilter(0,100) })
            DropdownMenuItem(text = { Text(text = ">100, <200") }, onClick = { isContextMenuVisible=false; viewModel.changeKarmaFilter(100,200) })
            DropdownMenuItem(text = { Text(text = ">200, <300") }, onClick = { isContextMenuVisible=false; viewModel.changeKarmaFilter(200,300) })
            DropdownMenuItem(text = { Text(text = ">300 ") }, onClick = { isContextMenuVisible=false; viewModel.changeKarmaFilter(300,100000) })
            DropdownMenuItem(text = { Text(text = "clear filter") }, onClick = { isContextMenuVisible=false; viewModel.changeKarmaFilter(null,null) })
        }
    }
}