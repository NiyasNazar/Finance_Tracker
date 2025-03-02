package com.pas.financetracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import com.pas.financetracker.databinding.ActivityMainBinding
import com.pas.financetracker.ui.base.BaseActivity
import com.pas.financetracker.ui.expense_manager.ExpenseManagerActivity
import com.pas.financetracker.ui.expense_manager.ExpenseManagerViewModel
import com.pas.financetracker.ui.spending_break_down.SpendingViewByCategoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : BaseActivity<ExpenseManagerViewModel, ActivityMainBinding>() {
    override fun getLayout() = R.layout.activity_main


    override fun getViewModelType(): Class<ExpenseManagerViewModel> {
        return ExpenseManagerViewModel::class.java
    }


    override fun bindViews() {
        onClicks()
    }

    private fun onClicks() {



        binding.btnLogExpense.setOnClickListener {
            startActivity(Intent(this, ExpenseManagerActivity::class.java))
        }

        binding.btnSetBudget.setOnClickListener {
           // startActivity(Intent(this, SetBudgetActivity::class.java))
        }

        binding.btnSpendingAnalysis.setOnClickListener {
            startActivity(Intent(this, SpendingViewByCategoryActivity::class.java))
        }
    }

    override fun trackSessionData(): LiveData<Boolean>? {
     return null
    }
}

