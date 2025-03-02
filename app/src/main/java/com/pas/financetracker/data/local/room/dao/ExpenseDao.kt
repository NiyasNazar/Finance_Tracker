package com.pas.financetracker.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update

import com.pas.financetracker.data.model.ExpenseEntity
import com.pas.financetracker.data.model.category.Categorymaster
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(entityList: List<Categorymaster>)

    @Query("SELECT * FROM category_master_table")
    suspend fun getAllCategories(): List<Categorymaster>


    @Insert(onConflict = REPLACE)
    suspend fun insertExpense(entity: ExpenseEntity)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Query("delete from expense_store_table where id=:expenseId")
    suspend fun deleteExpense(expenseId: Int)

    @Query("SELECT * FROM expense_store_table ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expense_store_table WHERE date >= strftime('%Y-%m-%d', 'now', '-7 days')")
    suspend fun getWeeklyExpenses(): List<ExpenseEntity>

    @Query("SELECT * FROM expense_store_table WHERE date >= strftime('%Y-%m-%d', 'now', '-30 days')")
    suspend fun getMonthlyExpenses(): List<ExpenseEntity>

}


