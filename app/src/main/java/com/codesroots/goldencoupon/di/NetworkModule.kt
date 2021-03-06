package com.codesroots.goldencoupon.di

import android.content.Context
import com.codesroots.goldencoupon.data_layer.APIServices
import com.codesroots.goldencoupon.helper.*
import com.codesroots.goldencoupon.helper.Constants.Companion.BASE_URL
import com.codesroots.goldencoupon.presentation.auth.loginfragment.LoginFragment
import com.codesroots.goldencoupon.presentation.auth.signupfragment.SignUpFragment
import com.codesroots.goldencoupon.presentation.couponsfragment.CouponsFragment
import com.codesroots.goldencoupon.presentation.dealsfragment.DealsFragment
import com.codesroots.goldencoupon.presentation.favfragment.FavoriteFragment
import com.codesroots.goldencoupon.presentation.forgetfragment.ForgetPasswordFragment
import com.codesroots.goldencoupon.presentation.homefragment.HomeFragment
import com.codesroots.goldencoupon.presentation.menufragment.MenuFragment
import com.codesroots.goldencoupon.presentation.menufragment.StaticFragment
import com.codesroots.goldencoupon.presentation.notificationfragment.NotificationFragment
import com.codesroots.goldencoupon.presentation.productoffersfragment.ProductOffersFragment
import com.codesroots.goldencoupon.presentation.sortfragment.SortFragment


import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 *
 * @author juancho.
 */
@Singleton
@Component(
    modules =
    [
        DispatcherModule::class,
        AndroidInjectionModule::class,
        APIModule::class,
        AppModule::class,
        FragmentFactoryModule::class,
        MainModule::class,
        ActivityBuildersModule::class,
        FragmentFactoryModule::class,
        ViewModelBuilderModule::class,
    ]
)


interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun inject(app: HomeFragment)
    fun inject(app: ProductOffersFragment)
    fun inject(app: MenuFragment)
    fun inject(app: CouponsFragment)
    fun inject(app: FavoriteFragment)
    fun inject(app: SortFragment)
    fun inject(app: DealsFragment)
    fun inject(app: SignUpFragment)
    fun inject(app: LoginFragment)
    fun inject(app: StaticFragment)
    fun inject(app: NotificationFragment)
    fun inject(app: ForgetPasswordFragment)


}


@Module
class APIModule constructor() {

    @Singleton
    @Provides
    fun provideHttpClient(context: Context): OkHttpClient {
        return OkHttpClient()
            .newBuilder()

            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain: Interceptor.Chain ->
                val originalRequest = chain.request()
                    var Pref = PreferenceHelper(context)
                val builder = originalRequest.newBuilder()


                //   builder.addHeader("Accept", "application/json")
                builder.addHeader("Content-Type", "application/json")
                builder.addHeader("Authorization",
                    "Bearer "+Pref.token)
//                Log.d("token",Pref.token!!)
                val newRequest = builder.build()

                chain.proceed(newRequest)

            }
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun ApiServices(retrofit: Retrofit): APIServices = retrofit.create(APIServices::class.java)


}
