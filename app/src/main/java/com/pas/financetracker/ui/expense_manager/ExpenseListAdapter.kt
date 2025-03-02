package com.pas.financetracker.ui.expense_manager

import com.pas.financetracker.R
import com.pas.financetracker.data.model.ExpenseEntity
import com.pas.financetracker.ui.base.BaseListAdapter

class ExpenseListAdapter(
    val data: List<ExpenseEntity>, val _listener: AdapterClickListener?
) : BaseListAdapter() {
    override fun getLayoutIdForType(viewType: Int) = R.layout.list_item_expense

    override fun getDataAtPosition(position: Int) = data[position]

    override fun getClickListener() = _listener

    override fun getItemCount() = data.size


    interface AdapterClickListener {
        fun onItemSelectedEdit(data: ExpenseEntity, position: Int)
        fun onItemClick(data: ExpenseEntity, position: Int)
        fun onItemDelete(data: ExpenseEntity, position: Int)
        fun onShowStatusMessage(message: String?)
    }

}