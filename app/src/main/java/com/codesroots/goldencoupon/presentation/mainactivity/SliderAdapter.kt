package com.codesroots.goldencoupon.presentation.mainactivity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.di.setSliderImageResource
import com.codesroots.goldencoupon.entites.brandsmodel.Slider

import com.codesroots.goldencoupon.presentation.web_view_slider.WebViewSliderActivity
import kotlinx.android.synthetic.main.viewpagerslide_home1.view.*


import javax.inject.Inject


class SliderAdapter @Inject constructor(val context: Context,val slidersData: List<Slider>) :
    PagerAdapter() {

    override fun getCount(): Int {
        return slidersData.size
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1 //To change body of created functions use File | Settings | File Templates.
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            (context as MainActivity).getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.viewpagerslide_home1, container, false)

        setSliderImageResource(view.im_slider, slidersData[position].photo)
        view.im_slider.setOnClickListener {

            if (!slidersData[position].url.isNullOrEmpty()) {

                val intent = Intent(context, WebViewSliderActivity::class.java)
                intent.putExtra("url", slidersData[position].url);
                intent.putExtra("code", slidersData[position].code)
                intent.putExtra("description", slidersData[position].description)

                //       intent.putExtra("url_en", url_en);

                (context.startActivity(intent))
            }
        }
        container.addView(view)



        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }


}
