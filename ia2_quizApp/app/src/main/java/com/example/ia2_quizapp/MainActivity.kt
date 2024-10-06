package com.example.ia2_quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ia2_quizapp.ui.theme.Ia2_quizAppTheme
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ia2_quizAppTheme {
                QuizApp()
            }
        }
    }
}

@Composable
fun QuizApp() {
    val questions = listOf(
        "What does 'www' stand for in a website browser?" to "World Wide Web",
        "How long is an Olympic swimming pool (in meters)?" to "50 meters",
        "What countries made up the original Axis powers in World War II?" to "Germany, Italy, and Japan",
        "Which country do cities of Perth, Adelaide & Brisbane belong to?" to "Australia",
        "What geometric shape is generally used for stop signs?" to "Octagon",
        "What is 'cynophobia'?" to "Fear of dogs",
        "What punctuation mark ends an imperative sentence?" to "A period or exclamation point",
        "Who named the Pacific Ocean?" to "Ferdinand Magellan"
    )

    var answer by remember { mutableStateOf("") }
    var currQuestion by remember { mutableStateOf(Random.nextInt(questions.size)) }
    var isAnswerCorrect by remember { mutableStateOf(false) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(10f)
                    .border(1.dp, Color.LightGray)
                    .padding(16.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = questions[currQuestion].first,
                    fontSize = 30.sp,
                    style = TextStyle(lineHeight = 40.sp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = {
                        answer = ""
                        isAnswerSubmitted = false
                    },
                    enabled = isAnswerSubmitted,
                    modifier = Modifier
                        .padding(end = 8.dp)
                ) {
                    Text("Retry")
                }
                Button(
                    onClick = {
                        currQuestion = Random.nextInt(questions.size)
                        answer = ""
                        isAnswerSubmitted = false
                    },
                    enabled = isAnswerSubmitted && isAnswerCorrect,
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text("Next Question")
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = answer,
                    onValueChange = { answer = it },
                    label = { Text("Enter your answer") },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        isAnswerCorrect =
                            answer.equals(questions[currQuestion].second, ignoreCase = true)
                        isAnswerSubmitted = true
                        val message = if (isAnswerCorrect) "Correct!" else "Incorrect!"
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = "Submit")
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ia2_quizAppTheme {
        QuizApp()
    }
}
