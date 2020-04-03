package com.geekhub_android_2019.cherkasyguide.ui.models

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.common.BaseEpoxyHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

@EpoxyModelClass(layout = R.layout.fragment_routes_group_separator)
abstract class RouteHeaderModel: EpoxyModelWithHolder<RouteHeaderModel.VH>() {

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var listener: View.OnClickListener

    override fun bind(holder: VH) {
        holder.groupName.text = name

        holder.showMapBtn.setOnClickListener(listener)
    }

    class VH: BaseEpoxyHolder() {
        val groupName by bind<MaterialTextView>(
            R.id.groupName
        )
        val showMapBtn by bind<MaterialButton>(
            R.id.showRouteMap
        )
    }
}
