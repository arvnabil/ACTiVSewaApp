package com.activ.activsewa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.activ.activsewa.databinding.ItemPenyewaBinding
import com.activ.activsewa.model.response.brand.Data


class BrandAdapter (private var listData: List<Data>,
                    private val itemAdapterCallback: ItemAdapterCallback
) :
RecyclerView.Adapter<BrandAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPenyewaBinding =
            ItemPenyewaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("LongLogTag", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }
    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return listData.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun transactionList(list: List<Data>) {
        listData = list
        notifyDataSetChanged()
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: ItemPenyewaBinding) : RecyclerView.ViewHolder(view.root) {
        // Holds the TextView that will add each item to
        private val tvNama = view.tvPenyewa

        @SuppressLint("SetTextI18n")
        fun bind(data : Data, itemAdapterCallback: ItemAdapterCallback) {

            itemView.apply {
                tvNama.text = data.name
                itemView.setOnClickListener{
                    itemAdapterCallback.onClick(it, data)
                }
            }
        }

    }

    interface ItemAdapterCallback {
        fun onClick(v: View, data: Data)
    }
}