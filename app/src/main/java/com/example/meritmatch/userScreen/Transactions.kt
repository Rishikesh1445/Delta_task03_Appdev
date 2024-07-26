package com.example.meritmatch.userScreen

import android.widget.Toast
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.meritmatch.AppViewModel
import com.example.meritmatch.retrofit.dataclass.TransactionResponse
import com.example.meritmatch.retrofit.dataclass.UsersName


data class DropDownItem(
    val text:String
)

@Composable
fun Transactions(viewModel: AppViewModel){
    LaunchedEffect(Unit){
        viewModel.usersNames()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.padding(40.dp))
        LazyColumn(contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)){
            items(viewModel.user.value.otherUsers){item ->
                User(item, Modifier.fillMaxWidth(), viewModel)
            }
        }
    }
}

@Composable
fun User(
    usercard: UsersName,
    modifier: Modifier=Modifier,
    viewModel: AppViewModel
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
                .fillMaxWidth()
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                            viewModel.transaction(usercard.id)
                        },
                        onPress = {
                            viewModel.transaction(usercard.id)
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                        }
                    )
                }
                .padding(16.dp)
        ){
            Row(horizontalArrangement = Arrangement.Start) {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "profile",
                    tint = Color.Green,
                    modifier = Modifier.padding(8.dp)
                )
                Text(text = usercard.name, fontFamily = FontFamily.Monospace, color = Color.Green, modifier = Modifier.padding(8.dp))
            }
        }

        DropdownMenu(expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
            ) {
            DropdownMenuItem(text = { Text(text = "Given = ${viewModel.user.value.transactionsUser?.lent}") }, onClick = { isContextMenuVisible=false })
            DropdownMenuItem(text = { Text(text = "Received = ${viewModel.user.value.transactionsUser?.received}") }, onClick = { isContextMenuVisible=false })
            DropdownMenuItem(text = { Text(text = "Received by You = ${viewModel.user.value.transactionsUser?.reservedYou}") }, onClick = { isContextMenuVisible=false })
            DropdownMenuItem(text = { Text(text = "Received by User = ${viewModel.user.value.transactionsUser?.reservedOther}") }, onClick = { isContextMenuVisible=false })
        }
    }
}