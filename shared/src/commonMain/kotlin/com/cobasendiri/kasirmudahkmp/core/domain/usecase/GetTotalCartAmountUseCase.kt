package com.cobasendiri.kasirmudahkmp.core.domain.usecase

import com.cobasendiri.kasirmudahkmp.core.domain.Result
import com.cobasendiri.kasirmudahkmp.core.domain.repository.ICartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetTotalCartAmountUseCase(
    private val cartRepository: ICartRepository
) {
    fun invoke(): Flow<Result<Long?>>{
        return cartRepository.getTotalCartAmount()
            .map {
                Result.Success(it)
            }.catch {
                Result.Error(it)
            }
    }
}