package com.geekhub_android_2019.cherkasyguide.ui.routelist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.geekhub_android_2019.cherkasyguide.models.Route
import com.geekhub_android_2019.cherkasyguide.ui.routelist.models.RouteItem
import kotlinx.android.synthetic.main.fragment_routes_create_new.view.*
import kotlinx.android.synthetic.main.fragment_routes_group_separator.view.*
import kotlinx.android.synthetic.main.fragment_routes_list_item.view.*

class RouteItemHolder(view: View, private val callback: View.OnClickListener) :
    RecyclerView.ViewHolder(view) {

    fun bind(item: RouteItem): Unit {
        itemView.tag = item
        itemView.setOnClickListener(callback)

        when (item) {
            is RouteItem.Regular -> bindRoute(item.route)
            is RouteItem.User -> bindRoute(item.route)
            is RouteItem.Separator -> bindSeparator(item.textResourceId)
            is RouteItem.CreateNew -> bindCreateNew()
        }
    }

    private fun bindRoute(r: Route): Unit = with(itemView) {
        routeNumber.text = "$adapterPosition"
        routeName.text = r.name
    }

    private fun bindSeparator(resourceId: Int): Unit =
        bindSeparator(itemView.resources.getString(resourceId))

    private fun bindSeparator(title: String) {
        itemView.groupName.text = title
    }

    private fun bindCreateNew() {
        itemView.createRoute.setOnClickListener { callback.onClick(itemView) }
    }

}
