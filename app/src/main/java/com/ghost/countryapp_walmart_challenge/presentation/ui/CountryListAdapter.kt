package com.ghost.countryapp_walmart_challenge.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ghost.countryapp_walmart_challenge.databinding.ItemCountryCardBinding
import com.ghost.countryapp_walmart_challenge.databinding.ItemCountryHeaderBinding
import com.ghost.countryapp_walmart_challenge.utils.CountryListItem


class CountryListAdapter(
    private var items: List<CountryListItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    sealed class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class HeaderViewHolder(val binding: ItemCountryHeaderBinding) : ViewHolder(binding.root)
        class CountryViewHolder(val binding: ItemCountryCardBinding) : ViewHolder(binding.root)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CountryListItem.Header -> 0
            is CountryListItem.Country -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val binding = ItemCountryHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ViewHolder.HeaderViewHolder(binding)
            }
            else -> {
                val binding = ItemCountryCardBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ViewHolder.CountryViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is CountryListItem.Header -> {
                val headerHolder = holder as ViewHolder.HeaderViewHolder
                headerHolder.binding.tvHeader.text = item.title
            }
            is CountryListItem.Country -> {
                val countryHolder = holder as ViewHolder.CountryViewHolder
                val country = item.country
                with(countryHolder.binding) {
                    tvNameRegion.text = "${country.name}, ${country.region}"
                    tvCode.text = country.code
                    tvCapital.text = country.capital
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<CountryListItem>) {
        items = newList
        notifyDataSetChanged()
    }
}

