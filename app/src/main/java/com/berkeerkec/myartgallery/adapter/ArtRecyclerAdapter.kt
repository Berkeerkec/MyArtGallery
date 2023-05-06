package com.berkeerkec.myartgallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.berkeerkec.myartgallery.databinding.ArtRowBinding
import com.berkeerkec.myartgallery.roomdb.Art
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    private val glide : RequestManager
) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {

    class ArtViewHolder(val binding : ArtRowBinding) : ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var arts : List<Art>
    get() = recyclerListDiffer.currentList
    set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val binding = ArtRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val art = arts[position]

        holder.itemView.apply {
            holder.binding.artRowArtNameText.text = "Art Name: ${art.name}"
            holder.binding.artRowArtistNameText.text = "Art Name: ${art.artistName}"
            holder.binding.artRowYearText.text = "Art Name: ${art.year}"
            glide.load(art.imageUrl).into(holder.binding.artRowImageView)
        }
    }
}