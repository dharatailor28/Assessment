package com.example.assessment.retrofit

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.assessment.ProductsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class SharedPref {
    private var mSharedPref: SharedPreferences? = null
    private val gson = Gson()
    lateinit var productList: ArrayList<ProductsItem>

    fun init(context: Context) {
        if (mSharedPref == null) mSharedPref =
            context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE)
    }

    fun getFavList(key: String?): ArrayList<ProductsItem> {

        val json = mSharedPref?.getString(key, "")
        if(!json.equals(""))
        {
            val type: Type = object : TypeToken<ArrayList<ProductsItem?>?>() {}.type
            productList = gson.fromJson<ProductsItem>(json, type) as ArrayList<ProductsItem>

        }else{
            productList = ArrayList()
        }
        return productList
    }

    fun putFavList(key: String?, value: ArrayList<ProductsItem>?) {
        val prefsEditor = mSharedPref!!.edit()
        val json: String = gson.toJson(value)
        prefsEditor.putString(key, json)
        prefsEditor.apply()
    }
}