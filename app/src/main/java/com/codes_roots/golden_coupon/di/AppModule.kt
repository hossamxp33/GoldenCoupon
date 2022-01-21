package com.codes_roots.golden_coupon.di




import com.codes_roots.golden_coupon.data_layer.APIServices
import com.codes_roots.golden_coupon.repo.brands.DataSource
import com.codes_roots.golden_coupon.repo.brands.RemoteDataSource
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

//    @Provides
//    fun providePreferenceHelper(context: Context): PreferenceHelper {
//        return PreferenceHelper(context)
//    }

}
