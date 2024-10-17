package com.example.voicemoder_beta

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.voicemoder_beta.ui.components.CustomButton
import com.example.voicemoder_beta.ui.components.PrimaryButton
import com.example.voicemoder_beta.ui.theme.VoiceModer_betaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoiceModer_betaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current // Obtener el contexto actual
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting(name = "Pedrito")
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre el saludo y el botón
        CustomButton(
            text = "Click Me",
            onClick = {
                // Acción del botón
                Toast.makeText(context, "Botón presionado", Toast.LENGTH_SHORT).show()
            }
        )
        PrimaryButton(
            text = "Click Me",
            onClick = {
                // Acción del botón
                Toast.makeText(context, "Botón presionado 2", Toast.LENGTH_SHORT).show()
            }
        )
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
    VoiceModer_betaTheme {
        MainScreen()
    }
}
