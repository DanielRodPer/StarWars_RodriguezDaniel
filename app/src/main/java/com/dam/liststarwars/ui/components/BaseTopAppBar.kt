package com.dam.liststarwars.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dam.liststarwars.ui.common.Action
import com.dam.liststarwars.ui.common.BaseTopAppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopAppBar(state: BaseTopAppBarState) {
    val visible = state.actions.filter { it.isVisible }
    val notVisible = state.actions.filter { !it.isVisible }
    TopAppBar(
        title = {
            Text(
                text = state.title, modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        navigationIcon = {
            IconButton(onClick = state.upAction) {
                Icon(
                    imageVector = state.iconUpAction,
                    contentDescription = "",
                )
            }
        },
        actions = {
            if (visible.isNotEmpty()) visible.forEach {
                IconButton(onClick = it.onClick) {
                    when (it) {
                        is Action.ActionImageVector -> {
                            Icon(
                                imageVector = it.icon!!,
                                contentDescription = it.contentDescription,
                            )
                        }

                        is Action.ActionPainter -> {
                            Icon(
                                painter = it.icon!!,
                                contentDescription = it.contentDescription,
                            )
                        }
                        is Action.ActionMenu -> {
                            var expanded by remember { mutableStateOf(false) }
                            Box {
                                IconButton(onClick = { expanded = true }) {
                                    Icon(
                                        imageVector = it.icon,
                                        contentDescription = it.contentDescription
                                    )
                                }
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    it.items.forEach { item ->
                                        DropdownMenuItem(
                                            text = { Text(text = item.name) },
                                            onClick = {
                                                expanded = false
                                                item.onClick()
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (notVisible.isNotEmpty()) TopAppBarDropDownMenu(state.actions.filter { !it.isVisible })
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}