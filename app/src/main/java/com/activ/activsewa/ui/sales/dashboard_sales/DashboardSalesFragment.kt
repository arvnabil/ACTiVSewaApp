package com.activ.activsewa.ui.sales.dashboard_sales
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.activ.activsewa.R
import com.activ.activsewa.databinding.FragmentDashboardSalesBinding
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.name
import com.activ.activsewa.utils.PreferenceHelper.roles

class DashboardSalesFragment : Fragment() {
    private var _binding: FragmentDashboardSalesBinding? = null
    var progressDialog: Dialog? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardSalesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initView()
        return root
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)
        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        val prefs = PreferenceHelper.defaultPreference(requireContext())
        val name = prefs.name
        val roles = prefs.roles
        binding.txtHalo.text = "Halo $roles $name!"
        binding.btnTransaction.setOnClickListener{
            val directions = DashboardSalesFragmentDirections.actionNavigationDashboardSalesToNavigationTransaction()
            view?.findNavController()?.navigate(directions)
        }
        binding.btnPenyewa.setOnClickListener{
            val directions = DashboardSalesFragmentDirections.actionNavigationDashboardSalesToCustomerFragment()
            view?.findNavController()?.navigate(directions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}