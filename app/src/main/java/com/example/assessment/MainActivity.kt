package com.example.assessment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.assessment.databinding.ActivityMainBinding
import com.example.assessment.fragment.FragmentFav
import com.example.assessment.fragment.FragmentList
import com.example.assessment.retrofit.AppCreator
import com.example.assessment.retrofit.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var mProductResponse: Response
    private lateinit var mProductViewModel: ProductListViewModel
    private var json: String = ""
    private val gson = Gson()
    private lateinit var binding: ActivityMainBinding
    private lateinit var commonClass: CommonClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        commonClass = CommonClass()
        val view = binding.root
        setContentView(view)
        initData()
        obtainListFromServer()
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    private fun obtainListFromServer() {
        if (commonClass.checkForInternet(this)) {
            //observe live data from viewmodel
            mProductViewModel.getAllProduct().observe(this)

            {
                when (it.status) {
                    Status.SUCCESS -> {

                        mProductResponse = it.data!!

                        getData()

                        Toast.makeText(
                            this, getString(R.string.successfully_load), Toast.LENGTH_LONG
                        ).show()

                    }

                    Status.FAILURE -> {
                        Toast.makeText(
                            this, getString(R.string.fail_load)+" ${it.message}", Toast.LENGTH_LONG
                        ).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(
                            this, getString(R.string.data_loading), Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.check_internet), Toast.LENGTH_LONG).show()
        }

    }

    private fun getData() {
        json = gson.toJson(mProductResponse.products)

        val firstFragment = FragmentList()
        val args = Bundle()

        args.putString(getString(R.string.jsonData), json)
        firstFragment.arguments = args

        val secondFragment = FragmentFav()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.list -> setCurrentFragment(firstFragment)
                R.id.fav -> setCurrentFragment(secondFragment)
            }
            true
        }
    }

    private fun initData() {
        //initialization of viewmodel instance,
        mProductViewModel = ViewModelProvider(
            this, ViewModelFactory(AppCreator.getApiHelperInstance())
        ).get(ProductListViewModel::class.java)
    }
}