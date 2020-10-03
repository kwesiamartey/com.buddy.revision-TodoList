package com.buddy.revision.ItemDiffUtil

import androidx.recyclerview.widget.DiffUtil
import com.buddy.revision.Entities.ItemsEntity


class ItemDiff(val newItems: List<ItemsEntity>, val oldItems:List<ItemsEntity>) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldItems.size
    }
    override fun getNewListSize(): Int {
       return newItems.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemPosition === newItemPosition
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newItems[newItemPosition] == oldItems[oldItemPosition]
    }
}