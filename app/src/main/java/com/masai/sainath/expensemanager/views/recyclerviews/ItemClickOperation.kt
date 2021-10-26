package com.example.moneylover.views.recyclerviews

import com.masai.sainath.expensemanager.datamodule.ExpenseEntity

interface ItemClickOperation {
    fun onEditListener(expenseEntity: ExpenseEntity)
    fun onDeleteListener(expenseEntity: ExpenseEntity)
}