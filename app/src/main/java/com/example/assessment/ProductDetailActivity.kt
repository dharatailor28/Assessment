package com.example.assessment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.assessment.databinding.ActivityProductDetailBinding
import com.example.assessment.retrofit.SharedPref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.item_product.*
import java.lang.reflect.Type

class ProductDetailActivity : AppCompatActivity() {
    private val gson = Gson()
    private val appSharedPref = SharedPref()
    private lateinit var binding: ActivityProductDetailBinding
    var list = ArrayList<ProductsItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        appSharedPref.init(this)
        val data = intent.getStringExtra(getString(R.string.Data))
        val collectionType: Type = object : TypeToken<ProductsItem?>() {}.type
        val productDetail: ProductsItem = gson.fromJson(data, collectionType)
        binding.textViewTitle.text = productDetail.title
        binding.textViewPrice.text =
            "Price : " + productDetail.price?.get(0)?.value.toString() + " Rs"
        Glide.with(binding.root).load(productDetail.imageURL).placeholder(R.drawable.ic_launcher_background).into(binding.imageView)
        binding.ratingBar.rating = productDetail.ratingCount?.toFloat()!!
        imgFav.isChecked = productDetail.isChecked == true
        list = appSharedPref.getFavList(getString(R.string.favProduct))

        imgFav.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (list.size == 0) {
                    productDetail.isChecked = true
                    list.add(productDetail)
                } else {
                    for (i in this.list.indices) {
                        if (productDetail.id == list[i].id) {
                            list[i].isChecked = true
                            productDetail.isChecked = true
                            list.removeAt(i)
                            list.add(productDetail)
                            break
                        } else {
                            list[i].isChecked = true
                            productDetail.isChecked = true
                            list.add(productDetail)
                            break
                        }
                    }
                }
            } else {
                for (i in list.indices) {
                    if (list[i].id == productDetail.id) {
                        list[i].isChecked = false
                        var j = list.size
                        for (i in 0 until j) {
                            if (list[i].id == productDetail.id) {
                                list.removeAt(i)
                                break
                            }
                        }
                        break
                    }
                }
            }
            appSharedPref.putFavList(getString(R.string.favProduct), list)
        }
    }
}