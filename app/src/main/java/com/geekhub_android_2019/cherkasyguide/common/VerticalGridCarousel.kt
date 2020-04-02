package com.geekhub_android_2019.cherkasyguide.common

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
internal class VerticalGridCarousel(ctx: Context?) : Carousel(ctx) {

    override fun createLayoutManager(): LayoutManager =
        GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
}
