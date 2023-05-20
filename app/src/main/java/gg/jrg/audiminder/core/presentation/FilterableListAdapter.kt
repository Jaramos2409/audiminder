package gg.jrg.audiminder.core.presentation

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class FilterableListAdapter<T, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val filterField: (T) -> String
) : ListAdapter<T, VH>(diffCallback) {

    private var itemListFull: List<T> = listOf()

    fun setFullList(list: List<T>) {
        this.itemListFull = list
        submitList(itemListFull)
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            itemListFull
        } else {
            itemListFull.filter {
                filterField(it).contains(query, ignoreCase = true)
            }
        }
        submitList(filteredList)
    }
}


