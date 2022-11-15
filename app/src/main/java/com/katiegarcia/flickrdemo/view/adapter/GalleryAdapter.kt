package com.katiegarcia.flickrdemo.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.katiegarcia.flickrdemo.R
import com.katiegarcia.flickrdemo.module.ApplicationModule
import com.katiegarcia.flickrdemo.databinding.ItemPhotoBinding
import com.katiegarcia.flickrdemo.model.data.FlickrPhoto
import com.katiegarcia.flickrdemo.model.data.events.UserSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus

/*
Adapter - Implements PagingDataAdapter for use with PagerSource,
Handled and passed by PhotoGalleryViewModel
 */
class GalleryAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<FlickrPhoto, GalleryAdapter.GalleryHolder>(comparator) {

    /*
    Generic comparator to efficiently recycle views and avoid reloading data.
     */
    companion object {
        private val comparator = object : DiffUtil.ItemCallback<FlickrPhoto>() {
            override fun areItemsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto) =
                oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(photo: FlickrPhoto)
    }

    /*
    Holder for binding, acquires user data on load to minimize amount of calls before the UI can
    load
     */
    inner class GalleryHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun bind(image: FlickrPhoto) {
            binding.apply {
                tvOwnerName.text = image.ownername
                if (image.tags != null) {
                    tvTags.text = image.tags!!.replace(" ", ", ")
                }

                //Aquiring the url based on the farm after getting the user data per photo object
                GlobalScope.launch { // CoroutineScope
                    image.getUser()

                    if(image.user != null){
                        withContext(Dispatchers.Main) {
                            Glide.with(itemView)
                                .load(image.user!!.getURL())
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .error(R.drawable.ic_baseline_person_outline_24)
                                .into(ivUserImage)
                        }
                    }
                }


                Glide.with(itemView)
                    .load(image.urlQ)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(com.google.android.material.R.drawable.mtrl_ic_error)
                    .into(ivPhoto)

                /*
                Using GreenRobot Eventbus to avoid having to implement clunky callbacks into adapter
                and fragment/activites.
                Currently handled by FragmentPhotoGallery
                 */
                ivUserImage.setOnClickListener()
                {
                    if (image.owner != null) {
                        EventBus.getDefault().post(UserSearch(image.owner!!))
                    }

                }

                tvOwnerName.setOnClickListener()
                {
                    if (image.owner != null) {
                        EventBus.getDefault().post(UserSearch(image.owner!!))
                    }
                }

            }
        }

        init {
            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val item = getItem(pos)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }

        }
    }

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GalleryHolder(binding)
    }


}