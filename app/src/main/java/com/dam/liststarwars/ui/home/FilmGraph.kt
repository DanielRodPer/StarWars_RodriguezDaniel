package com.dam.liststarwars.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dam.liststarwars.data.model.Film
import com.dam.liststarwars.ui.common.BaseTopAppBarState
import com.dam.liststarwars.ui.common.FloatingActionButtonState
import com.dam.liststarwars.ui.screen.AboutUsScreen
import com.dam.liststarwars.ui.screen.LoadingScreen
import com.dam.liststarwars.ui.screen.NoDataScreen
import com.dam.liststarwars.ui.screen.film.FilmAddScreen
import com.dam.liststarwars.ui.screen.film.FilmEditScreen
import com.dam.liststarwars.ui.screen.list.ListScreen

object FilmRoutes {
    const val FILM_GRAPH = "filmGraph"
}

fun NavGraphBuilder.filmGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onConfigureTopBar: (BaseTopAppBarState) -> Unit,
    onOpenDrawer: () -> Unit,
    onConfigureFab: (FloatingActionButtonState) -> Unit,
    onEnableDrawerGestures: (Boolean) -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    navigation(
        startDestination = Routes.LISTFILMS,
        route = FilmRoutes.FILM_GRAPH
    ) {
        composable(Routes.LISTFILMS) {
            onConfigureFab(
                FloatingActionButtonState(
                    visible = true,
                    icon = Icons.Default.Add,
                    onClick = { navController.navigate(Routes.ADDFILMS) }
                )
            )
            ListScreen(
                modifier = modifier,
                onAdd = { navController.navigate(Routes.ADDFILMS) },
                onEdit = { film ->
                    // Guardamos el objeto en el SavedStateHandle del entry actual antes de navegar
                    navController.currentBackStackEntry?.savedStateHandle?.set("film", film)
                    navController.navigate(Routes.EDITFILMS)
                },
                onAboutUs = { navController.navigate(Routes.ABOUTUS) },
                onListClick = { navController.navigate(Routes.LISTFILMS) }, //Esto recarga la misma lista
                onConfigureTopBar = onConfigureTopBar,
                onOpenDrawer = onOpenDrawer,
                onEnableDrawerGestures = onEnableDrawerGestures,
                onShowSnackbar = onShowSnackbar,
                onConfigureFab = onConfigureFab
            )
        }

        composable(Routes.ADDFILMS) {
            FilmAddScreen(
                goToBack = { navController.popBackStack() },
                onConfigureTopBar = onConfigureTopBar,
                onEnableDrawerGestures = onEnableDrawerGestures,
                onConfigureFab = onConfigureFab
            )
        }

        composable(Routes.EDITFILMS) {
            // Recuperamos el objeto desde el entry anterior
            val film = navController.previousBackStackEntry?.savedStateHandle?.get<Film>("film")
            FilmEditScreen(
                film = film,
                goToBack = { navController.popBackStack() },
                onConfigureTopBar = onConfigureTopBar,
                onEnableDrawerGestures = onEnableDrawerGestures,
                onConfigureFab = onConfigureFab
            )
        }

        composable(Routes.LOADING) {
            LoadingScreen(
                onConfigureTopBar = onConfigureTopBar,
                onEnableDrawerGestures = onEnableDrawerGestures,
                onConfigureFab = onConfigureFab
            )
        }

        composable(Routes.NODATA) {
            NoDataScreen(
                onConfigureTopBar = onConfigureTopBar,
                onEnableDrawerGestures = onEnableDrawerGestures,
                onConfigureFab = onConfigureFab,
                onOpenDrawer = onOpenDrawer,
                onAdd = {navController.navigate(Routes.ADDFILMS)}
            )
        }

        composable(Routes.ABOUTUS) {
            onConfigureFab(FloatingActionButtonState(visible = false))
            AboutUsScreen(
                onConfigureTopBar = onConfigureTopBar,
                onGoTOBack = { navController.popBackStack() },
                onEnableDrawerGestures = onEnableDrawerGestures
            )
        }
    }
}