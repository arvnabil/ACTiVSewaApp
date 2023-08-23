package com.activ.activsewa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.activ.activsewa.BuildConfig
import com.activ.activsewa.R
import com.activ.activsewa.databinding.ItemProductCardTrashBinding
import com.activ.activsewa.model.response.pengajuan.Data
import com.activ.activsewa.utils.FragmentUtil
import com.activ.activsewa.utils.Helpers.formatPrice
import com.bumptech.glide.Glide


class FilingAdapter (private var listData: List<Data>,
                     private val itemAdapterCallback: ItemAdapterCallback
) :
RecyclerView.Adapter<FilingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemProductCardTrashBinding =
            ItemProductCardTrashBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    class ViewHolder(view: ItemProductCardTrashBinding) : RecyclerView.ViewHolder(view.root) {
        // Holds the TextView that will add each item to
        val ivPoster = view.ivProduct
        val tvProductName = view.tvProductName
        val tvDurasi = view.tvDurasi
        val tvStatus = view.tvStatus
        val tvBrand = view.tvBrand
        val tvPrice = view.tvPrice
        val btnTrash = view.ivTrash

        @SuppressLint("SetTextI18n")
        fun bind(data: Data, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                itemView.apply {
                    var url = BuildConfig.IMAGE_URL
                    var foto = data.product?.foto
                    var path: String = url + foto
                    Glide.with(context)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.drawable.iv_logigroup)
                        .into(ivPoster)
                    val durasi = data.product?.durasi
                    tvProductName.text = data.product?.nama_produk
                    tvDurasi.text = "Durasi $durasi Hari"
                    tvStatus.text = data.product?.status

                    tvBrand.text = data.product?.brand?.name
                    data?.product?.harga?.let { tvPrice.formatPrice(it) }
                    btnTrash.setOnClickListener {
                        itemAdapterCallback.onClickProductTrash(it, data)
                        FragmentUtil.refreshFragment(context)
                    }
                    itemView.setOnClickListener {
                        itemAdapterCallback.onClickProductFiling(it, data)
                    }
                }
            }

        }
    }
    interface ItemAdapterCallback {
        fun onClickProductFiling(v: View, data: Data)
        fun onClickProductTrash(v: View, data: Data)
    }


}