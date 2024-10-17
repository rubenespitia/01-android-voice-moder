package com.example.voicemoder_beta.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = text)
    }
}

// Botón primario con un estilo específico
@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
        // Aquí puedes agregar más estilos y temas personalizados
    ) {
        Text(text = text)
    }
}

// Botón con icono
@Composable
fun IconButton(text: String, icon: @Composable () -> Unit, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        icon() // Muestra el ícono
        Text(text = text)
    }
}
