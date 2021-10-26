package com.masai.sainath.expensemanager.views.recyclerviews

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moneylover.views.recyclerviews.OnItemClickListener
import com.masai.sainath.expensemanager.datamodule.ExpenseEntity
import kotlinx.android.synthetic.main.layout_for_transaction.view.*

class TransactionViewHolder(itemView: View, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.ViewHolder(itemView) {

    fun setData(expenseEntity: ExpenseEntity) {
        itemView.apply {
            tvTransactionDateToDisplay.text = expenseEntity.date
            tvTransactionCategory.text = expenseEntity.category
            tvTransactionAmount.text = expenseEntity.amount.toString()
            Glide.with(ivCategoryImage.context).load(expenseEntity.image).into(ivCategoryImage)


            rlUpdateTransaction.setOnClickListener {
                onItemClickListener.onItemClick(expenseEntity)
            }
        }
    }
}