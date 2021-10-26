package com.example.moneylover.views.recyclerviews

import com.masai.sainath.expensemanager.datamodule.ExpenseEntity

interface OnItemClickListener {
    fun onItemClick(expenseEntity: ExpenseEntity)
}