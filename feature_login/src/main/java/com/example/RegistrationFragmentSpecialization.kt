package com.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.postgraduate.cabinet.feature_login.R
import com.postgraduate.cabinet.feature_login.databinding.FragmentRegistrationSpecializationBinding

class RegistrationFragmentSpecialization : Fragment() {

    private lateinit var binding: FragmentRegistrationSpecializationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationSpecializationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_registrationSpecializationFragment_to_additionalFragment)
        }
    }
}