package com.test.shops.interactor

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.test.shops.MainContract
import com.test.shops.entity.Shop
import com.test.shops.repository.ShopRepository

class MainInteractor(private val repository: ShopRepository.RepositoryImpl) : MainContract.Interactor {

    override fun loadSopsList(interactorOutput: (result: Result<ArrayList<Shop>, FuelError>) -> Unit) {
        interactorOutput(repository.getShopsList())
    }

}