package com.activ.activsewa.ui.success


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.activ.activsewa.MainActivity
import com.activ.activsewa.databinding.FragmentSuccessDaftarBinding
import com.activ.activsewa.ui.auth.AuthActivity


class SuccessDaftarFragment : Fragment() {
    private var time: Long = 0
    private lateinit var binding: FragmentSuccessDaftarBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSuccessDaftarBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.btnHome.setOnClickListener {
            val home = Intent(activity, MainActivity::class.java)
            startActivity(home)
            activity?.finish()
        }

        binding.btnLihatProfile.setOnClickListener {
            val profil = Intent(activity, MainActivity::class.java)
            startActivity(profil)
        }

        return binding.root
    }

}