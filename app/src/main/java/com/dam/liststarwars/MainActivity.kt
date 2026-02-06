package com.dam.liststarwars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.dam.liststarwars.ui.common.BaseTopAppBarState
import com.dam.liststarwars.ui.common.Dimensions
import com.dam.liststarwars.ui.common.FloatingActionButtonState
import com.dam.liststarwars.ui.common.LocalDimensions
import com.dam.liststarwars.ui.common.LocalOutlinedTextFieldStyle
import com.dam.liststarwars.ui.common.OutlinedTextFieldStyle
import com.dam.liststarwars.ui.components.BaseTopAppBar
import com.dam.liststarwars.ui.home.DrawerItem
import com.dam.liststarwars.ui.home.NavHostScreen
import com.dam.liststarwars.ui.home.Routes
import com.dam.liststarwars.ui.theme.ListStarWarsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            //--- 1. ESTADOS GLOBALES ---
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            var topBarState by remember { mutableStateOf(BaseTopAppBarState(upAction = {})) }
            var enableGesturesState by remember { mutableStateOf(false) }
            var fabState by remember { mutableStateOf(FloatingActionButtonState()) }

            val drawerItems = listOf(
                DrawerItem(
                    name = R.string.film,
                    icon = R.drawable.film,
                    route = Routes.LISTFILMS
                ),
            )

            ListStarWarsTheme {
                val estiloCampoTexto = OutlinedTextFieldStyle(
                    singleLine = true,
                    iconSize = LocalDimensions.current.extraLarge,
                    fillMaxWidth = true,
                    iconTint = MaterialTheme.colorScheme.primary
                )

                val dimensions = Dimensions()

                CompositionLocalProvider(
                    LocalOutlinedTextFieldStyle provides estiloCampoTexto,
                    LocalDimensions provides dimensions
                ) {

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        ModalNavigationDrawer(
                            drawerState = drawerState,
                            gesturesEnabled = enableGesturesState,
                            drawerContent = {
                                ModalDrawerSheet(
                                    drawerContainerColor = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.width(240.dp),
                                ) {
                                    val dimensiones = LocalDimensions.current

                                    Column(Modifier.fillMaxHeight()) {
                                        Spacer(Modifier.height(dimensiones.big))
                                        Text(
                                            text = stringResource(R.string.drawer_app_name),
                                            Modifier.align(
                                                Alignment.CenterHorizontally
                                            ),
                                            style = MaterialTheme.typography.headlineLarge.copy(
                                                fontSize = 36.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                fontFamily = FontFamily(Font(R.font.starwars)),
                                                shadow = androidx.compose.ui.graphics.Shadow(
                                                    color = Color.Black,
                                                    offset = androidx.compose.ui.geometry.Offset(
                                                        4f,
                                                        4f
                                                    ),
                                                    blurRadius = 8f
                                                )
                                            ),
                                            color = if (!isSystemInDarkTheme()) Color.Red else Color.Yellow,
                                        )
                                        Spacer(Modifier.height(dimensiones.extraLarge))

                                        // Cabecera del Drawer
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(color = MaterialTheme.colorScheme.primary),
                                        ) {
                                            Text(
                                                text = stringResource(R.string.drawer_title),
                                                modifier = Modifier
                                                    .padding(dimensiones.large)
                                                    .fillMaxWidth(),
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                color = if (!isSystemInDarkTheme()) Color.White else Color.Black,
                                            )
                                        }
                                        Spacer(Modifier.height(dimensiones.standard))
                                        Column(Modifier.weight(1f)) {
                                            drawerItems.forEach { item ->
                                                NavigationDrawerItem(
                                                    label = { Text(text = stringResource(id = item.name)) },
                                                    icon = {
                                                        Icon(
                                                            painter = painterResource(id = item.icon),
                                                            modifier = Modifier.size(dimensiones.extraLarge),
                                                            contentDescription = null
                                                        )
                                                    },
                                                    selected = false,
                                                    onClick = {
                                                        scope.launch {
                                                            drawerState.close()
                                                        }
                                                        navController.navigate(item.route)
                                                    },
                                                    modifier = Modifier
                                                        .padding(horizontal = dimensiones.medium)
                                                        .fillMaxWidth()
                                                )
                                            }
                                        }
                                        NavigationDrawerItem(
                                            label = { Text(stringResource(R.string.about_us)) },
                                            icon = {
                                                Icon(
                                                    Icons.Default.Face,
                                                    contentDescription = null
                                                )
                                            },
                                            selected = false,
                                            onClick = {
                                                scope.launch {
                                                    drawerState.close()
                                                }
                                                navController.navigate(Routes.ABOUTUS)
                                            },
                                            modifier = Modifier
                                                .padding(dimensiones.standard)
                                                .fillMaxWidth()
                                        )
                                    }

                                }
                            }
                        )
                        {
                            Scaffold(
                                floatingActionButton = {
                                    if (fabState.visible) {
                                        FloatingActionButton(
                                            onClick = fabState.onClick,
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            contentColor = MaterialTheme.colorScheme.background
                                        ) {
                                            Icon(
                                                imageVector = fabState.icon,
                                                contentDescription = "Añadir"
                                            )
                                        }
                                    }
                                },
                                topBar = { BaseTopAppBar(state = topBarState) },
                                snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                            ) { paddingValues ->
                                NavHostScreen(
                                    navController,
                                    modifier = Modifier.padding(paddingValues),
                                    onConfigureTopBar = { topBarState = it },
                                    onOpenDrawer = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    },
                                    onConfigureFab = { fabState = it },
                                    onEnableDrawerGestures = { enableGesturesState = it },
                                    onShowSnackbar = { mensaje ->
                                        scope.launch {
                                            snackbarHostState.showSnackbar(mensaje, )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}