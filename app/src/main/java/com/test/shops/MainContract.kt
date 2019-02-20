package com.test.shops

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.test.shops.entity.Shop

interface MainContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun publishDataList(data: ArrayList<Shop>)
        fun showInfoMessage(msg: String)
    }

    interface Presenter {
        // User actions
        fun listItemClicked(shop: Shop?)

        // Model updates
        fun onViewCreated()

        fun onDestroy()
    }

    interface Interactor {
        fun loadSopsList(interactorOutput: (result: Result<ArrayList<Shop>, FuelError>) -> Unit)
    }

    interface InteractorOutput {
        fun onQuerySuccess(data: ArrayList<Shop>)
        fun onQueryError()
    }
}