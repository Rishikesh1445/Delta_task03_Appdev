package com.example.meritmatch.userScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meritmatch.AppViewModel

@Composable
fun game(viewModel: AppViewModel){
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(30.dp))
        Text(text = "Spin the Wheel", fontFamily = FontFamily.Monospace, fontSize = 30.sp, color = Color.Green )
        Canvas(modifier = Modifier.size(400.dp)){
            val canvasWidth = size.width
            val canvasHeight = size.height
            val radius = (canvasWidth.coerceAtMost(canvasHeight)) / 2
            drawArc(
                color = Color.Red,
                startAngle = 90f + viewModel.game.value.redRotation,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset((canvasWidth - radius) / 2, (canvasHeight - radius) / 2),
                size = Size(radius,radius)
            )
            drawArc(
                color = Color.Blue,
                startAngle = 270f + viewModel.game.value.blueRotation,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset((canvasWidth - radius) / 2, (canvasHeight - radius) / 2),
                size = Size(radius,radius)
            )
        }
        Text(text = "Red: No Karma Points" , fontFamily = FontFamily.Monospace, color = Color.Red,)
        Text(text = "Blue: +100 Karma Points" , fontFamily = FontFamily.Monospace, color = Color.Blue,)
        Text(text = "Whatever colour in bottom half will be considered" , fontFamily = FontFamily.Monospace, color = Color.White)
        Spacer(modifier = Modifier.padding(32.dp))
        Button(onClick = { viewModel.game(context) }, colors = ButtonDefaults.buttonColors(Color.Green)) {
            Text(text = "Spin it", fontFamily = FontFamily.SansSerif, color = Color.White)
        }
    }
}