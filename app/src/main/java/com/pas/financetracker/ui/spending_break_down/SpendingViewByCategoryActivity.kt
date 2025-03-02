package com.pas.financetracker.ui.spending_break_down

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.acube.itms.utils.date_picker.DatePickerDialogFragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.pas.financetracker.R
import com.pas.financetracker.data.model.ExpenseEntity
import com.pas.financetracker.databinding.ActivityExpenseManagerBinding
import com.pas.financetracker.databinding.ActivitySpendingViewBinding
import com.pas.financetracker.ui.base.BaseActivity
import com.pas.financetracker.ui.expense_manager.ExpenseManagerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint

class SpendingViewByCategoryActivity :
    BaseActivity<ExpenseManagerViewModel, ActivitySpendingViewBinding>() {
    companion object {
        fun start(context: Context) {
            val starter = Intent(
                context, SpendingViewByCategoryActivity::class.java
            )
            context.startActivity(starter)
        }
    }

    override fun getLayout() = R.layout.activity_spending_view
    override fun getViewModelType(): Class<ExpenseManagerViewModel> {
        return ExpenseManagerViewModel::class.java
    }

    override fun bindViews() {

        setUpToolBar(
            binding.toolbarLayout.toolbar,
            "Spending",
            true
        )
        binding.toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnCategory -> {
                        Log.d("Toggle", "Category button clicked")
                        showPiechart()
                    }

                    R.id.btnMonthly -> {
                        Log.d("Toggle", "Monthly button clicked")
                        showBarchart()
                    }
                }
            }
        }


        viewModel.getallExpenses()
        observeData()


    }

    private fun showBarchart() {
        Log.d("showchart", "showBarchart: ")
        binding.barChart.visibility= View.VISIBLE
        binding.pieChart.visibility= View.GONE

    }

    private fun showPiechart() {
        Log.d("showchart", "showPiechart: ")

        binding.barChart.visibility= View.GONE
        binding.pieChart.visibility= View.VISIBLE
    }

    private fun observeData() {
        viewModel.expenseLiveData.observe(this) {
            setupPieChart(it)
            setupBarChart(it)
        }



    }


    private fun setupPieChart(expenses: List<ExpenseEntity>) {
        val categoryMap = expenses.groupBy { it.category }
            .mapValues { entry ->
                entry.value.sumOf {
                    it.amount ?: 0.0
                }
            }

        val entries = ArrayList<PieEntry>()
        categoryMap.forEach { (category, amount) ->
            entries.add(PieEntry(amount.toFloat(), category))
        }

        val dataSet = PieDataSet(entries, "Expense Breakdown")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 14f

        val data = PieData(dataSet)
        binding.pieChart.data = data
        binding.pieChart.description.isEnabled = false

        binding.pieChart.isEnabled = false
        binding.pieChart.animateY(1000)
        binding.pieChart.invalidate()
    }


    override fun trackSessionData(): LiveData<Boolean>? {
        return null
    }

    private fun setupBarChart(expenses: List<ExpenseEntity>) {
        val calendar = Calendar.getInstance()
        val weeklyExpenses = mutableMapOf<String, Float>()

        for (i in 0..6) {
            calendar.add(Calendar.DAY_OF_YEAR, -i)
            val dateString = SimpleDateFormat("dd MMM", Locale.getDefault()).format(calendar.time)
            weeklyExpenses[dateString] = 0f
        }

        expenses.forEach { expense ->
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
            val parsedDate: Date = inputFormat.parse(expense.date) ?: Date()  // Parse String to Date
            val dateString = outputFormat.format(parsedDate)  // Format to "dd MMM"


            if (weeklyExpenses.containsKey(dateString)) {
                weeklyExpenses[dateString] = weeklyExpenses[dateString]!! + expense.amount!!.toFloat()
            }
        }

        val entries = ArrayList<BarEntry>()
        weeklyExpenses.entries.forEachIndexed { index, entry ->
            entries.add(BarEntry(index.toFloat(), entry.value))
        }

        val dataSet = BarDataSet(entries, "Weekly Spending")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 14f

        val barData = BarData(dataSet)
        binding.barChart.data = barData
        binding.barChart.xAxis.valueFormatter =
            IndexAxisValueFormatter(weeklyExpenses.keys.toList())
        binding.barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.barChart.xAxis.setDrawGridLines(false)
        binding.barChart.description.isEnabled = false
        binding.barChart.animateY(1000)
        binding.barChart.invalidate()
    }

}