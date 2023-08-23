package com.activ.activsewa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.activ.activsewa.BuildConfig
import com.activ.activsewa.R
import com.activ.activsewa.databinding.ItemProductHorizontalBinding
import com.activ.activsewa.model.response.home.Data
import com.bumptech.glide.Glide


class HomeAdapter (private var listData : List<Data>,
                   private val itemAdapterCallback : ItemAdapterCallback) :
RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemProductHorizontalBinding =
            ItemProductHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

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
    fun perangkatList(list: List<Data>) {
        listData = list
        notifyDataSetChanged()
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: ItemProductHorizontalBinding) : RecyclerView.ViewHolder(view.root) {
        // Holds the TextView that will add each item to
        private val ivPoster = view.ivPoster
        private val tvProductName = view.tvProductName
        private val tvDurasi = view.tvDurasi
        private val tvStatus = view.tvStatus
        //        val labelStatus = view.labelStatus
        @SuppressLint("SetTextI18n")
        fun bind(data : Data, itemAdapterCallback: ItemAdapterCallback) {

            itemView.apply {
                var url = BuildConfig.IMAGE_URL
                var foto = data.foto
                var path: String = url + foto
                Glide.with(context)
                    .load(path)
                    .centerCrop()
                    .placeholder(R.drawable.iv_logigroup)
                    .into(ivPoster)
                val durasi = data.durasi
                tvProductName.text = data.nama_produk
                tvDurasi.text = "Durasi $durasi Hari"
                tvStatus.text = data.status
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