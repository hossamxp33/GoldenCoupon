package com.codesroots.goldencoupon.repo.products

import com.codesroots.goldencoupon.di.IoDispatcher
import com.codesroots.goldencoupon.entites.allbrands.AllBrandsModel
import com.codesroots.goldencoupon.entites.category.AllCategoryModel
import com.codesroots.goldencoupon.entites.products.ProductsModel
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

    suspend fun getProductsData(country_id: Int?,page:Int?,sort:String?):Flow<Result<ProductsModel>> =
        flow {
            emit(Datasources.getProductResponse(country_id,page,sort))
             }
            .map { Result.success(it) }
            .retry(retries = 4) { t -> (t is IOException).also { if (it) {
                delay(1000 )
            }}}
            .catch {
                     throwable ->  emit(Result.failure(throwable)) }
            .flowOn(ioDispatcher)


     val getAllBrandsResponse:Flow<Result<AllBrandsModel>> =
        flow {
            emit(Datasources.getAllBrandsResponse())
             }
            .map { Result.success(it) }
            .retry(retries = 4) { t -> (t is IOException).also { if (it) {
                delay(1000 )
            }}}
            .catch {
                     throwable ->  emit(Result.failure(throwable)) }
            .flowOn(ioDispatcher)


        suspend fun getProductsData(country_id: Int?,sort:String?,FilterData:HashMap<String,String>):Flow<Result<ProductsModel>> =
        flow {
            emit(Datasources.getFilterProductsResponse(country_id,FilterData,sort))
             }
            .map { Result.success(it) }
            .retry(retries = 4) { t -> (t is IOException).also { if (it) {
                delay(1000 )
            }
            }
            }
            .catch {
                     throwable ->  emit(Result.failure(throwable)) }
            .flowOn(ioDispatcher)
}

