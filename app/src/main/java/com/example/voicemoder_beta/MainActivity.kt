package com.example.voicemoder_beta

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.PlaybackParams
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
                        onPlayAudio = { pitch -> playAudio(pitch) } // Se pasa el pitch aquí
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

    private fun playAudio(pitch: Float = 1.0f) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mediaPlayer = MediaPlayer().apply {
                try {
                    setDataSource(fileName)
                    prepare()
                    val playbackParams = PlaybackParams().apply {
                        this.pitch = pitch  // Aplicar el pitch aquí
                    }
                    this.playbackParams = playbackParams
                    start()
                    Toast.makeText(this@MainActivity, "Reproduciendo audio con pitch $pitch", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } else {
            Toast.makeText(this@MainActivity, "Cambio de pitch no soportado en esta versión", Toast.LENGTH_SHORT).show()
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
    onPlayAudio: (Float) -> Unit // Cambia aquí para aceptar el pitch
) {
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }
    var pitch by remember { mutableStateOf(1.0f) } // Valor de pitch inicial
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
                    onPlayAudio(pitch)
                } else {
                    onStartRecording()
                }
                isRecording = !isRecording
            }
        )
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los botones

        // Slider para ajustar el pitch
        Text(text = "Pitch: ${"%.2f".format(pitch)}")
        Slider(
            value = pitch,
            onValueChange = { pitch = it },
            valueRange = 0.5f..2.0f,
            steps = 10
        )

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
