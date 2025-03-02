package com.pas.financetracker.data.model.category

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "category_master_table")

class Categorymaster(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryName: String? = null,
    val categoryType: String? = null,
    val categoryID: Int? = null,
){
    override fun toString(): String {
        return categoryName.toString()
    }
}