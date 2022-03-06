package com.codesroots.goldencoupon.helper

import android.app.ActivityOptions
import android.content.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.di.WARN_MotionToast
import com.codesroots.goldencoupon.entites.allbrands.AllBrandsModel
import com.codesroots.goldencoupon.entites.products.Product
import com.codesroots.goldencoupon.entites.staticpages.StaticPagesItem
import com.codesroots.goldencoupon.presentation.auth.RegisterActivity
import com.codesroots.goldencoupon.presentation.chose_language.LanguageActivity
import com.codesroots.goldencoupon.presentation.country_activity.CountryActivity
import com.codesroots.goldencoupon.presentation.couponsfragment.CouponsFragment
import com.codesroots.goldencoupon.presentation.dealsfragment.DealsFragment
import com.codesroots.goldencoupon.presentation.forgetfragment.ForgetPasswordFragment
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity
import com.codesroots.goldencoupon.presentation.menufragment.StaticFragment
import com.codesroots.goldencoupon.presentation.notificationfragment.NotificationFragment
import com.codesroots.goldencoupon.presentation.productoffersfragment.mvi.ProductsViewModel
import com.codesroots.goldencoupon.presentation.sortfragment.SortFragment
import com.codesroots.goldencoupon.presentation.web_view.WebViewActivity
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.auth.FirebaseAuth

class ClickHandler {

    fun switchToCouponsFragment(brandId: Int, context: Context, image: String) {
        context as MainActivity
        val fragment = CouponsFragment()
        val bundle = Bundle()
        bundle.putInt("brandId", brandId)
        bundle.putString("brand_image", image)
        fragment.arguments = bundle
        switchFragment(context, fragment)

    }

    fun openSortFragment(
        context: Context,
        viewModel: ProductsViewModel,
        data: Product?,
        brandsData: AllBrandsModel,
    ) {
        val frag = SortFragment(viewModel)
        frag.apply {
            show((context as MainActivity).supportFragmentManager, SortFragment.TAG)
        }

    }

    fun rateApp(context: Context) {
        context as MainActivity
        val reviewManager = ReviewManagerFactory.create(context)
        val requestReviewFlow = reviewManager.requestReviewFlow()
        requestReviewFlow.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                val reviewInfo = request.result
                val flow = reviewManager.launchReviewFlow(context, reviewInfo)
                flow.addOnCompleteListener {
                }
            } else {
                Log.d("Error: ", request.exception.toString())
            }
        }
    }

    fun setClipboard(context: Context, text: String, code: String) {
        context as MainActivity

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            val clipboard =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
            clipboard.text = text
        } else {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", text)
            clipboard.setPrimaryClip(clip)

        }

        Toast.makeText(context, context.getString(R.string.copied) +
                " " + code, Toast.LENGTH_SHORT).show()
    }

    fun openStaticFragment(context: Context, Item: StaticPagesItem) {
        (context as MainActivity)
        context.supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, StaticFragment(Item)).addToBackStack(null).commit()

    }

    fun switchToDealsFragment(context: Context) {
        switchFragment(context, DealsFragment())
    }

    fun switchToForgetPWFragment(context: Context) {

        ( context as RegisterActivity).supportFragmentManager.beginTransaction()
            .setCustomAnimations(0, 0, 0, 0)
            .replace(R.id.login_frame, ForgetPasswordFragment()).addToBackStack(null).commit()
    }
    fun Logout(context: Context){
         context as MainActivity
       FirebaseAuth.getInstance().signOut()

        context.preferenceHelper.token = ""
        context.preferenceHelper.userName=""
        Toast.makeText(context, "تم تسجيل خروجك", Toast.LENGTH_SHORT).show()
        val homeIntent = Intent(context, RegisterActivity::class.java)
        ( context ).startActivity(homeIntent, ActivityOptions.makeSceneTransitionAnimation(context).toBundle())

    }
    fun switchToNotificationFragment(context: Context) {
        switchFragment(context, NotificationFragment())
    }

    fun switchToLanguage(context: Context) {
        switchToActivity(context, LanguageActivity())
    }

    fun switchToCountry(context: Context) {
        switchToActivity(context, CountryActivity())
    }

    fun switchToLogin(context: Context) {
        switchToActivity(context, RegisterActivity())
    }


    fun switchFragment(context: Context, fragment: Fragment) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .setCustomAnimations(0, 0, 0, 0)
            .replace(R.id.main_frame, fragment).addToBackStack(null).commit()
    }

    fun checkForToken(context: Context, fragment: Fragment): Boolean {
        context as MainActivity
        if ((context).preferenceHelper.token.isNullOrEmpty()) {
            WARN_MotionToast((context).getString(R.string.loginFirst), context)
            val i = Intent(context, RegisterActivity::class.java)
            (context).startActivityForResult(i, 100)
            return true
        } else
            switchFragment(context, fragment)
        return false
    }

    fun openWebView(context: Context, url: String?, url_en:String?) {
        context as MainActivity

        if (!url.isNullOrEmpty()) {

            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", url);
            intent.putExtra("url_en", url_en);

            (context.startActivity(intent))
        }
    }

    fun switchToMainActivity(context: Context) {
        switchToActivity(context, MainActivity())
    }
   fun switchToRegisterActivity(context: Context, countryId: Int?) {
        context as CountryActivity
        context.Pref.CountryId = countryId!!
        switchToActivity(context, RegisterActivity())

    }

    fun switchToActivity(context: Context, activity: AppCompatActivity) {

        val i = Intent(context, activity::class.java)
        (context).startActivity(i);

    }
}
