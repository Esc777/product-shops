package com.test.shops.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.shops.R
import com.test.shops.entity.Shop
import kotlinx.android.synthetic.main.card_view_custom_layout.view.*


class ShopsListAdapter(private var listener: (Shop?) -> Unit, private var dataList: ArrayList<Shop>?) :
    RecyclerView.Adapter<ShopsListAdapter.ViewHolder>(), Filterable {

    private var dataListFiltered: ArrayList<Shop>? = dataList

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNumber: TextView? = itemView.tv_shop_number_card_view_custom_layout
        val tvName: TextView? = itemView.tv_shop_name_card_view_custom_layout
        val tvAddress: TextView? = itemView.tv_shop_address_card_view_custom_layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewRow = LayoutInflater.from(parent.context).inflate(R.layout.card_view_custom_layout, parent, false)
        return ViewHolder(viewRow)
    }

    override fun getItemCount(): Int = dataListFiltered?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNumber?.text = dataListFiltered.let { "Номер магазина: " + it?.get(position)?.number.toString() }
        holder.tvName?.text = dataListFiltered.let { "Название: " + it?.get(position)?.name }
        holder.tvAddress?.text = dataListFiltered.let { "Адресс: " + it?.get(position)?.address }
        holder.itemView.setOnClickListener { listener(dataListFiltered?.get(position)) }
    }

    fun updateData(list: ArrayList<Shop>) {
        this.dataList = list
        this.dataListFiltered = list
        this.notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence.toString()
                dataListFiltered = if (charString.isEmpty()) {
                    dataList
                } else {
                    val filteredList: ArrayList<Shop> = arrayListOf()
                    if (!dataList.isNullOrEmpty()) {
                        for (row in dataList!!) {
                            if (row.address.toLowerCase().contains(charString.toLowerCase()) or row.number.toString().contains(
                                    charString
                                )
                            ) {
                                filteredList.add(row)
                            }
                        }
                    }

                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = dataListFiltered
                return filterResults
            }

            override fun publishResults(CharSequence: CharSequence?, filterResults: FilterResults?) {


                if (filterResults == null) dataListFiltered = arrayListOf()
                else dataListFiltered = (filterResults?.values) as ArrayList<Shop>

                notifyDataSetChanged()
            }


        }
    }
}