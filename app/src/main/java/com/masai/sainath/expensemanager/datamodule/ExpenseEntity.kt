package com.masai.sainath.expensemanager.datamodule

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
class ExpenseEntity(
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "wallet") var wallet: String,
    @ColumnInfo(name = "note") var note: String?,
    @ColumnInfo(name = "with") var with: String?,
    @ColumnInfo(name = "image") var image: String

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}