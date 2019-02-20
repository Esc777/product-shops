package com.test.shops.interactor

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.test.shops.DetailContract
import com.test.shops.entity.ShopDetail
import com.test.shops.repository.ShopDetailRepository

class DetailInteractor(private val repository: ShopDetailRepository.RepositoryImpl) : DetailContract.Interactor {

    override fun loadShopDetail(shopId: Int, interactorOutput: (result: Result<ShopDetail, FuelError>) -> Unit) {
        interactorOutput(repository.getShop(shopId))
    }

}