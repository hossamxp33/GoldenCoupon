package com.codes_roots.golden_coupon.repo.products

import com.codes_roots.golden_coupon.di.IoDispatcher
import com.codes_roots.golden_coupon.entites.category.AllCategoryModel
import com.codes_roots.golden_coupon.entites.products.ProductsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject


class DataRepo @Inject constructor(
    private val Datasources: ProductsDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
)  {

    // // انا كنت ناسي اعمل emit في الcatch عشان كدا كان بيرجع هنا نفس الrespone في الحالتين
    // استخدمت Result في ال follow عشان بترجع الobject , error في نفس الclass وده مش بيحصل في ال sealed
     val getCategoryData:Flow<Result<AllCategoryModel>> =
        flow {
            emit(Datasources.getCategoryResponse())
             }
            .map { Result.success(it) }
            .retry(retries = 4) { t -> (t is IOException).also { if (it) {
                delay(1000 )
            }}}
            .catch {
                     throwable ->  emit(Result.failure(throwable)) }
            .flowOn(ioDispatcher)

        suspend fun getProductsData(cat_id:Int):Flow<Result<ProductsModel>> =
        flow {
            emit(Datasources.getProductsResponse(cat_id))
             }
            .map { Result.success(it) }
            .retry(retries = 4) { t -> (t is IOException).also { if (it) {
                delay(1000 )
            }}}
            .catch {
                     throwable ->  emit(Result.failure(throwable)) }
            .flowOn(ioDispatcher)



//    /


}

