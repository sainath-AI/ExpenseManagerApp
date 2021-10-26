package com.masai.sainath.expensemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.masai.sainath.expensemanager.datamodule.ExpenseEntity
import com.masai.sainath.expensemanager.repository.ExpenseRepository

class ExpenseViewModel(private val repository: ExpenseRepository): ViewModel() {
     fun addTransaction(expenseEntity: ExpenseEntity) {
        repository.addTransaction(expenseEntity)
    }

    fun getTransaction(): LiveData<List<ExpenseEntity>> {
        return repository.getTransaction()
    }

    fun updateTransaction(expenseEntity: ExpenseEntity) {
        repository.updateTransaction(expenseEntity)
    }

    fun deleteTransaction(expenseEntity: ExpenseEntity) {
        repository.deleteTransaction(expenseEntity)
    }
}