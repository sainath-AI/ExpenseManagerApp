package com.masai.sainath.expensemanager.views

import com.masai.sainath.expensemanager.R

object Constants {
    const val CATEGORY: String = "Category"
    const val WALLET:String="Wallet"
    fun categories(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("Food & Beverage")
        list.add("Cafe")
        list.add("Phone Bill")
        list.add("Water Bill")
        list.add("Transportation")
        list.add("Taxi")
        list.add("Shopping")
        list.add("Clothing")
        list.add("Footwear")
        list.add("Friends & Lover")
        list.add("Entertainment")
        list.add("Movies")
        list.add("Travel")
        list.add("Other")
        return list
    }
    fun imageCategory():ArrayList<Int>{
       val list=ArrayList<Int>()
        list.add(R.drawable.food_beverage)
        list.add(R.drawable.cafe)
        list.add(R.drawable.phone_bill)
        list.add(R.drawable.water_bill)
        list.add(R.drawable.transportations)
        list.add(R.drawable.taxi)
        list.add(R.drawable.shopping)
        list.add(R.drawable.clothing)
        list.add(R.drawable.footwear)
        list.add(R.drawable.love)
        list.add(R.drawable.entertainment)
        list.add(R.drawable.movies)
        list.add(R.drawable.travel)
        list.add(R.drawable.other)

        return list
    }
    fun wallets():ArrayList<String>{
        val list=ArrayList<String>()
        list.add("Cash")
        list.add("Upi")
        list.add("Card")

        return list

    }
    fun walletsImage():ArrayList<Int>{
        val list=ArrayList<Int>()
        list.add(R.drawable.ic_purse)
        list.add(R.drawable.upi)
        list.add(R.drawable.atm)

        return list

    }
}