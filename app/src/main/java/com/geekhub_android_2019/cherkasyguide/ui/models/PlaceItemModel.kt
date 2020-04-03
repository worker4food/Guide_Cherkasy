package com.geekhub_android_2019.cherkasyguide.ui.models

import android.view.View
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.*
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.common.BaseEpoxyHolder
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

@EpoxyModelClass(layout = R.layout.fragment_placeedit_place_card)
abstract class PlaceItemModel: EpoxyModelWithHolder<PlaceItemModel.VH>() {

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var imageUrl: String

    @EpoxyAttribute
    var checked: Boolean = false

    @EpoxyAttribute(Option.DoNotHash)
    lateinit var listener: View.OnClickListener

    override fun bind(holder: VH) {
        holder.placeTitle.text = title
        holder.card.isChecked = checked

        Glide.with(holder.view)
            .load(imageUrl)
            .placeholder(R.drawable.download)
            .into(holder.placeThumb)

        holder.view.setOnClickListener(listener)
    }

    class VH: BaseEpoxyHolder() {
        val card by bind<MaterialCardView>(R.id.placeCard)
        val placeTitle by bind<MaterialTextView>(R.id.placeTitle)
        val placeThumb by bind<ImageView>(R.id.placeImage)
    }
}
