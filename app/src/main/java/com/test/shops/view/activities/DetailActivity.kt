package com.test.shops.view.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.test.shops.BaseApplication
import com.test.shops.DetailContract
import com.test.shops.R
import com.test.shops.entity.Shop
import com.test.shops.presenter.DetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.toast
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command


class DetailActivity : BaseActivity(), DetailContract.View {

    companion object {
        const val TAG = "DetailActivity"
    }

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command) {
                if (command is Back) {
                    back()
                }
            }

            private fun back() {
                finish()
            }
        }
    }

    private var presenter: DetailContract.Presenter? = null
    private val progressBar: ProgressBar by lazy { prog_bar_loading_shops_activity_detail}
    private val toolbar: Toolbar by lazy { toolbar_toolbar_view }
    private val tvNumber: TextView? by lazy { tv_shop_number_activity_detail }
    private val tvName: TextView? by lazy { tv_shop_name_activity_detail }
    private val tvAddress: TextView? by lazy { tv_shop_address_activity_detail }
    private val tvAnnualProfit: TextView? by lazy { tv_shop_annual_profit_activity_detail }
    private val tvLatitude: TextView? by lazy { tv_shop_latitude_activity_detail }
    private val tvLongitude: TextView? by lazy { tv_shop_longitude_activity_detail }
    private val tvOpeningDate: TextView? by lazy { tv_shop_opening_date_activity_detail }
    private val tvStatus: TextView? by lazy { tv_shop_status_activity_detail }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        presenter = DetailPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        // add back arrow to toolbar
        supportActionBar?.let {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        // load invoking arguments
        val argument = intent.getParcelableExtra<Shop>("data")
        argument?.let { presenter?.onViewCreated(it) }

        BaseApplication.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        BaseApplication.INSTANCE.cicerone.navigatorHolder.removeNavigator()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                presenter?.backButtonClicked()
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        presenter = null
        super.onDestroy()
    }

    override fun getToolbarInstance(): Toolbar? = toolbar

    override fun publishShopData(shop: Shop) {
        tvNumber?.text = "Номер магазина: "+shop.number.toString()
        tvName?.text = "Название: "+shop.name
        tvAddress?.text = "Адресс: "+shop.address
        tvAnnualProfit?.text ="Годовой доход: "+shop.shopDetail?.annualProfit.toString()+"рублей"
        tvLatitude?.text = "Широта: "+shop.shopDetail?.latitude.toString()
        tvLongitude?.text = "Долгота: "+shop.shopDetail?.longitude.toString()
        tvOpeningDate?.text = "Дата открытия: "+shop.shopDetail?.openingDate.toString()
        tvStatus?.text = "Статус: "+shop.shopDetail?.status?.statusName
    }

    override fun showInfoMessage(msg: String) {
        toast(msg)
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}