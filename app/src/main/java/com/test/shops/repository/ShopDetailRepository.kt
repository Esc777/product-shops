package com.test.shops.repository

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.test.shops.entity.ShopDetail
import kotlinx.coroutines.delay
import org.threeten.bp.Instant

interface ShopDetailRepository {
    fun getShop(itemId: Int): Result<ShopDetail, FuelError>

    object RepositoryImpl : ShopDetailRepository {

        private val mockData: List<ShopDetail> = listOf(
            ShopDetail(
                1,
                1_500_000,
                56.49835495,
                84.97313642,
                Instant.now(),
                ShopDetail.Status.Open
            ),
            ShopDetail(
                2,
                2_000_000_000,
                56.4819747,
                84.9961782,
                Instant.now(),
                ShopDetail.Status.Open
            ),
            ShopDetail(
                3,
                3_000_000_000,
                56.51230625,
                84.96976847,
                Instant.now(),
                ShopDetail.Status.Closed
            ),
            ShopDetail(
                4,
                3_000_000_000,
                56.51205405,
                85.03521516,
                Instant.now(),
                ShopDetail.Status.Closed
            ),
            ShopDetail(
                5,
                3_000_000_000,
                56.48089735,
                84.9811467,
                Instant.now(),
                ShopDetail.Status.Open
            ),
            ShopDetail(
                6,
                3_000_000_000,
                56.497555,
                84.9828216,
                Instant.now(),
                ShopDetail.Status.ClosedForRepairs
            )
        )

        override fun getShop(itemId: Int): Result<ShopDetail, FuelError> {
            return Result.Success(ShopDetailRepository.RepositoryImpl.mockData.first { it.id == itemId })
        }
    }
}