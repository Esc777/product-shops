package com.test.shops.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.shops.BaseApplication
import com.test.shops.MainContract
import com.test.shops.R
import com.test.shops.entity.Shop
import com.test.shops.presenter.MainPresenter
import com.test.shops.view.adapters.ShopsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.toast
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward


class MainActivity : BaseActivity(), MainContract.View {

    companion object {
        const val TAG: String = "MainActivity"
    }

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command) {
                if (command is Forward) {
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                val data = (command.transitionData as Shop)

                when (command.screenKey) {
                    DetailActivity.TAG -> startActivity(
                        Intent(this@MainActivity, DetailActivity::class.java)
                            .putExtra("data", data as Parcelable)
                    )
                    else -> Log.e("Cicerone", "Unknown screen: " + command.screenKey)
                }
            }
        }
    }

    private var presenter: MainContract.Presenter? = null
    private val toolbar: Toolbar by lazy { toolbar_toolbar_view }
    private val recyclerView: RecyclerView by lazy { rv_shops_list_activity_main }
    private lateinit var adapter: ShopsListAdapter
    private val progressBar: ProgressBar by lazy { prog_bar_loading_shops_activity_main }
    //private val searchView: SearchView by lazy { toolbar_search_view }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.test.shops.R.layout.activity_main)

        presenter = MainPresenter(this)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = ShopsListAdapter({ shop -> presenter?.listItemClicked(shop) }, arrayListOf())
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search View Hint")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter.filter(query)
                return false
            }

        })

        return true
    }

    override fun onResume() {
        super.onResume()
        presenter?.onViewCreated()
        BaseApplication.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        BaseApplication.INSTANCE.cicerone.navigatorHolder.removeNavigator()
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        presenter = null
        super.onDestroy()
    }

    override fun getToolbarInstance(): Toolbar? = toolbar

    override fun showLoading() {
        recyclerView.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        recyclerView.isEnabled = true
        progressBar.visibility = View.GONE
    }

    override fun publishDataList(data: ArrayList<Shop>) {
        (recyclerView.adapter as ShopsListAdapter).updateData(data)
    }

    override fun showInfoMessage(msg: String) {
        toast(msg)
    }
}
