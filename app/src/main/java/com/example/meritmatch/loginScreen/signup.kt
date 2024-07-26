package com.example.meritmatch.loginScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.meritmatch.AppViewModel
import com.example.meritmatch.R

@Composable
fun Signup(loginNav:NavController, viewModel: AppViewModel, context: Context){
    var username by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)  {
        TypewriterText(listOf("Sign Up"))

        OutlinedTextField(value = username,
            onValueChange ={ if(it.length<10){username = it.letters()}},
            singleLine = true,
            label = { Text(text = "Username", color = Color.Green) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = "Username") },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.Green,
                focusedLeadingIconColor = Color.Green,
                focusedBorderColor = Color.Green
            ),
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(value = password,
            onValueChange ={ if(it.length<10){password  = it}},
            singleLine = true,
            label = { Text(text = "Password", color = Color.Green) },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.password_vector), contentDescription = "password") },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.Green,
                focusedLeadingIconColor = Color.Green,
                focusedBorderColor = Color.Green
            ),
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(context, "Username or password field cannot be empty", Toast.LENGTH_SHORT).show()
                }else {
                    viewModel.signUp(username, password, loginNav, context)
                } },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green
            )) {
            Text(text = "REGISTER")
        }
    }
}