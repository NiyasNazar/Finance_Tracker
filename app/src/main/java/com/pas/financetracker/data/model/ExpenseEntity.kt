package com.pas.financetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_store_table")

class ExpenseEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String?,
    val amount: Double?,
    val date: String?,
    val notes: String?,
    val receiptImage: String?
)

