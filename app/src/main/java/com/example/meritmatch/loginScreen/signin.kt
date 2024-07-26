package com.example.meritmatch.loginScreen

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.meritmatch.AppViewModel
import com.example.meritmatch.R
import com.example.meritmatch.sealedClass.LoginScreen
import kotlinx.coroutines.delay
import kotlin.streams.toList

@Composable
fun SignIn(loginNav: NavController, mainNavController: NavController, viewModel: AppViewModel, context: Context){
    var username by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        TypewriterText(texts = listOf("Merit Match"))
        OutlinedTextField(value = username,
            onValueChange ={ if(it.length<10){username  = it.letters()}},
            singleLine = true,
            label = { Text(text = "Username", color = Color.Green) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = "Profile") },
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
        Button(onClick = {
            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show()
            }else {
                viewModel.signIn(username, password, mainNavController, context)
            } }, shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green
            ), modifier = Modifier.padding(16.dp)) {
            Text(text = "SIGN IN")
        }
        Row(){
            Text(text = "Don't have an account?");
            Text(text = "Sign up", color = Color.Green, textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    loginNav.navigate(LoginScreen.SignUp.route)
                }
            )
        }
    }
}


fun String.letters() = filter { it.isLetter() }

@Composable
fun TypewriterText(texts: List<String>) {
    var textIndex by remember {
        mutableStateOf(0)
    }
    var textToDisplay by remember {
        mutableStateOf("")
    }
    val textCharsList = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            texts.map {
                it.splitToCodePoints()
            }
        } else {
            texts.map { text ->
                text.map {
                    it.toString()
                }
            }
        }
    }

    LaunchedEffect(
        key1 = texts,
    ) {
        while (textIndex < textCharsList.size) {
            textCharsList[textIndex].forEachIndexed { charIndex, _ ->
                textToDisplay = textCharsList[textIndex]
                    .take(
                        n = charIndex + 1,
                    ).joinToString(
                        separator = "",
                    )
                delay(160)
            }
            textIndex = (textIndex + 1)
            delay(1000)
        }
    }

    Text(
        text = textToDisplay,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Green,
        fontFamily = FontFamily.Monospace
    )
}

@RequiresApi(Build.VERSION_CODES.N)
fun String.splitToCodePoints(): List<String> {
    return codePoints()
        .toList()
        .map {
            String(Character.toChars(it))
        }
}