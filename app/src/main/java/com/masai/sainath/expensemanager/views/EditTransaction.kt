package com.masai.sainath.expensemanager.views

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moneylover.views.recyclerviews.OnCategoryClickListener
import com.masai.sainath.expensemanager.R
import com.masai.sainath.expensemanager.databinding.DialogCustomListBinding
import com.masai.sainath.expensemanager.datamodule.ExpenseDatabase
import com.masai.sainath.expensemanager.datamodule.ExpenseEntity
import com.masai.sainath.expensemanager.repository.ExpenseRepository
import com.masai.sainath.expensemanager.viewmodels.ExpenseViewModel
import com.masai.sainath.expensemanager.viewmodels.ExpenseViewModelFactory
import com.masai.sainath.expensemanager.views.recyclerviews.CustomListItemAdapter
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_edit_transaction.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class EditTransaction : AppCompatActivity(), OnCategoryClickListener {
    private val expenseDao by lazy {
        val roomDatabase= ExpenseDatabase.getDatabase(applicationContext)
        roomDatabase.getExpenseDao()
    }

    val repository by lazy {
        ExpenseRepository(expenseDao)
    }

    private lateinit var  viewModel: ExpenseViewModel
    private lateinit var mCustomListDialog: Dialog
    private var mImagePath: String =""

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaction)
        val viewModelFactory = ExpenseViewModelFactory(repository)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ExpenseViewModel::class.java)


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)



        etSelectEditableDate.setOnClickListener {
            val dp =
                DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        etSelectEditableDate.setText(dayOfMonth.toString() + "/" + month.toString() + "/" + year.toString())
                    },
                    year,
                    month,
                    day
                )
            dp.show()
        }
        etSelectEditableCategory.setOnClickListener {
            customItemsListDialog(
                resources.getString(R.string.title_select_category),
                Constants.categories(),
                Constants.imageCategory(),
                Constants.CATEGORY
            )
        }
        etSelectEditableWallet.setOnClickListener {
            customItemsListDialog(
                resources.getString(R.string.title_select_wallet),
                Constants.wallets(),
                Constants.walletsImage(),
                Constants.WALLET
            )

        }

//        etSetEditableReminder.setOnClickListener {
//            val time = etSetEditableReminder.text.toString().toInt()
//            val intent = Intent(AlarmClock.ACTION_SET_ALARM)
//            intent.putExtra(AlarmClock.EXTRA_HOUR, time)
//            intent.putExtra(AlarmClock.EXTRA_MINUTES, 0)
//            startActivity(intent)
//        }


        ivEditableSelectFromGallery.setOnClickListener {
            val builder = AlertDialog.Builder(
                this
            )
            builder.setTitle("Wait a sec...")
            builder.setMessage("Free is great but Premium s better. \n  \nGo Premium o enjoy unlimited awesome features!")
            builder.setPositiveButton("GO PREMIUM", null)
            val alert: AlertDialog = builder.create()
            alert.show()
        }

        ivEditableClickPicture.setOnClickListener {
            val builder = AlertDialog.Builder(
                this
            )
            builder.setTitle("Wait a sec...")
            builder.setMessage("Free is great but Premium s better.\n  \nGo Premium to enjoy unlimited awesome features! ")
            builder.setPositiveButton("GO PREMIUM", null)
            val alert: AlertDialog = builder.create()
            alert.show()
        }


        val transactionEntity = ExpenseEntity(0, "", "", "", "", "", "")


        btnSaveEditedTransaction.setOnClickListener {

            val id = intent.getIntExtra("identity", 0)

            transactionEntity.id = id
            transactionEntity.date = etSelectEditableDate.text.toString()
            transactionEntity.category = etSelectEditableCategory.text.toString()
            transactionEntity.amount = etSelectEditableAmount.text.toString().toInt()
            transactionEntity.wallet = etSelectEditableWallet.text.toString()
            transactionEntity.note = etSelectEditableNote.text.toString()
            transactionEntity.with = etEditablePeopleWith.text.toString()
            val image = ivCategoryEtEdit.drawable.toBitmap()
            mImagePath = saveImageToInternalStorage(image)
            transactionEntity.image = mImagePath

            viewModel.updateTransaction(transactionEntity)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnCloseEditTransaction.setOnClickListener {
            finish()
        }


    }

    private fun customItemsListDialog(
        title: String,
        itemsList: List<String>,
        imageList: List<Int>,
        selection: String
    ) {

        mCustomListDialog = Dialog(this)
        val binding: DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)
        mCustomListDialog.setContentView(binding.root)

        binding.tvTitle.text = title
        binding.rvList.layoutManager = LinearLayoutManager(this)
        val adapter =
            CustomListItemAdapter(this, itemsList, imageList, selection, this)

        binding.rvList.adapter = adapter

        mCustomListDialog.show()

    }

    override fun selectedListItem(item: String, image: Int, selection: String) {
        when (selection) {

            Constants.CATEGORY -> {
                mCustomListDialog.dismiss()
                etSelectEditableCategory.setText(item)
                Glide.with(ivCategoryEtEdit.context).load(image).into(ivCategoryEtEdit)
            }
            Constants.WALLET -> {
                mCustomListDialog.dismiss()
                etSelectEditableWallet.setText(item)
                Glide.with(ivWalletEtEdit.context).load(image).into(ivWalletEtEdit)
            }
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): String {


        val wrapper = ContextWrapper(this)


        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

        // Mention a file name to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException) { // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image absolute path
        return file.absolutePath
    }

    companion object {
        private val IMAGE_DIRECTORY = "CategoryImage"
    }
}