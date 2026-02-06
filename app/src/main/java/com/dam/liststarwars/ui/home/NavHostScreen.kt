package com.dam.liststarwars.ui.home

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dam.liststarwars.ui.common.BaseTopAppBarState
import com.dam.liststarwars.ui.common.FloatingActionButtonState

object Routes {
    const val LISTFILMS = "listFilms"
    const val ADDFILMS = "addFilms"
    const val EDITFILMS = "editFilms"
    const val NODATA = "nodata"
    const val ABOUTUS = "aboutus"
    const val LOADING = "loading"

}

/**
 * Nav host screen
 * Se define el contenerdor del grafo de navegación
 *
 * @param navController
 */
@Composable
fun NavHostScreen(
    navController: NavHostController,
    modifier: Modifier,
    onConfigureTopBar: (BaseTopAppBarState) -> Unit,
    onOpenDrawer: () -> Unit,
    onConfigureFab: (FloatingActionButtonState) -> Unit,
    onEnableDrawerGestures: (Boolean) -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = FilmRoutes.FILM_GRAPH,
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = tween(1000)) },
        exitTransition = { fadeOut(animationSpec = tween(1000)) }
    ) {
        filmGraph(
            navController = navController,
            modifier = modifier,
            onConfigureTopBar = onConfigureTopBar,
            onOpenDrawer = onOpenDrawer,
            onConfigureFab = onConfigureFab,
            onEnableDrawerGestures = onEnableDrawerGestures,
            onShowSnackbar = onShowSnackbar
        )

        /*
        composable(Routes.LISTFILMS) {
            onConfigureFab(
                FloatingActionButtonState(
                    visible = true,
                    onClick =  {navController.navigate(Routes.ADDFILMS)}
                )
            )
            ListScreen(
                modifier,
                onAdd = { navController.navigate(Routes.ADDFILMS) },
                onEdit = { film ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("film", film)
                    navController.navigate(Routes.EDITFILMS)
                },
                onAboutUs = { navController.navigate(Routes.ABOUTUS) },
                onListClick = { navController.navigate(Routes.LISTFILMS) },
                onConfigureTopBar = onConfigureTopBar,
                onOpenDrawer = onOpenDrawer,
                tipo = ListType.FILM,
            )
        }

        composable(Routes.ADDFILMS) {
            onConfigureFab(FloatingActionButtonState(visible = false))
            FilmAddScreen(
                modifier,
                { navController.popBackStack() },
                onConfigureTopBar = onConfigureTopBar,
            )
        }

        composable(Routes.EDITFILMS) {
            onConfigureFab(FloatingActionButtonState(visible = false))
            val film = navController.previousBackStackEntry?.savedStateHandle?.get<Film>("film")
            FilmEditScreen(
                modifier,
                film,
                { navController.popBackStack() },
                onConfigureTopBar = onConfigureTopBar,
            )
        }


         */
    }
}