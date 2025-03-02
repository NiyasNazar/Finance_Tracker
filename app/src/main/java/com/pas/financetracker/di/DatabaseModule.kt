package com.pas.financetracker.di

import android.content.Context
import androidx.room.Room
import com.pas.financetracker.data.local.room.AppDataBase
import com.pas.financetracker.data.local.room.dao.ExpenseDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {




    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "expensemanager_db"
        ).fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun provideDao(dataBase: AppDataBase): ExpenseDao {
        return dataBase.expenseDao()
    }



}