package com.example.mad_24172012057_jetpack.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mad_24012012009_practical5.ui.theme.MAD_24012012009_Practical5Theme


@Composable
fun FormField(
    label: String,
    textState: String,
    onTextChange: (String) -> Unit,
    isPassword: Boolean = false,
    isNumeric: Boolean = false
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)) {

        if (!isNumeric) {
            OutlinedTextField(
                value = textState,
                onValueChange = onTextChange,
                modifier = Modifier
                    .padding(start = 0.dp)
                    .fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                visualTransformation = if (isPassword)
                    PasswordVisualTransformation()
                else VisualTransformation.None,
                label = { Text("Enter the $label") },
                placeholder = { Text("Enter the $label") }
            )
        }
        else
            NumericOutlinedTextField(
                label = label,
                value = textState,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(2f),
                onTextChange = onTextChange)
    }
}

@Composable
fun NumericOutlinedTextField(label: String,
                             value: String,
                             modifier: Modifier,
                             onTextChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { newvalue ->
            if (newvalue.all { it.isDigit() }){
                onTextChange(newvalue)
            }
        },
        modifier = modifier,
        label = { Text("Enter the $label") },
        textStyle = LocalTextStyle.current.copy(fontSize = 10.sp),
        placeholder = { Text("Enter the $label") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = VisualTransformation.None
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MAD_24172012057_jetpackTheme {
        FormField("Android","",{})
    }

}
