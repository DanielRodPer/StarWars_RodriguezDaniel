package com.dam.liststarwars.data.di

import android.content.Context
import android.content.res.Resources
import com.dam.liststarwars.data.StarWarsDatabase
import com.dam.liststarwars.data.dao.FilmDAO
import com.dam.liststarwars.data.dao.PersonDao
import com.dam.liststarwars.data.dao.PlanetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context): Resources{
        return context.resources
    }

    @Provides
    @Singleton
    fun provideStarWarsDatabase(@ApplicationContext context: Context): StarWarsDatabase{
        return StarWarsDatabase.getDatabase(context)
        //El problema es que la bd es una clase abstracta por lo que tampoco tiene constructor por lo que también tenemos que proveer una instancia
        //El metodo getDatabase , devuelve una instancia de la base de datos
    }

    @Provides
    @Singleton
    fun provideFilmDao(starWarsDatabase: StarWarsDatabase): FilmDAO{
        return starWarsDatabase.getFilmDao()    //El dao al ser una interfaz no lo podemos instanciar entonces lo lo podemos proveer
        //Para poder hacerlo lo tomaremos de la base de datos que ya tiene una instancia de él

        //El problema es que la bd es una clase abstracta por lo que tampoco tiene constructor por lo que también tenemos que proveer una instancia
    }

    @Provides
    @Singleton
    fun providePersonsDao(starWarsDatabase: StarWarsDatabase): PersonDao {
        return starWarsDatabase.getPersonDAO()
    }

    @Provides
    @Singleton
    fun providePlanetsDao(starWarsDatabase: StarWarsDatabase): PlanetDao {
        return starWarsDatabase.getPlanetDAO()
    }
}