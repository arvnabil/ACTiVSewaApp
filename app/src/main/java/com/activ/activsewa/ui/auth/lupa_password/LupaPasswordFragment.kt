package com.activ.activsewa.ui.auth.lupa_password

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.activ.activsewa.databinding.FragmentLupaPasswordBinding
import com.activ.activsewa.ui.auth.AuthActivity

class LupaPasswordFragment : Fragment() {
    private lateinit var binding: FragmentLupaPasswordBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLupaPasswordBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.btnMasuk.setOnClickListener {
            val masuk = Intent(activity, AuthActivity::class.java)
            masuk.putExtra("page_request",0)
            startActivity(masuk)
        }

        return binding.root
    }
}