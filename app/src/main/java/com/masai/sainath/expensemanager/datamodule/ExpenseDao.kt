package com.masai.sainath.expensemanager.datamodule

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransaction(expenseEntity: ExpenseEntity)

    @Query("select*from expenses")
    fun getTransaction(): LiveData<List<ExpenseEntity>>

    @Update
    fun updateTransaction(expenseEntity: ExpenseEntity)

    @Delete
    fun deleteTransaction(expenseEntity: ExpenseEntity)

}