package com.pas.financetracker.data.repository

import com.pas.financetracker.data.local.room.dao.ExpenseDao


abstract class BaseRepository {

    abstract fun getRoomDao(): ExpenseDao

    protected var TAG: String = javaClass.simpleName


}