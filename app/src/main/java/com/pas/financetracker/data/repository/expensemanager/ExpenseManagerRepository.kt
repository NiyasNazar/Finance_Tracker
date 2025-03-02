package com.pas.financetracker.data.repository.expensemanager


import com.pas.financetracker.data.local.room.dao.ExpenseDao
import com.pas.financetracker.data.model.ExpenseEntity
import com.pas.financetracker.data.model.category.Categorymaster
import com.pas.financetracker.data.repository.BaseRepository
import com.pas.financetracker.utils.AppConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ExpenseManagerRepository @Inject constructor(
    private val _expenseDao: ExpenseDao,
) : BaseRepository() {


    override fun getRoomDao(): ExpenseDao {
        return _expenseDao
    }

   suspend  fun saveCategory(dataSet: List<Categorymaster>) {


        _expenseDao.insertCategory(dataSet)
    }

    suspend fun getAllCategories(): List<Categorymaster> {
        return _expenseDao.getAllCategories()
    }



    suspend fun getallExpenses():Flow<List<ExpenseEntity>> {
        return _expenseDao.getAllExpenses()
    }

    suspend fun addExpense(expense: ExpenseEntity) {
        _expenseDao.insertExpense(expense)
    }

    suspend fun updateExpense(expense: ExpenseEntity) {
        _expenseDao.updateExpense(expense)
    }

    suspend fun deleteExpense(id: Int) {
        _expenseDao.deleteExpense(id)
    }


}
