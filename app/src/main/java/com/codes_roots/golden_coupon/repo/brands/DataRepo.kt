package com.codes_roots.golden_coupon.repo.brands

import com.codes_roots.golden_coupon.di.IoDispatcher
import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject


class DataRepo @Inject constructor(
    private val Datasources: DataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
)  {

    // // انا كنت ناسي اعمل emit في الcatch عشان كدا كان بيرجع هنا نفس الrespone في الحالتين
    // استخدمت Result في ال follow عشان بترجع الobject , error في نفس الclass وده مش بيحصل في ال sealed
     val getMainData:Flow<Result<BrandsModel>> =
        flow {
            emit(Datasources.getBrandsResponse())
             }
            .map { Result.success(it) }
            .retry(retries = 4) { t -> (t is IOException).also { if (it) {
                delay(1000 )
            }}}
            .catch {
                     throwable ->  emit(Result.failure(throwable)) }
            .flowOn(ioDispatcher)
}

