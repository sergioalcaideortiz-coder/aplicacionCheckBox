package com.example.aplicacionbuttoncheckbox.pantalla

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionbuttoncheckbox.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun MiPantalla(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()

    var estaCargando by remember { mutableStateOf(false) }

    var mensaje by remember { mutableStateOf("Nada pulsado") }

    var textoVisible by remember { mutableStateOf(false) }

    var mostrarRadioButtons by remember { mutableStateOf(false) }

    val opcionesRadio = listOf("Opción 1", "Opción 2", "Opción 3")
    var opcionSeleccionada by remember { mutableStateOf(opcionesRadio[0]) }

    val imagenes = listOf(
        R.drawable.logo_kotlin,
        R.drawable.logo_java,
        R.drawable.logo_cpp
    )
    var indiceImagen by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.btn_star_big_on),
            contentDescription = "Icono permanente",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(56.dp)
        )

        HorizontalDivider()

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.size(160.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imagenes[indiceImagen]),
                    contentDescription = "Imagen actual (Índice $indiceImagen)",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
        }
        Text(
            text = "Imagen actual: ${indiceImagen + 1} de ${imagenes.size}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Button(
            onClick = {
                mensaje = "Botón presionado"

                indiceImagen = (indiceImagen + 1) % imagenes.size

                coroutineScope.launch {
                    estaCargando = true
                    delay(5.seconds)
                    estaCargando = false
                }
            }
        ) {
            Text(text = "Presionar", fontSize = 16.sp)
        }

        if (estaCargando) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    strokeWidth = 3.dp
                )
                Text(
                    text = "Cargando",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        HorizontalDivider()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    role = Role.Checkbox,
                    selected = textoVisible,
                    onClick = { textoVisible = !textoVisible }
                )
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Checkbox(
                checked = textoVisible,
                onCheckedChange = { textoVisible = it }
            )
            Text(
                text = "Activar",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        if (textoVisible) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Campo de texto:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = mensaje,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        HorizontalDivider()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "Mostrar grupo de opciones",
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = mostrarRadioButtons,
                onCheckedChange = { mostrarRadioButtons = it }
            )
        }

        if (mostrarRadioButtons) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .selectableGroup()
                ) {
                    Text(
                        text = "Selecciona una opción:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    opcionesRadio.forEach { opcion ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    role = Role.RadioButton,
                                    selected = (opcion == opcionSeleccionada),
                                    onClick = {
                                        opcionSeleccionada = opcion
                                        mensaje = "Opción seleccionada: $opcion"
                                    }
                                )
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = (opcion == opcionSeleccionada),
                                onClick = null
                            )
                            Text(
                                text = opcion,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
