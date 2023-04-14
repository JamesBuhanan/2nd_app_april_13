package com.example.a2ndappapril13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.a2ndappapril13.ui.theme._2ndAppApril13Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _2ndAppApril13Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var name by remember { mutableStateOf("") }
                    var showGreeting by remember { mutableStateOf(false) }
                    var showQuestion by remember { mutableStateOf(false) }
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        if (!showGreeting && !showQuestion) {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = {
                                    Text(
                                        "Enter your name",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(start = 84.dp, end = 16.dp)
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    showGreeting = true
                                },
                                enabled = name.isNotBlank(),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Submit")
                            }
                        } else if (showGreeting) {
                            Greeting(name = name, showGreeting = showGreeting)
                            LaunchedEffect(true) {
                                delay(5000)
                                showGreeting = false
                                showQuestion = true
                            }
                        } else if (showQuestion) {
                            Question(onColorSelected = { color ->
                                // Do something with the selected color
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Question(modifier: Modifier = Modifier, onColorSelected: (String) -> Unit) {
    var selectedColor by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "What's your favorite color?",
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ColorButton(
                text = "Red",
            ) {
                selectedColor = "Red"
                onColorSelected(selectedColor)
            }
            ColorButton(text = "Green") {
                selectedColor = "Green"
                onColorSelected(selectedColor)
            }
            ColorButton(text = "Blue") {
                selectedColor = "Blue"
                onColorSelected(selectedColor)
            }
        }
        if (selectedColor.isNotEmpty()) {
            Image(
                painter = rememberImagePainter(getImageResource(selectedColor)),
                contentDescription = "Image for $selectedColor",
                modifier = Modifier.size(128.dp)
            )
        }
    }
}

@DrawableRes
fun getImageResource(color: String): Int {
    return when (color) {
        "Red" -> R.drawable.troll
        "Green" -> R.drawable.troll
        "Blue" -> R.drawable.troll
        else -> throw IllegalArgumentException("Unknown color: $color")
    }
}
@Composable
fun ColorButton(text: String, onClick: () -> Unit) {
    var buttonPressed by remember { mutableStateOf(false) } // add a mutable state variable

    if (!buttonPressed) { // show the button if it hasn't been pressed
        Button(
            onClick = {
                onClick()
                buttonPressed = true // set the mutable state variable to true
            },
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, showGreeting: Boolean, modifier: Modifier = Modifier) {
    if (showGreeting) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hello $name!",
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Thank you for submitting your name.",
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _2ndAppApril13Theme {
        Greeting("Android", showGreeting = true)
    }
}