package com.test.shops.presenter

import com.github.kittinunf.result.Result
import com.test.shops.BaseApplication
import com.test.shops.MainContract
import com.test.shops.entity.Shop
import com.test.shops.interactor.MainInteractor
import com.test.shops.repository.ShopRepository
import com.test.shops.view.activities.DetailActivity
import ru.terrakok.cicerone.Router

class MainPresenter(private var view: MainContract.View?) : MainContract.Presenter, MainContract.InteractorOutput {

    private var interactor: MainContract.Interactor? = MainInteractor(ShopRepository.RepositoryImpl)
    private val router: Router? by lazy { BaseApplication.INSTANCE.cicerone.router }

    override fun listItemClicked(shop: Shop?) {
        router?.navigateTo(DetailActivity.TAG, shop)
    }

    override fun onViewCreated() {
        view?.showLoading()

        interactor?.loadSopsList { result ->
            when (result) {
                is Result.Failure -> {
                    this.onQueryError()
                }
                is Result.Success -> {
                    this.onQuerySuccess(result.value)
                }
            }
        }
    }

    override fun onDestroy() {
        view = null
        interactor = null
    }

    override fun onQuerySuccess(data: ArrayList<Shop>) {
        view?.hideLoading()
        view?.publishDataList(data)
    }

    override fun onQueryError() {
        view?.hideLoading()
        view?.showInfoMessage("Error when loading data")
    }
}