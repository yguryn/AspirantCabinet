package com.example.feature_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.postgraduate.cabinet.feature_profile.databinding.FragmentSecondProfileBinding

class ProfileSecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondProfileBinding.inflate(layoutInflater)
        return binding.root
    }
}