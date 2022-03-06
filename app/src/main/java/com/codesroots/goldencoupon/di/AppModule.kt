package com.codesroots.goldencoupon.di




import android.content.Context
import com.codesroots.goldencoupon.data_layer.APIServices
import com.codesroots.goldencoupon.helper.PreferenceHelper
import com.codesroots.goldencoupon.repo.auth.AuthRemoteDataSource
import com.codesroots.goldencoupon.repo.brands.DataSource
import com.codesroots.goldencoupon.repo.brands.RemoteDataSource
import com.codesroots.goldencoupon.repo.products.ProductsDataSource
import com.codesroots.goldencoupon.repo.products.RemoteProductsDataSource
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
