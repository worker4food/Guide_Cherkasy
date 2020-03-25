package com.geekhub_android_2019.cherkasyguide.ui.routeslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.geekhub_android_2019.cherkasyguide.ui.routeslist.models.RouteItem

class RouteListAdapter(private val listener: (RouteItem) -> Unit) :
    ListAdapter<RouteItem, RouteItemHolder>(RoutesDiffCallback) {

    private val clickListener: View.OnClickListener

    init {
        clickListener = View.OnClickListener { v ->
            listener(v.tag as RouteItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return RouteItemHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: RouteItemHolder, position: Int) =
        holder.bind(getItem(position))

    override fun getItemViewType(position: Int) = getItem(position).resourceId

    object RoutesDiffCallback : DiffUtil.ItemCallback<RouteItem>() {
        override fun areItemsTheSame(oldItem: RouteItem, newItem: RouteItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RouteItem, newItem: RouteItem) =
            oldItem == newItem
    }
}
