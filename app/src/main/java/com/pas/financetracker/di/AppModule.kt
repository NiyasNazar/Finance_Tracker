package com.pas.financetracker.di

import android.content.Context
import com.pas.financetracker.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }

    @Provides
    fun provideContext(@ApplicationContext app: Context): Context {
        return app
    }

}