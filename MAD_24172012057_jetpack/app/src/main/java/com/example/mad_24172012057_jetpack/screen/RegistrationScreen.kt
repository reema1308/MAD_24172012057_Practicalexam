package com.example.mad_24172012057_jetpack.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mad_24172012057_jetpack.R
import com.example.mad_24172012057_jetpack.screen.component.CustomButton
import com.example.mad_24172012057_jetpack.screen.component.FormField
import com.example.mad_24172012057_jetpack.ui.theme.MAD_24172012057_jetpackTheme

data class Field(
    val label: String,
    var textState: String = "",
    val isPassword: Boolean = false,
    val isNumeric: Boolean = false
)

@Composable
fun RegisterUI(navController: NavController) {
    val fields = remember {
        mutableStateListOf(
            Field("Name"),
            Field("Phone Number"),
            Field("City"),
            Field("Email"),
            Field("Password", isPassword = true),
            Field("Confirm Password", isPassword = true)
        )
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)) {

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.guni_pink_logo),
            contentDescription = "GUNI Pink Logo",
            modifier = Modifier
                .height(100.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Fit
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color(223, 235, 255))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                fields.forEach { field ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = field.label,
                            modifier = Modifier.width(120.dp) // fixed width for label
                        )
                        FormField(
                            label = field.label,
                            textState = field.textState,
                            onTextChange = { field.textState = it },
                            isPassword = field.isPassword,
                            isNumeric = field.isNumeric
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                CustomButton(
                    label = "Register",
                    onClick = {
                        // You can handle register click here
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account? ")
            TextButton(
                onClick = { navController.navigate("login") },
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    text = "LOGIN",
                    color = colorResource(R.color.pink)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    MAD_24172012057_jetpackTheme {
        val navController = rememberNavController()
        RegisterUI(navController)
    }
}
