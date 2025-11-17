package com.example.mad_24172012057_jetpack.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mad_24172012057_jetpack.R
import com.example.mad_24172012057_jetpack.screen.component.CustomButton
import com.example.mad_24172012057_jetpack.screen.component.FormField
import com.example.mad_24172012057_jetpack.ui.theme.MAD_24172012057_jetpackTheme
import com.example.mad_24172012057_jetpack.ui.theme.Purple40

@Composable
fun LoginScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            LoginUI(navController = navController)
        }
    }
}

fun navigator(navController: NavController) {
    navController.navigate("register") {
        popUpTo("login") { inclusive = true }
    }
}

@Composable
fun LoginUI(navController: NavController? = null) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.guni_pink_logo),
            contentDescription = "GUNI Pink Logo",
            modifier = Modifier
                .height(150.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFDFF0FF))
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {
                // Email
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Email", modifier = Modifier.width(100.dp))
                    FormField(
                        label = "Email",
                        textState = email,
                        onTextChange = { email = it },
                        isNumeric = false
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Password", modifier = Modifier.width(100.dp))
                    FormField(
                        label = "Password",
                        textState = password,
                        onTextChange = { password = it },
                        isPassword = true
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Forgot Password?", modifier = Modifier.align(Alignment.End))
                Spacer(modifier = Modifier.height(16.dp))

                CustomButton(
                    label = "Login",
                    onClick = {},
                    backgroundColor = Purple40
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an account?")
            TextButton(
                onClick = { navController?.let { navigator(it) } },
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(text = "SIGNUP", color = colorResource(R.color.pink))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    MAD_24172012057_jetpackTheme {
        LoginUI()
    }
}