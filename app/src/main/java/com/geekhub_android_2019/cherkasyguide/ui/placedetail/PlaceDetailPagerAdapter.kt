package com.geekhub_android_2019.cherkasyguide.ui.placedetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.geekhub_android_2019.cherkasyguide.R


class PlaceDetailPagerAdapter(private val photos: List<String>) : PagerAdapter() {

    override fun getCount(): Int {
        return photos.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val photo = photos.get(position)
        val inflater = LayoutInflater.from(collection.context)
        val layout =
            inflater.inflate(R.layout.image_viewpager_item, collection, false) as ViewGroup
        collection.addView(layout)
        val imageView = layout.findViewById<ImageView>(R.id.imageView_photoLarge)
        Glide.with(imageView).load(photo).into(imageView)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}
