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
import com.example.assessment.adapter.FavouriteAdapter
import com.example.assessment.databinding.FragmentListBinding
import com.example.assessment.retrofit.SharedPref
import com.google.gson.Gson

class FragmentFavourite : Fragment(R.layout.fragment_list) {
    var arrayListProductItem = arrayListOf<ProductsItem>()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    var gson = Gson()
    private lateinit var adapter: FavouriteAdapter
    val appSharedPref = SharedPref()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        appSharedPref.init(requireActivity())

        if (arrayListProductItem.size == 0) {
            // if the array list is empty
            // creating a new array list.
            arrayListProductItem = ArrayList()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        arrayListProductItem = appSharedPref.getFavList(getString(R.string.favProduct))
        setAdapter()

    }


    private fun setAdapter() {

        if (arrayListProductItem.size>0) {
            binding.recyclerview.visibility = View.VISIBLE
            binding.firstFragment.visibility = View.GONE
        } else {
            binding.recyclerview.visibility = View.GONE
            binding.firstFragment.visibility = View.VISIBLE
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        adapter = FavouriteAdapter(arrayListProductItem, object : FavouriteAdapter.BtnClickListener {
            override fun onBtnClick(position: Int) {

            }

            override fun onItemClick(position: Int) {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra(getString(R.string.Data), gson.toJson(arrayListProductItem[position]))
                startActivity(intent)
            }

            override fun onFavClick(position: Int, imgFav: CheckBox) {
                if (!imgFav.isChecked) {
                    arrayListProductItem.removeAt(position)
                    appSharedPref.putFavList(getString(R.string.favProduct), arrayListProductItem)
                    adapter.notifyDataSetChanged()

                    if (arrayListProductItem.size>0) {
                        binding.recyclerview.visibility = View.VISIBLE
                        binding.firstFragment.visibility = View.GONE
                    } else {
                        binding.recyclerview.visibility = View.GONE
                        binding.firstFragment.visibility = View.VISIBLE
                    }

                }
            }
        })
        binding.recyclerview.adapter = adapter
    }
}