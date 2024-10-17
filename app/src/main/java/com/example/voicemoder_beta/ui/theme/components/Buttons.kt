package com.example.voicemoder_beta.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(8.dp), // Margen externo del botón
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6200EE), // Fondo del botón
            contentColor = Color.White // Color del texto
        ),
        shape = RoundedCornerShape(12.dp), // Bordes redondeados
        border = BorderStroke(1.dp, Color.Gray), // Borde gris
    ) {
        Text(
            text = text,
            fontSize = 18.sp, // Tamaño de texto
            fontWeight = FontWeight.Bold // Negrita en el texto
        )
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
