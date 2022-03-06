package com.codesroots.goldencoupon.presentation.country_activity


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.helper.PreferenceHelper
import com.codesroots.goldencoupon.presentation.country_activity.adapter.CountriesAdapter
import com.codesroots.goldencoupon.presentation.country_activity.viewmodel.CountryViewModel

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.country_activity.*
import javax.inject.Inject


class CountryActivity @Inject constructor(): AppCompatActivity() , HasAndroidInjector {

    lateinit var countriesAdapter: CountriesAdapter

    @Inject
    lateinit var Pref: PreferenceHelper

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<CountryViewModel> { viewModelFactory }

     override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this@CountryActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_activity)

      viewModel.getCountriesData()
         viewModel.CountryLD!!.observe(this, Observer {
                 countriesAdapter.submitList(it.Countries)

         })
         countriesRecycleView()
    }
    fun countriesRecycleView() {
        countriesAdapter = CountriesAdapter(this)
        countriesRecycle.apply {
            layoutManager = LinearLayoutManager(
                context
            )
            adapter = countriesAdapter;
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }

    }
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

}