package com.codesroots.goldencoupon.helper


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codesroots.goldencoupon.presentation.auth.loginfragment.LoginFragment
import com.codesroots.goldencoupon.presentation.couponsfragment.CouponsFragment
import com.codesroots.goldencoupon.presentation.dealsfragment.DealsFragment
import com.codesroots.goldencoupon.presentation.favfragment.FavoriteFragment
import com.codesroots.goldencoupon.presentation.forgetfragment.ForgetPasswordFragment
import com.codesroots.goldencoupon.presentation.homefragment.HomeFragment
import com.codesroots.goldencoupon.presentation.menufragment.MenuFragment
import com.codesroots.goldencoupon.presentation.notificationfragment.NotificationFragment
import com.codesroots.goldencoupon.presentation.productoffersfragment.ProductOffersFragment

import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * Lazy ViewModel Factory to be used for scopes and subscopes.
 *
 * @author juan.saravia
 */
class ViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown model class: $modelClass")
        }
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}

@Module
abstract class ViewModelBuilderModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: ViewModelFactory, // Extends ViewModelProvider.Factory
    ): ViewModelProvider.Factory // Android Lifecycle ViewModel

}

// This key will enforce using a class that extends ViewModel
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

class MyFragmentFactory @Inject constructor(
    private val creator: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>,
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        return creator[fragmentClass]?.get() ?: super.instantiate(classLoader, className)
    }

}

@Module
abstract class FragmentFactoryModule {

    @Binds
    abstract fun bindFragmentFactroy(factory: MyFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    abstract fun bindMainFragment(fragment: HomeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(ProductOffersFragment::class)
    abstract fun bindProductOffersFragment(fragment: ProductOffersFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(CouponsFragment::class)
    abstract fun bindCouponsFragment(fragment: CouponsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FavoriteFragment::class)
    abstract fun bindFavoriteFragment(fragment: FavoriteFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginFragment::class)
    abstract fun bindLoginFragment(fragment: LoginFragment): Fragment
    @Binds
    @IntoMap
    @FragmentKey(DealsFragment::class)
    abstract fun bindDealsFragment(fragment: DealsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(MenuFragment::class)
    abstract fun bindMenuFragment(fragment: MenuFragment): Fragment   @Binds
    @IntoMap
    @FragmentKey(ForgetPasswordFragment::class)
    abstract fun bindForgetPasswordFragment(fragment: ForgetPasswordFragment): Fragment


    @Binds
    @IntoMap
    @FragmentKey(NotificationFragment::class)
    abstract fun bindNotificationFragment(fragment: NotificationFragment): Fragment

}

@MapKey
annotation class FragmentKey(val clazz: KClass<out Fragment>)