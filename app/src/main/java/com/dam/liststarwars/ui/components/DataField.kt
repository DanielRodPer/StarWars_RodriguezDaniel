package com.dam.liststarwars.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.dam.liststarwars.ui.common.LocalOutlinedTextFieldStyle

@Composable
        /**
         * Función composable que crea un campo de texto personalizado con DataField.
         * Se cambiará su título según el parámetro tipo
         *
         * Hecho con el objetivo de poder reutilizarse con todos los campos posibles
         *
         * @author: Daniel Rodíguez Pérez
         * @version: 2.0
         */
fun DataField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorText: String = "",
    icon: Painter? = null,
    numeric: Boolean = false
) {
    val style = LocalOutlinedTextFieldStyle.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        singleLine = style.singleLine,
        isError = isError,
        label = { Text(label) },
        supportingText = {
            if (isError){
                Text(errorText)
            }
        },
        leadingIcon = if (icon != null) {
            {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(style.iconSize),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else null,
        keyboardOptions = if (numeric) {
            KeyboardOptions(keyboardType = KeyboardType.Number)
        } else {
            KeyboardOptions.Default
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DataFieldPreview() {
    var lect by remember { mutableStateOf("") }
    DataField("Personajes", onValueChange = {}, label = "")
}