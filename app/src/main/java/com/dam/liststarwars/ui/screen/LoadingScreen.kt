package com.dam.liststarwars.ui.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dam.liststarwars.R
import com.dam.liststarwars.ui.common.BaseTopAppBarState
import com.dam.liststarwars.ui.common.FloatingActionButtonState
import com.dam.liststarwars.ui.common.LocalDimensions

@Composable
fun LoadingScreen(
    onConfigureTopBar: (BaseTopAppBarState) -> Unit,
    onEnableDrawerGestures: (Boolean) -> Unit,
    onConfigureFab: (FloatingActionButtonState) -> Unit
) {
    val dimensiones = LocalDimensions.current

    LaunchedEffect(Unit) {
        onEnableDrawerGestures(true)
        onConfigureFab(FloatingActionButtonState(visible = false))
        onConfigureTopBar(BaseTopAppBarState("Cargando..."))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.abus_app_name),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(Font(R.font.starwars)),
                shadow = androidx.compose.ui.graphics.Shadow(
                    color = Color.Black,
                    offset = androidx.compose.ui.geometry.Offset(4f, 4f),
                    blurRadius = 8f
                )
            ),
            color = if (!isSystemInDarkTheme()) Color.Red else Color.Yellow,
        )
        Spacer(Modifier.height(dimensiones.huge))
        CircularProgressIndicator(
            modifier = Modifier.size(dimensiones.huge),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(dimensiones.extraLarge))

        Text(
            text = stringResource(R.string.loading_screen),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LoadingScreenPreview(){
    LoadingScreen({}, {}, {})
}