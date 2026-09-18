package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import co.touchlab.kermit.Logger
import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductInfo
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProductRepository
import com.cobasendiri.kasirmudahkmp.core.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetProductLisUseCase(
    private val productRepository: IProductRepository
): BaseUseCase() {

    override val log: Logger = Logger.withTag("GetProductLisUseCase")

    fun invoke(searchQuery: String): Flow<Result<List<ProductInfo>>>{
       return productRepository.getAllProducts(searchQuery)
            .map {
                if(it==null){
                    logWarning("Product list is null")
                    Result.Error(KasirMudahException.UnknownError(null))
                }else{
                    logInfo("Product list: $it")
                    Result.Success(it)
                }
            }.catch{
                emit(Result.Error(it))
            }
    }
}