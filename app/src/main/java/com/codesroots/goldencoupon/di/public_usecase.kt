package com.codesroots.goldencoupon.di

import android.app.Activity
import android.content.Context
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.helper.Constants

import com.google.android.material.imageview.ShapeableImageView
import com.makeramen.roundedimageview.RoundedImageView
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.lang.Long
import java.text.SimpleDateFormat
import java.util.*


///TOAST_SUCCESS_MotionToast
fun SUCCESS_MotionToast(massage: String, context: Activity) {
    MotionToast.createColorToast(
        context,
        "تم بنجاح",
        massage,
        MotionToastStyle.SUCCESS,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(context, R.font.helvetica_regular)
    )
}
fun WARN_MotionToast(massage: String, context: Activity) {
    MotionToast.createColorToast(
        context,
        context.getString(R.string.attention),
        massage,
        MotionToastStyle.WARNING,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(context, R.font.helvetica_regular)
    )
}
fun Error_MotionToast(massage: String, context: Activity) {
    MotionToast.createColorToast(
        context,
        "يوجد خطأ",
        massage,
        MotionToastStyle.ERROR,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(context, R.font.helvetica_regular)
    )
}


@BindingAdapter("app:imageResource")
fun setImageResource(imageView: AppCompatImageView, resource: String?) {
    val requestOptions = RequestOptions()
    requestOptions.error(R.drawable.noimg)
    Glide.with(imageView.context)
        .load((Constants.IMAGE_URL + resource))
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(requestOptions)
        .into(imageView)
}
@BindingAdapter("app:imageResourcee")
fun imageResourcee(imageView: RoundedImageView, resource: String?) {
    val requestOptions = RequestOptions()
    requestOptions.error(R.drawable.noimg)
    Glide.with(imageView.context)
        .load((Constants.IMAGE_URL+resource))
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(requestOptions)
        .into(imageView)
}

@BindingAdapter("app:imageResourcee")
fun setSliderImageResource(imageView: AppCompatImageView, resource: String?) {
    val requestOptions = RequestOptions()
    requestOptions.error(R.drawable.noimg)
    Glide.with(imageView.context)
        .load(Constants.IMAGE_URL+(resource))
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(requestOptions)
        .into(imageView)
}


@BindingAdapter("app:datetext")
fun setDatetext(text: TextView, resource: String?) {
    val myFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    val dateObj: Date = myFormat.parse(resource!!)
    val timestamp = dateObj.time.toString()//  //Example -> in ms
    val fromServer = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale("en"))
    val dateString = fromServer.format(Date(Long.parseLong(timestamp)))


    text.text = dateString

}

fun ShapeAppearanceRounded(context: Context, view: ShapeableImageView) {
    val radius = context.resources.getDimension(R.dimen.dimen_48)
    val shapeAppearanceModel = view.shapeAppearanceModel.toBuilder()
        .setAllCornerSizes(radius)
        .build()
    view.shapeAppearanceModel = shapeAppearanceModel

}

fun <T> androidLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
inline fun <reified T : ViewModel> Fragment.getViewModel(viewModelFactory: ViewModelProvider.Factory): T =
    ViewModelProviders.of(this, viewModelFactory)[T::class.java]

