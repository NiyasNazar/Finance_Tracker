package com.pas.financetracker.ui.expense_manager


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pas.financetracker.R
import com.pas.financetracker.data.model.ExpenseEntity
import com.pas.financetracker.data.model.category.Categorymaster
import com.pas.financetracker.data.repository.expensemanager.ExpenseManagerRepository
import com.pas.financetracker.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseManagerViewModel @Inject constructor(
    private val _repository: ExpenseManagerRepository, private val application: Application
) :
    BaseViewModel() {
    var pickupLocation: String? = null
    var dropoffLocation: String? = null
    var pickupDate: String? = null
    var dropoffDate: String? = null


    override fun getRepository() = _repository
    private val _weeklyexpenses = MutableLiveData<List<ExpenseEntity>>()
    val weeklyexpenses: LiveData<List<ExpenseEntity>> = _weeklyexpenses

    private val _monthlyexpenses = MutableLiveData<List<ExpenseEntity>>()
    val monthlyexpenses: LiveData<List<ExpenseEntity>> = _monthlyexpenses


    val expenseLiveData: LiveData<List<ExpenseEntity>>
        get() = _expenseLiveData
    private val _expenseLiveData = MutableLiveData<List<ExpenseEntity>>()

    private val _categories = MutableLiveData<List<Categorymaster>>()
    val categories: LiveData<List<Categorymaster>> get() = _categories

    init {
        viewModelScope.launch {
            insertDefaultCategories()
        }
    }

    suspend fun insertDefaultCategories() {
        val categories = listOf(
            Categorymaster(categoryName = "Food", categoryType = "Expense", categoryID = 1),
            Categorymaster(categoryName = "Transport", categoryType = "Expense", categoryID = 2),
            Categorymaster(
                categoryName = "Entertainment",
                categoryType = "Expense",
                categoryID = 3
            ),
            Categorymaster(categoryName = "Bills", categoryType = "Expense", categoryID = 4),
            Categorymaster(categoryName = "Miscellaneous", categoryType = "Expense", categoryID = 5)
        )
        _repository.saveCategory(categories)
    }

    fun fetchCategories() {
        viewModelScope.launch {
            val categoryList = _repository.getAllCategories()
            _categories.postValue(categoryList)
        }
    }

    fun getallExpenses() {
        viewModelScope.launch {
            _repository.getallExpenses().collect {
                if (it.isNotEmpty()) {
                    _expenseLiveData.value = it
                }
            }
        }

    }

    fun addExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            _repository.addExpense(expense)
        }
    }

    fun updateExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            _repository.updateExpense(expense)
        }
    }

    fun deleteExpense(id: Int) {
        viewModelScope.launch {
            _repository.deleteExpense(id)
        }
    }

    fun loadWeeklyExpenses() {

    }

    fun loadMonthlyExpenses() {

    }

}