package com.geekhub_android_2019.cherkasyguide.ui.models

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.common.BaseEpoxyHolder
import com.google.android.material.button.MaterialButton

@EpoxyModelClass(layout = R.layout.fragment_routes_create_new)
abstract class TextButtonModel: EpoxyModelWithHolder<TextButtonModel.VH>() {

    @EpoxyAttribute
    var titleId: Int? = null

    @EpoxyAttribute(Option.DoNotHash)
    lateinit var listener: View.OnClickListener

    override fun bind(holder: VH) {
        holder.button.setText(titleId!!)
        holder.button.setOnClickListener(listener)
    }

    class VH: BaseEpoxyHolder() {
        val button by bind<MaterialButton>(R.id.createRoute)
    }
}
