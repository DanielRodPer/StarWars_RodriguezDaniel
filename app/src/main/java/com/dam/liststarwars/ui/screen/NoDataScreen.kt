package com.dam.liststarwars.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dam.liststarwars.ui.common.BaseTopAppBarState
import com.dam.liststarwars.ui.common.FloatingActionButtonState

@Composable
fun NoDataScreen(
    onConfigureTopBar: (BaseTopAppBarState) -> Unit,
    onEnableDrawerGestures: (Boolean) -> Unit,
    onConfigureFab: (FloatingActionButtonState) -> Unit,
    onOpenDrawer: () -> Unit,
    onAdd: () -> Unit,
) {
    LaunchedEffect(Unit) {
        onEnableDrawerGestures(true)
        onConfigureTopBar(BaseTopAppBarState("Sin datos de películas...", upAction = onOpenDrawer))
        onConfigureFab(FloatingActionButtonState(visible = true, onClick = onAdd))
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No hay datos",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
