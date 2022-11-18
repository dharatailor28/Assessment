package com.example.assessment.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assessment.ProductDetailActivity
import com.example.assessment.ProductsItem
import com.example.assessment.R
import com.example.assessment.adapter.ProductListAdapter
import com.example.assessment.databinding.FragmentListBinding
import com.example.assessment.retrofit.SharedPref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class FragmentList : Fragment(R.layout.fragment_list) {
    var gson = Gson()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductListAdapter
    var list = ArrayList<ProductsItem>()
    val appSharedPref = SharedPref()
    private lateinit var testModel: List<ProductsItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        appSharedPref.init(requireActivity())
        val name = arguments?.getString(getString(R.string.jsonData))
        val collectionType: Type = object : TypeToken<List<ProductsItem?>?>() {}.type
        testModel = gson.fromJson(name, collectionType)

        return view
    }

    private fun setAdapter() {

        if (testModel.isNotEmpty()) {
            binding.recyclerview.visibility = View.VISIBLE
            binding.firstFragment.visibility = View.GONE
        } else {
            binding.recyclerview.visibility = View.GONE
            binding.firstFragment.visibility = View.VISIBLE
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        adapter = ProductListAdapter(testModel, object : ProductListAdapter.BtnClickListener {
            override fun onBtnClick(position: Int) {
            }

            override fun onItemClick(position: Int) {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra(getString(R.string.Data), gson.toJson(testModel[position]))
                startActivity(intent)
            }

            override fun onFavClick(position: Int, imgFav: CheckBox) {
                if (imgFav.isChecked) {
                    list.add(testModel[position])
                    appSharedPref.putFavList(getString(R.string.favProduct), list)
                } else {
                    list = appSharedPref.getFavList(getString(R.string.favProduct))

                    var j = list.size
                    for (i in 0 until j) {
                        if (list[i].id == testModel[position].id) {
                            list.removeAt(i)
                            break
                        }
                    }

                    if (list.size == 0) {
                        list = ArrayList()
                    }
                    appSharedPref.putFavList(getString(R.string.favProduct), list)
                }
                if (list == null) {
                    list = ArrayList()
                }
            }
        })
        binding.recyclerview.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        list = appSharedPref.getFavList(getString(R.string.favProduct))

        if (list.size == 0) {
            list = ArrayList()
            for (i in testModel.indices) {
                testModel[i].isChecked = false
            }
        } else {
            for (i in testModel.indices) {
                for (j in list.indices) {
                    if (testModel[i].id == list[j].id) {
                        var value = list[j].isChecked
                        testModel[i].isChecked = value
                        break
                    } else {
                        testModel[i].isChecked = false
                    }
                }
            }
        }
        setAdapter()
    }
}
