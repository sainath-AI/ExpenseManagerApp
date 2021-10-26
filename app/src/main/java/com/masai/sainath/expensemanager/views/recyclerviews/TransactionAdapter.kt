package com.masai.sainath.expensemanager.views.recyclerviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneylover.views.recyclerviews.OnItemClickListener
import com.masai.sainath.expensemanager.R
import com.masai.sainath.expensemanager.datamodule.ExpenseEntity

class TransactionAdapter(
    private val expenseList: List<ExpenseEntity>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_for_transaction, parent, false)
        return TransactionViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val expenseEntity: ExpenseEntity = expenseList[position]
        holder.setData(expenseEntity)
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

}