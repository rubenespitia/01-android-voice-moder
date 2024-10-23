package com.example.voicemoder_beta

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.voicemoder_beta.ui.components.CustomButton
import com.example.voicemoder_beta.ui.components.PrimaryButton
import com.example.voicemoder_beta.ui.theme.VoiceModer_betaTheme
import java.io.IOException

class MainActivity : ComponentActivity() {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private val fileName: String by lazy {
        "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Solicitar permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), 0)
        }

        enableEdgeToEdge()
        setContent {
            VoiceModer_betaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        onStartRecording = { startRecording() },
                        onStopRecording = { stopRecording() },
                        onPlayAudio = { playAudio() }
                    )
                }
            }
        }
    }

    private fun startRecording() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(fileName)
            try {
                prepare()
                start()
                Toast.makeText(this@MainActivity, "Grabando audio...", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
            Toast.makeText(this@MainActivity, "Grabación detenida.", Toast.LENGTH_SHORT).show()
        }
        mediaRecorder = null
    }

    private fun playAudio() {
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
                Toast.makeText(this@MainActivity, "Reproduciendo audio...", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaRecorder?.release()
        mediaPlayer?.release()
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onPlayAudio: () -> Unit
) {
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Greeting(name = "Pedrito")
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre el saludo y el botón

        // Botón para grabar/terminar grabación
        PrimaryButton(
            text = if (isRecording) "Detener Grabación" else "Iniciar Killer Voice",
            onClick = {
                if (isRecording) {
                    onStopRecording()
                    onPlayAudio()
                } else {
                    onStartRecording()
                }
                isRecording = !isRecording
            }
        )
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los botones

        /*// Botón para reproducir
        PrimaryButton(
            text = "Reproducir Audio",
            onClick = {
                onPlayAudio()
            }
        )*/
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
        MainScreen(
            onStartRecording = {},
            onStopRecording = {},
            onPlayAudio = {}
        )
    }
}
