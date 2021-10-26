package com.masai.sainath.expensemanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masai.sainath.expensemanager.repository.ExpenseRepository

class ExpenseViewModelFactory(private val repository: ExpenseRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExpenseViewModel(repository) as T
    }

}