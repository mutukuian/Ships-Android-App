package com.example.shipsapi.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shipsapi.R

import com.example.shipsapi.databinding.ItemSingleShipBinding
import com.example.shipsapi.retrofit.shipsresponse.ShipsResponseListItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import javax.inject.Inject

class ShipsAdapter @Inject constructor() : RecyclerView.Adapter<ShipsAdapter.ShipsViewHolder>() {

    class ShipsViewHolder(private val binding: ItemSingleShipBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: ShipsResponseListItem) {
            binding.apply {
                tvShipName.text = item.ship_name
                shipImage.load(item.image){
                    crossfade(true)
                    crossfade(500)
                }

                singleShipCard.setOnClickListener {
                    displayBottomSheetDialog(item)
                }
            }
        }

        private fun displayBottomSheetDialog(item: ShipsResponseListItem) {
            val bottomSheet = BottomSheetDialog(itemView.context)
            val view2: View =
                LayoutInflater.from(itemView.context).inflate(R.layout.ship_bottom_sheet, null)
            val shipImageSheet: ImageView = view2.findViewById(R.id.shipImageSheet)
            val shipNameSheet: TextView = view2.findViewById(R.id.shipNameSheet)
            val shipStatusSheet: TextView = view2.findViewById(R.id.shipStatusSheet)
            val shipYearBuiltSheet: TextView = view2.findViewById(R.id.yearBuilt)
            val shipPort: TextView = view2.findViewById(R.id.homePort)
            val shipModel: TextView = view2.findViewById(R.id.shipModel)
            Glide.with(itemView.context)
                .load(item.image)
                .apply(RequestOptions.centerCropTransform())
//                .placeholder(R.drawable.ship_placeholder)
                .into(shipImageSheet)

            shipYearBuiltSheet.text = item.year_built.toString()
            if (item.home_port == null) {
                shipPort.text = "N/A"
            } else {
                shipPort.text = item.home_port
            }

            if (item.ship_model == null) {
                shipModel.text = "N/A"
            } else {
                shipModel.text = item.ship_model
            }

            shipNameSheet.text = item.ship_name
            if (item.active) {
                shipStatusSheet.setTextColor(Color.GREEN)
                shipStatusSheet.text = "Active"
            } else {
                shipStatusSheet.setTextColor(Color.RED)
                shipStatusSheet.text = "InActive"

            }
            bottomSheet.setContentView(view2)
            bottomSheet.show()
        }

    }



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
}
