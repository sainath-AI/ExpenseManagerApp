package com.masai.sainath.expensemanager.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.masai.sainath.expensemanager.R
import com.masai.sainath.expensemanager.datamodule.ExpenseDatabase
import com.masai.sainath.expensemanager.datamodule.ExpenseEntity
import com.masai.sainath.expensemanager.repository.ExpenseRepository
import com.masai.sainath.expensemanager.viewmodels.ExpenseViewModel
import com.masai.sainath.expensemanager.viewmodels.ExpenseViewModelFactory
import kotlinx.android.synthetic.main.activity_show_transaction.*

class ShowTransaction : AppCompatActivity() {


    private lateinit var viewModel: ExpenseViewModel

    var id: Int? = null


    private val transactionDao by lazy {
        val roomDatabase = ExpenseDatabase.getDatabase(this)
        roomDatabase.getExpenseDao()
    }
    private val repository by lazy {
        ExpenseRepository(transactionDao)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_transaction)
        id = intent.getIntExtra("id", 0)


        val viewModelFactory = ExpenseViewModelFactory(repository)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ExpenseViewModel::class.java)
        settingData()
        initListeners()
    }
    private fun settingData() {


        val amount = intent.getIntExtra("amount", 0)
        val category = intent.getStringExtra("cat")
        val note = intent.getStringExtra("note")
        val date = intent.getStringExtra("date")
        val wallet = intent.getStringExtra("wallet")
        val with = intent.getStringExtra("with")
        Glide.with(ivCategoryImageShow).load(intent.getStringExtra("image")).into(ivCategoryImageShow)

        tvDisplayAmount.text = amount.toString()
        tvDisplayCategory.text = category
        tvDisplayNotes.text = note
        tvDisplayDate.text = date
        tvDisplayWallet.text = wallet
        tvDisplayWith.text = with

    }

    private fun initListeners() {
        btnCloseDisplayTransaction.setOnClickListener {
            finish()
        }

        tvDelete.setOnClickListener {
            val transactionEntity = ExpenseEntity(0, "", "", "", "", "","")
            transactionEntity.id = id
            viewModel.deleteTransaction(transactionEntity)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        tvEdit.setOnClickListener {
            val intent = Intent(this, EditTransaction::class.java)
            intent.putExtra("identity", id)
            startActivity(intent)
            finish()
        }
    }
}