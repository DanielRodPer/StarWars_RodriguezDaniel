package com.dam.liststarwars.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DrawerItem(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val route: String
)
