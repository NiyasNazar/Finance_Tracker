package com.pas.financetracker.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

import com.pas.financetracker.data.local.room.dao.ExpenseDao
import com.pas.financetracker.data.model.ExpenseEntity
import com.pas.financetracker.data.model.category.Categorymaster

@Database(
    entities = [
        ExpenseEntity::class,
        Categorymaster::class

    ], version = 1, exportSchema = false
)

abstract class AppDataBase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao


}