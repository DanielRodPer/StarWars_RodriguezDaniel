package com.dam.liststarwars.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.dam.liststarwars.ui.common.LocalDimensions


@Composable
fun AlertDialogOkCancel(
    title: String = "Confirmación",
    text: String,
    okText: String = "Aceptar",
    cancelText: String = "",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val dimensiones = LocalDimensions.current

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(dimensiones.large * 2)
                )
                Spacer(modifier = Modifier.width(dimensiones.standard))
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Text(
                text = text,
                fontSize = 17.sp,
                lineHeight = 22.sp
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = dimensiones.medium,

        // BOTONES OK / CANCELAR
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(end = dimensiones.medium, bottom = dimensiones.medium)
            ) {
                Text(
                    text = okText,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        //He añadido esta comprobación para poder usarlo tambén en los titulos duplicados
        dismissButton = if(cancelText.isNotEmpty()){
            {
                OutlinedButton(
                    onClick = onDismiss,
                    border = BorderStroke(dimensiones.tiny, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(start = dimensiones.medium, bottom = dimensiones.medium)
                ) {
                    Text(
                        text = cancelText,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            {}
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAlertDialogOkCancel() {
    MaterialTheme {
        AlertDialogOkCancel(
            title = "¿Confirmar acción?",
            text = "Esta acción no se puede deshacer.\n¿Deseas continuar?",
            okText = "Aceptar",
            cancelText = "Cancelar",
            onConfirm = {},
            onDismiss = {}
        )
    }
}

