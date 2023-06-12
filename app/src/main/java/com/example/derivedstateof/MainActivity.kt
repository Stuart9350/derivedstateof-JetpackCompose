package com.example.derivedstateof

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.derivedstateof.ui.theme.DerivedStateOfTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DerivedStateOfTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UsernameScreen()
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameScreen() {
    var username by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Define a derived state that checks if the username is valid
    val isUsernameValid by derivedStateOf {
        username.isNotEmpty() && username.matches(Regex("^[a-zA-Z0-9]*$")) && !username.contains(" ")
                && number.length == 10
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = username,
            onValueChange = { newUsername ->
                if (newUsername.contains(" ")) {
                    Toast.makeText(context, "Spaces are not allowed. Using underscore instead.", Toast.LENGTH_SHORT).show()
                    username = newUsername.replace(" ", "")
                } else {
                    username = newUsername
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Username") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        TextField(
            value = number,
            onValueChange = { newNumber ->
                number = if (newNumber.contains(" ")) {
                    Toast.makeText(context, "Spaces are not allowed.", Toast.LENGTH_SHORT).show()
                    newNumber.replace(" ", "")
                } else {
                    newNumber
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        )

        Button(
            onClick = { /* Handle submit logic here */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            enabled = isUsernameValid
        ) {
            Text(text = "Submit")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prev(){
    UsernameScreen()
}
