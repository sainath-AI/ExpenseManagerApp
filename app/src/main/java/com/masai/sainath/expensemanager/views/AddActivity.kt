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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AddActivity : AppCompatActivity() , OnCategoryClickListener {

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
        setContentView(R.layout.activity_add)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)



        etSelectDate.setOnClickListener {
            val dp = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    etSelectDate.setText(dayOfMonth.toString() + "/" + month.toString() + "/" + year.toString())
                },
                year,
                month,
                day
            )
            dp?.show()
        }
        etSelectCategory.setOnClickListener {
            customItemsListDialog(
                resources.getString(R.string.title_select_category),
                Constants.categories(),
                Constants.imageCategory(),
                Constants.CATEGORY
            )
        }
        etSelectWallet.setOnClickListener {
            customItemsListDialog(
                resources.getString(R.string.title_select_wallet),
                Constants.wallets(),
                Constants.walletsImage(),
                Constants.WALLET
            )

        }

        ivSelectFromGallery.setOnClickListener {
            val builder = AlertDialog.Builder(
                applicationContext
            )
            builder.setTitle("Wait a sec...")
            builder.setMessage("Free is great but Premium s better. \n  \nGo Premium o enjoy unlimited awesome features!")
            builder.setPositiveButton("GO PREMIUM", null)
            val alert: AlertDialog = builder.create()
            alert.show()
        }
        ivClickPicture.setOnClickListener {
            val builder = AlertDialog.Builder(
                applicationContext
            )
            builder.setTitle("Wait a sec...")
            builder.setMessage("Free is great but Premium s better.\n  \nGo Premium to enjoy unlimited awesome features! ")
            builder.setPositiveButton("GO PREMIUM", null)
            val alert: AlertDialog = builder.create()
            alert.show()
        }

        etSetReminder.setOnClickListener {
            val time = etSetReminder.text.toString().toInt()
            val intent = Intent(AlarmClock.ACTION_SET_ALARM)
            intent.putExtra(AlarmClock.EXTRA_HOUR, time)
            intent.putExtra(AlarmClock.EXTRA_MINUTES, 0)
            startActivity(intent)
        }

        val viewModelFactory = ExpenseViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ExpenseViewModel::class.java)

        btnSaveTransaction.setOnClickListener {
            val date = etSelectDate.text.toString()
            val category = etSelectCategory.text.toString()
            val amount = etSelectAmount.text.toString().toInt()
            val wallet = etSelectWallet.text.toString()
            val note = etSelectNote.text.toString()
            val with = etPeopleWith.text.toString()
            val image = ivCategoryEt.drawable.toBitmap()
            mImagePath = saveImageToInternalStorage(image)

            val transactionEntity = ExpenseEntity(amount, category, date, wallet, note, with, mImagePath)


            viewModel.addTransaction(transactionEntity)

            startActivity(Intent(this, MainActivity::class.java))
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
                etSelectCategory.setText(item)
                Glide.with(ivCategoryEt.context).load(image).into(ivCategoryEt)
            }
            Constants.WALLET -> {
                mCustomListDialog.dismiss()
                etSelectWallet.setText(item)
                Glide.with(ivWalletEt.context).load(image).into(ivWalletEt)
            }

        }
    }

    //        fun selectedListItem(item: String, image: Int, selection: String) {
//
//            when (selection) {
//
//                Constants.CATEGORY -> {
//                    mCustomListDialog.dismiss()
//                    mBinding.etSelectCategory.setText(item)
//                }
//
//            }
//        }
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
    companion object{
        val IMAGE_DIRECTORY="CategoryImage"
    }



}