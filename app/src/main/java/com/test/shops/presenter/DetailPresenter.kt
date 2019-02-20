package com.test.shops.presenter

import com.github.kittinunf.result.Result
import com.test.shops.BaseApplication
import com.test.shops.DetailContract
import com.test.shops.entity.Shop
import com.test.shops.interactor.DetailInteractor
import com.test.shops.repository.ShopDetailRepository
import ru.terrakok.cicerone.Router

class DetailPresenter(private var view: DetailContract.View?) : DetailContract.Presenter,
    DetailContract.InteractorOutput {

    private var interactor: DetailContract.Interactor? = DetailInteractor(ShopDetailRepository.RepositoryImpl)
    private val router: Router? by lazy { BaseApplication.INSTANCE.cicerone.router }

    override fun backButtonClicked() {
        router?.exit()
    }

    override fun onViewCreated(shop: Shop) {
        view?.showLoading()

        interactor?.loadShopDetail(shopId = shop.id) { result ->
            when (result) {
                is Result.Failure -> {
                    this.onQueryError()
                }
                is Result.Success -> {
                    val rez = Shop(shop.id, shop.number, shop.name, shop.address, result.value)
                    this.onQuerySuccess(rez)
                }
            }
        }
    }

    override fun onDestroy() {
        view = null
        interactor = null
    }

    override fun onQuerySuccess(data: Shop) {
        view?.hideLoading()
        view?.publishShopData(data)
    }

    override fun onQueryError() {
        view?.hideLoading()
        view?.showInfoMessage("Error when loading data")
    }
}