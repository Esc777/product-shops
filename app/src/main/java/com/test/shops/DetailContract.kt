package com.test.shops

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.test.shops.entity.Shop
import com.test.shops.entity.ShopDetail

interface DetailContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun publishShopData(shop: Shop)
        fun showInfoMessage(msg: String)
    }

    interface Presenter {
        // User actions
        fun backButtonClicked()

        // Model updates
        fun onViewCreated(shop: Shop)

        fun onDestroy()
    }

    interface Interactor {
        fun loadShopDetail(shopId: Int, interactorOutput: (result: Result<ShopDetail, FuelError>) -> Unit)
    }

    interface InteractorOutput {
        fun onQuerySuccess(data: Shop)
        fun onQueryError()
    }
}