package com.masai.sainath.expensemanager.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneylover.views.recyclerviews.OnItemClickListener
import com.masai.sainath.expensemanager.R
import com.masai.sainath.expensemanager.datamodule.ExpenseDatabase
import com.masai.sainath.expensemanager.datamodule.ExpenseEntity
import com.masai.sainath.expensemanager.repository.ExpenseRepository
import com.masai.sainath.expensemanager.viewmodels.ExpenseViewModel
import com.masai.sainath.expensemanager.viewmodels.ExpenseViewModelFactory
import com.masai.sainath.expensemanager.views.recyclerviews.TransactionAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var viewModel: ExpenseViewModel
    lateinit var transactionAdapter: TransactionAdapter
    private val expenseDao by lazy {
        val roomDatabase = ExpenseDatabase.getDatabase(applicationContext)
        roomDatabase.getExpenseDao()
    }
    val repository by lazy {
        ExpenseRepository(expenseDao)
    }
    var expenseList = mutableListOf<ExpenseEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerData()


        val viewModelFactory = ExpenseViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ExpenseViewModel::class.java)

        viewModel.getTransaction().observe(this, Observer {
            expenseList.clear()
            expenseList.addAll(it)
            transactionAdapter.notifyDataSetChanged()
        })

        newNotes.setOnClickListener {
            val intent = Intent(this,AddActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setRecyclerData() {
        transactionAdapter = TransactionAdapter(expenseList, this)
        transactionRecyclerView.layoutManager = LinearLayoutManager(this)
        transactionRecyclerView.adapter = transactionAdapter
    }


    override fun onItemClick(expenseEntity: ExpenseEntity) {
        val intent = Intent(this, ShowTransaction::class.java)
        intent.putExtra("id", expenseEntity.id)
        intent.putExtra("amount", expenseEntity.amount)
        intent.putExtra("cat", expenseEntity.category)
        intent.putExtra("note", expenseEntity.note)
        intent.putExtra("date", expenseEntity.date)
        intent.putExtra("wallet", expenseEntity.wallet)
        intent.putExtra("with", expenseEntity.with)
        intent.putExtra("image",expenseEntity.image)
        startActivity(intent)
    }


}