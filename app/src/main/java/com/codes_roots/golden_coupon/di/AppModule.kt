package com.codes_roots.golden_coupon.di




import android.content.Context
import com.codes_roots.golden_coupon.data_layer.APIServices
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.repo.auth.AuthRemoteDataSource
import com.codes_roots.golden_coupon.repo.brands.DataSource
import com.codes_roots.golden_coupon.repo.brands.RemoteDataSource
import com.codes_roots.golden_coupon.repo.products.ProductsDataSource
import com.codes_roots.golden_coupon.repo.products.RemoteProductsDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule() {





    @Singleton
    @Provides
    fun provideTasksRemoteDataSource(apiService: APIServices): DataSource {
        return RemoteDataSource(apiService)
    }

    @Singleton
    @Provides
    fun provideTasksBranchDataSource(apiService: APIServices): ProductsDataSource {
        return RemoteProductsDataSource(apiService)
    }

    @Singleton
    @Provides
    fun provideTasks(apiService: APIServices): AuthRemoteDataSource {
        return AuthRemoteDataSource(apiService)
    }

    @Provides
    fun providePreferenceHelper(context: Context): PreferenceHelper {
        return PreferenceHelper(context)
    }

}
