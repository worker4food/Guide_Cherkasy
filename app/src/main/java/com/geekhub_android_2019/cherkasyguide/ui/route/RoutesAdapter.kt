package com.geekhub_android_2019.cherkasyguide.ui.route

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Route

import kotlinx.android.synthetic.main.fragment_routes_list_item.view.*

class RoutesAdapter(private val listener: (Route) -> Unit)
    : ListAdapter<Route, RoutesAdapter.ViewHolder>(RoutesDiffCallback) {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            listener(v.tag as Route)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_routes_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.mIdView.text = item.name
        holder.mContentView.text = item.id

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

    object RoutesDiffCallback: DiffUtil.ItemCallback<Route>() {
        override fun areItemsTheSame(oldItem: Route, newItem: Route) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Route, newItem: Route) = oldItem == newItem
    }
}

