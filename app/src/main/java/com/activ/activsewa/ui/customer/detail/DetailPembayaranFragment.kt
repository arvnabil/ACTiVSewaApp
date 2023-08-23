package com.activ.activsewa.ui.customer.detail

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.activ.activsewa.MainActivity
import com.activ.activsewa.databinding.FragmentDetailPembayaranBinding
import com.activ.activsewa.model.response.payment.PaymentResponse
import com.activ.activsewa.model.response.transaction.Data
import com.activ.activsewa.ui.customer.detail.contract.PembayaranContract
import com.activ.activsewa.ui.customer.detail.presenter.PembayaranPresenter
import com.activ.activsewa.utils.Helpers.formatPrice
import com.google.android.material.snackbar.Snackbar
import java.util.*

class DetailPembayaranFragment : Fragment() , PembayaranContract.View {
    var bundle:Bundle ?= null
    private var _binding: FragmentDetailPembayaranBinding? = null
    private val args: DetailPenyewaFragmentArgs by navArgs()
    private var datePickerDialog: DatePickerDialog? = null
    private var dateButton: Button? = null
    private val binding get() = _binding!!
    private lateinit var dataTransaction: Data
    private lateinit var pembayaranPresenter: PembayaranPresenter
    var progressDialog : Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailPembayaranBinding.inflate(inflater, container, false)
        binding.includeToolbar.btnBack.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        initDatePicker()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        dataTransaction = args.data!!
        binding.tvTotalSewa.formatPrice(dataTransaction.payment?.total_harga.toString())
        binding.btnTanggalSewa.setOnClickListener{
            openDatePicker(view)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTodaysDate(): String? {
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        var month = cal[Calendar.MONTH]
        month += 1
        val day = cal[Calendar.DAY_OF_MONTH]
        return makeDateString(day, month, year)
    }

    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month += 1
                val date = makeDateString(day, month, year)
                dateButton!!.text = date
            }
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]
        val style = AlertDialog.THEME_HOLO_LIGHT
        datePickerDialog = DatePickerDialog(requireContext(), style, dateSetListener, year, month, day)
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        val tanggalSewa = "$year-$month-$day"
        pembayaranPresenter = PembayaranPresenter(this, requireContext())
        dateButton = binding.btnTanggalSewa
        dateButton!!.text = getTodaysDate()
        binding.etMetode.setText("Online Midtrans")
        binding.btnBayarSekarang.setOnClickListener {
            val metode = "Online Midtrans"
            if (metode.isNullOrEmpty()) {
                binding.etMetode.requestFocus()
            } else {
                pembayaranPresenter.submitPayment(dataTransaction.id.toString(),tanggalSewa,metode)
            }
        }
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String? {
        return getMonthFormat(month) + " " + day + " " + year
    }

    private fun getMonthFormat(month: Int): String {
        if (month == 1) return "JAN"
        if (month == 2) return "FEB"
        if (month == 3) return "MAR"
        if (month == 4) return "APR"
        if (month == 5) return "MAY"
        if (month == 6) return "JUN"
        if (month == 7) return "JUL"
        if (month == 8) return "AUG"
        if (month == 9) return "SEP"
        if (month == 10) return "OCT"
        if (month == 11) return "NOV"
        return if (month == 12) "DEC" else "JAN"

        //default should never happen
    }

    fun openDatePicker(view: View?) {
        datePickerDialog!!.show()
    }

    override fun onPaymentSuccess(paymentResponse: PaymentResponse) {
        Log.d("payment Response", paymentResponse.toString())
        val directions = DetailPembayaranFragmentDirections.actionDetailPembayaranFragmentToSuccessPembayaranFragment()
        view?.findNavController()?.navigate(directions)
        val i = Intent(Intent.ACTION_VIEW,Uri.parse(paymentResponse.payment?.url.toString()))
        startActivity(i)
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "Melakukan pembayaran...", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onPaymentFailed(message: String) {
        Log.d("message Payment Failed", message)
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "$message", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}