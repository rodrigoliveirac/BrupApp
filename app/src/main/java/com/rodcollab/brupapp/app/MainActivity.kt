package com.rodcollab.brupapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rodcollab.brupapp.app.theme.BrupAppTheme
import com.rodcollab.brupapp.hangman.repository.HangmanGame
import com.rodcollab.brupapp.hangman.ui.QuestionScreen

class MainActivity : ComponentActivity() {

    private val game: HangmanGame by lazy {
        val application = application as BrupApp
        application.hangmanGame
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            BrupAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QuestionScreen(game = game)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BrupAppTheme {
        Greeting("Android")
    }
}