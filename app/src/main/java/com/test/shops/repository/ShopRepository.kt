package com.test.shops.repository

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.test.shops.entity.Shop

interface ShopRepository {
    fun getShopsList(): Result<ArrayList<Shop>, FuelError>

    object RepositoryImpl : ShopRepository {

        private val mockData: ArrayList<Shop> = arrayListOf(
            Shop(
                1,
                1,
                "Пятёрочка",
                "Партизанская, 6"
            ),
            Shop(
                2,
                2,
                "Ярче!",
                "Сибирская, 91"
            ),
            Shop(
                3,
                3,
                "Лента",
                "Проспект Мира, 30"
            ),
            Shop(
                4,
                4,
                "Азбука вкуса",
                "Иркутский тракт, 114/1"
            ),
            Shop(
                5,
                5,
                "Гастроном",
                "Комсомольский проспект, 37в"
            ),
            Shop(
                6,
                6,
                "Абрикос",
                "Комсомольский проспект, 7"
            )
        )

        override fun getShopsList(): Result<ArrayList<Shop>, FuelError> {
            return Result.Success(mockData)
        }
    }
}