package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudahkmp.core.domain.model.ProductInfo
import com.cobasendiri.kasirmudahkmp.core.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetProductLisUseCase(
    private val productRepository: IProductRepository
){
    fun invoke(searchQuery: String): Flow<Result<List<ProductInfo>>>{
       return productRepository.getAllProducts(searchQuery)
            .map {
                if(it==null){
                    Result.Error(KasirMudahException.UnknownError(null))
                }else{
                    Result.Success(it)
                }
            }.catch{
                emit(Result.Error(it))
            }
    }
}