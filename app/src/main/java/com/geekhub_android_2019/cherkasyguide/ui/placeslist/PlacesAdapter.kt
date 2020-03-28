package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Place
import kotlinx.android.synthetic.main.list_places_item.view.*


class PlacesAdapter(var place: List<Place> , val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

    interface OnItemClickListener {
        fun onClick(place: Place)
    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): PlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_places_item , parent , false)
        return PlacesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return place.size
    }

    override fun onBindViewHolder(holder: PlacesViewHolder , position: Int) {
        holder.bind(place[position])
    }


    inner class PlacesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) ,
        View.OnClickListener {
      /*  private val vibrantView: TextView? = null
        private var vibrantLightView: TextView? = null
        private var vibrantDarkView: TextView? = null
        private var mutedView: TextView? = null
        private var mutedLightView: TextView? = null
        private var mutedDarkView: TextView? = null*/

        init {
            view.setOnClickListener(this)
        }

        fun bind(place: Place) {
            view.textViewName.text = place.name
            Glide.with(this.view)
                .asBitmap()
                .load(place.photoSmallUrl)
                .into(view.imageViewPlaceIcon)


        }

     /*   private fun toggleImages(photoSmallUrl: Int) {
            val bitmap = BitmapFactory.decodeResource(
                getResources(),
                photoSmallUrl
            )
            view.imageViewPlaceIcon.setImageBitmap(bitmap)
            setToolbarColor(bitmap)
            createPaletteAsync(bitmap)
        }

        private fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

        private fun createPaletteAsync(bitmap: Bitmap) {
            Palette.from(bitmap).generate { palette ->
                val defaultValue = 0x000000
                val vibrant = palette!!.getVibrantColor(defaultValue)
                val vibrantLight = palette.getLightVibrantColor(defaultValue)
                val vibrantDark = palette.getDarkVibrantColor(defaultValue)
                val muted = palette.getMutedColor(defaultValue)
                val mutedLight = palette.getLightMutedColor(defaultValue)
                val mutedDark = palette.getDarkMutedColor(defaultValue)

                vibrantView?.setBackgroundColor(vibrant)
                vibrantLightView?.setBackgroundColor(vibrantLight)
                vibrantDarkView?.setBackgroundColor(vibrantDark)
                mutedView?.setBackgroundColor(muted)
                mutedLightView?.setBackgroundColor(mutedLight)
                mutedDarkView?.setBackgroundColor(mutedDark)
            }
        }

        fun setToolbarColor(bitmap: Bitmap) {
            val vibrantSwatch = createPaletteSync(bitmap).vibrantSwatch
            with(view.findViewById<Toolbar>(R.id.toolbar)) {
                setBackgroundColor(
                    vibrantSwatch?.rgb ?: ContextCompat.getColor(
                        context ,
                        R.color.colorSecondaryLight
                    )
                )
                setTitleTextColor(
                    vibrantSwatch?.titleTextColor ?: ContextCompat.getColor(
                        context ,
                        R.color.white
                    )
                )
            }*/


            override fun onClick(v: View?) {
                clickListener.onClick(place[adapterPosition])
            }
        }
    }
