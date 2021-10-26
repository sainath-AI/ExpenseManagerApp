package com.masai.sainath.expensemanager.repository

import androidx.lifecycle.LiveData
import com.masai.sainath.expensemanager.datamodule.ExpenseDao
import com.masai.sainath.expensemanager.datamodule.ExpenseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseRepository(private val expenseDao: ExpenseDao) {
     fun addTransaction(expenseEntity: ExpenseEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            expenseDao.addTransaction(expenseEntity)
        }
    }

    fun getTransaction(): LiveData<List<ExpenseEntity>> {
        return expenseDao.getTransaction()
    }

    fun updateTransaction(expenseEntity: ExpenseEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            expenseDao.updateTransaction(expenseEntity)
        }
    }

    fun deleteTransaction(expenseEntity: ExpenseEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            expenseDao.deleteTransaction(expenseEntity)
        }
    }
}