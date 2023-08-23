package com.activ.activsewa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.activ.activsewa.databinding.ItemTransactionCardBinding
import com.activ.activsewa.databinding.ItemTransactionCardSalesBinding
import com.activ.activsewa.model.response.transaction.Data
import com.activ.activsewa.utils.Helpers.formatPrice

class TransactionSalesAdapter (private var listData: List<Data>,
                               private val itemAdapterCallback: ItemAdapterCallback
) :
RecyclerView.Adapter<TransactionSalesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTransactionCardSalesBinding =
            ItemTransactionCardSalesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    class ViewHolder(view: ItemTransactionCardSalesBinding) : RecyclerView.ViewHolder(view.root) {
        // Holds the TextView that will add each item to
        private val tvCodeReference = view.tvCodeReference
        private val tvTotalSewa = view.tvTotalSewa
        private val txtPengajuan = view.txtStatus
        private val tvStatus = view.tvStatus

        @SuppressLint("SetTextI18n")
        fun bind(data : Data, itemAdapterCallback: ItemAdapterCallback) {

            itemView.apply {
                tvCodeReference.text = data.code_reference
                tvTotalSewa.formatPrice(data.payment?.total_harga.toString())
                txtPengajuan.text = "Total Sewa: "
                tvStatus.text = data.status_verification
                itemView.setOnClickListener{
                    itemAdapterCallback.onClickTransaction(it, data)
                }
            }
        }

    }

    interface ItemAdapterCallback {
        fun onClickTransaction(v: View, data: Data)
    }
}