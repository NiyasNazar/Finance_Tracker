package com.pas.financetracker.ui.expense_manager

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.acube.itms.utils.date_picker.DatePickerDialogFragment
import com.pas.financetracker.R
import com.pas.financetracker.data.model.ExpenseEntity
import com.pas.financetracker.databinding.ActivityExpenseManagerBinding
import com.pas.financetracker.ui.base.BaseActivity
import com.pas.financetracker.ui.spending_break_down.SpendingViewByCategoryActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint

class ExpenseManagerActivity :
    BaseActivity<ExpenseManagerViewModel, ActivityExpenseManagerBinding>() {
    private lateinit var listAdapter: ExpenseListAdapter

    override fun getLayout() = R.layout.activity_expense_manager
    override fun getViewModelType(): Class<ExpenseManagerViewModel> {
        return ExpenseManagerViewModel::class.java
    }

    override fun bindViews() {
        setUpToolBar(
            binding.toolbarLayout.toolbar,
            getString(R.string.log_expense),
            false
        )
        binding.viewModel = viewModel
        onClicks()
        viewModel.getallExpenses()
        observeData()


    }

    private fun observeData() {
        viewModel.expenseLiveData.observe(this) {
            setUpAdapter(it)
        }
    }

    private fun setUpAdapter(dataSet: List<ExpenseEntity>) {
        listAdapter = ExpenseListAdapter(dataSet, mCallback)
        binding.rvExpenses.apply {
            layoutManager = LinearLayoutManager(this@ExpenseManagerActivity)
            adapter = listAdapter
        }
    }

    private val mCallback = object : ExpenseListAdapter.AdapterClickListener {
        override fun onItemSelectedEdit(data: ExpenseEntity, position: Int) {
        }

        override fun onItemClick(data: ExpenseEntity, position: Int) {
        }

        override fun onItemDelete(data: ExpenseEntity, position: Int) {
            showDeleteConfirmationDialog(this@ExpenseManagerActivity) {
                viewModel.deleteExpense(data.id) // Call delete function
            }

        }

        override fun onShowStatusMessage(message: String?) {
        }

    }

    fun showDeleteConfirmationDialog(context: Context, onConfirm: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Delete Expense?")
            .setMessage("Are you sure you want to delete this expense? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                onConfirm()
            }
            .setNegativeButton("Cancel", null)
            .setCancelable(false)
            .show()
    }


    private fun onClicks() {
        binding.fabAddExpense.setOnClickListener() {
            showAddExpenseBottomSheet()

        }
    }

    override fun trackSessionData(): LiveData<Boolean>? {
        return null
    }

    private fun showAddExpenseBottomSheet() {

        val fragment = AddExpenseBottomSheet(object :
            AddExpenseBottomSheet.CallBack {
            override fun onExpenseSaved(

            ) {


            }
        }
        )
        fragment.show(supportFragmentManager, AddExpenseBottomSheet::class.java.simpleName)
    }

}