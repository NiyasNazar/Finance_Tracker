package com.pas.financetracker.ui.expense_manager.add_expense

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import com.acube.itms.utils.date_picker.DatePickerDialogFragment
import com.pas.financetracker.R
import com.pas.financetracker.databinding.ActivityExpenseManagerBinding
import com.pas.financetracker.ui.base.BaseActivity
import com.pas.financetracker.ui.expense_manager.ExpenseManagerViewModel

class AddExpenseActivity :
    BaseActivity<ExpenseManagerViewModel, ActivityExpenseManagerBinding>() {
    override fun getLayout() = R.layout.activity_add_expense
    override fun getViewModelType() = ExpenseManagerViewModel::class.java
    override fun bindViews() {

    }

    override fun trackSessionData(): LiveData<Boolean>? {
       return null
    }

}