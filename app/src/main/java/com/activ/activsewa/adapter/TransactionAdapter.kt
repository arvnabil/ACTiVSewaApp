package com.activ.activsewa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.activ.activsewa.databinding.ItemTransactionCardBinding
import com.activ.activsewa.model.response.transaction.Data

class TransactionAdapter (private var listData: List<Data>,
                          private val itemAdapterCallback: ItemAdapterCallback
) :
RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTransactionCardBinding =
            ItemTransactionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    class ViewHolder(view: ItemTransactionCardBinding) : RecyclerView.ViewHolder(view.root) {
        // Holds the TextView that will add each item to
        private val tvCodeReference = view.tvCodeReference
        private val tvStatusVerification = view.tvStatusVerification
        private val txtPengajuan = view.txtStatus

        @SuppressLint("SetTextI18n")
        fun bind(data : Data, itemAdapterCallback: ItemAdapterCallback) {

            itemView.apply {
                tvCodeReference.text = data.code_reference
                tvStatusVerification.text = data.status_verification
                txtPengajuan.text = "Status pengajuan: "
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