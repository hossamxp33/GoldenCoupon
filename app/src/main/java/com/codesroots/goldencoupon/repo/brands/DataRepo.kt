package com.codesroots.goldencoupon.repo.brands

import com.codesroots.goldencoupon.di.IoDispatcher
import com.codesroots.goldencoupon.entites.brandsmodel.BrandsModel
import com.codesroots.goldencoupon.entites.deals.DealsModel
import com.codesroots.goldencoupon.entites.fav.FavouritModel
import com.codesroots.goldencoupon.entites.staticpages.StaticPagesModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject


class DataRepo @Inject constructor(
    private val Datasources: DataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    // // انا كنت ناسي اعمل emit في الcatch عشان كدا كان بيرجع هنا نفس الrespone في الحالتين
    // استخدمت Result في ال follow عشان بترجع الobject , error في نفس الclass وده مش بيحصل في ال sealed
    fun getMainData(page: Int?, filter: String): Flow<Result<BrandsModel>> =
        flow {
            emit(Datasources.getBrandsResponse(page, filter))
        }
            .map { Result.success(it) }
            .retry(retries = 4) { t ->
                (t is IOException).also {
                    if (it) {
                        delay(1000)
                    }
                }
            }.catch { throwable -> emit(Result.failure(throwable)) }
            .flowOn(ioDispatcher)


    fun getDealsData(page: Int?): Flow<Result<DealsModel>> =
        flow {
            emit(Datasources.getDealsResponse(page))
        }
            .map { Result.success(it) }
            .retry(retries = 4) { t ->
                (t is IOException).also {
                    if (it) {
                        delay(1000)
                    }
                }
            }.catch { throwable -> emit(Result.failure(throwable)) }
            .flowOn(ioDispatcher)


    val getFavouriteData: Flow<Result<FavouritModel>> =
        flow {
            emit(Datasources.getFavoritesResponse())
        }
            .map { Result.success(it) }
            .retry(retries = 4) { t ->
                (t is IOException).also {
                    if (it) {
                        delay(1000)
                    }
                }
            }
            .catch { throwable -> emit(Result.failure(throwable)) }
            .flowOn(ioDispatcher)

    fun addFavouriteData(brand_id: Int): Flow<Result<Boolean>> =
        flow {
            emit(Datasources.addFavorites(brand_id))
        }
            .map { Result.success(it) }
            .retry(retries = 4) { t ->
                (t is IOException).also {
                    if (it) {
                        delay(1000)
                    }
                }
            }
            .catch { throwable -> emit(Result.failure(throwable)) }
            .flowOn(ioDispatcher)

    fun deleteFavorite(brand_id: Int): Flow<Result<Boolean>> =
        flow {
            emit(Datasources.deleteFavorite(brand_id))
        }
            .map { Result.success(it) }
            .retry(retries = 4) { t ->
                (t is IOException).also {
                    if (it) {
                        delay(1000)
                    }
                }
            }
            .catch { throwable -> emit(Result.failure(throwable)) }
            .flowOn(ioDispatcher)




    fun getStaticPages(): Flow<Result<StaticPagesModel>> =
        flow {
            emit(Datasources.getStaticPages())
        }
            .map { Result.success(it) }
            .retry(4) { t ->
                (t is IOException).also {
                    if (it) {
                        delay(1000)
                    }
                }
            }
            .catch { throwable -> emit(Result.failure(throwable)) }
            .flowOn(ioDispatcher)

}

