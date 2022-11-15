package com.katiegarcia.flickrdemo.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.katiegarcia.flickrdemo.databinding.FooterRecyclerRetryBinding

/*
Footer adapter for use if the user's internet drops while scrolling an already loaded list.
Allowing them to retry.
 */
class GalleryLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<GalleryLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: FooterRecyclerRetryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(loadState: LoadState){
            binding.apply{
                pbRecyclerRetry.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                tvNophotosLoaded.isVisible = loadState !is LoadState.Loading
            }
        }

        init{
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = FooterRecyclerRetryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding)
    }
}