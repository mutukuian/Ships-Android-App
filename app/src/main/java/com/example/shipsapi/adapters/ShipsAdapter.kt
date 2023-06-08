package com.example.shipsapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide

import com.example.shipsapi.databinding.ItemSingleShipBinding
import com.example.shipsapi.retrofit.shipsresponse.ShipsResponseListItem
import javax.inject.Inject

class ShipsAdapter @Inject constructor() : RecyclerView.Adapter<ShipsAdapter.ShipsViewHolder>() {

    inner class ShipsViewHolder(private val binding: ItemSingleShipBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: ShipsResponseListItem) {
            binding.apply {
                tvShipName.text = item.ship_name
                shipImage.load(item.image){
                    crossfade(true)
                    crossfade(500)
                }
//                Glide.with(itemView)
//                    .load(item.image)
//                    .into(binding.shipImage)
            }
        }

    }

    private val differCallback = object :DiffUtil.ItemCallback<ShipsResponseListItem>(){
        override fun areItemsTheSame(
            oldItem: ShipsResponseListItem,
            newItem: ShipsResponseListItem
        ): Boolean {
            return oldItem.ship_id == newItem.ship_id
        }

        override fun areContentsTheSame(
            oldItem: ShipsResponseListItem,
            newItem: ShipsResponseListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipsViewHolder {
       return ShipsViewHolder(
           ItemSingleShipBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ShipsViewHolder, position: Int) {
       holder.bind(differ.currentList[position])
    }
}