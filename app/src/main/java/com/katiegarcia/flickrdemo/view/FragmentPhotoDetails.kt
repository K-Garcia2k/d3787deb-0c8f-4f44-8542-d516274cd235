package com.katiegarcia.flickrdemo.view

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.katiegarcia.flickrdemo.R
import com.katiegarcia.flickrdemo.databinding.FragmentPhotoDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


/*
Simple ViewBinding Fragment for loading in higher resolution image and more details
than could be displayed on the previous screen.
Loading bar should be present for slower connections.
 */
@AndroidEntryPoint
class FragmentPhotoDetails : Fragment(R.layout.fragment_photo_details) {

    //Photo Object passed from the gallery.
    private val args by navArgs<FragmentPhotoDetailsArgs>()

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPhotoDetailsBinding.bind(view)

        binding.apply {
            val photo = args.photo

            /*
q               Loading into imageview with a listener so that we can load the views in when
                appropriate for the user to see them.
             */
            Glide.with(this@FragmentPhotoDetails)
                .load(photo.urlC)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbPhotoDetail.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbPhotoDetail.isVisible = false
                        tvDate.isVisible = true
                        tvTitle.isVisible = true
                        if (!tvDescription.text.isNullOrBlank()) {
                            tvDescription.isVisible = true
                        }
                        tvDescriptionUserName.isVisible = true
                        tvTags.isVisible = true
                        return false
                    }

                })
                .into(ivPhotoDetail)

            if (photo.title != null) {
                tvTitle.text = photo.title
            }

            if (photo.dateupload != null) {
                val date = Date(photo.dateupload!! * 1000)
                val format = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
                tvDate.text = format.format(date)
            }

            /*
            FLICKR API allows for HTML to be present in the description.
            Setup to automatically handle any links that may be present.
             */
            if (photo.description != null) {
                if (photo.description!!.Content != null) {
                    tvDescription.text = HtmlCompat.fromHtml(
                        photo.description!!.Content!!,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }
            }

            tvDescriptionUserName.text = photo.ownername
            tvTags.text = photo.tags!!.replace(" ", ", ") //Easier readability

        }


    }

}