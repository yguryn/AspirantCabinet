package com.example.feature_administrator.aspirantlist.addaspirant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.core.model.Aspirant
import com.example.feature_administrator.R
import com.example.feature_administrator.databinding.FragmentAddAspirantBinding

class AddAspirantFragment : Fragment(R.layout.fragment_add_aspirant) {

    private lateinit var binding: FragmentAddAspirantBinding
    private var aspirant = Aspirant()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAspirantBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            binding.errorTextView.isVisible = false
            setAspirantObject()
            val bundle = Bundle().apply {
                putParcelable("aspirant", aspirant)
            }
            findNavController().navigate(R.id.action_addAspirant_to_addAspirantSecondStep, bundle)
        }
    }

    private fun setAspirantObject(){
        binding.apply {
            aspirant.name = nameEditText.text.toString()
            aspirant.surname = surnameEditText.text.toString()
            aspirant.middleName = middleNameEditText.text.toString()
            aspirant.phone = phoneNumberEditText.text.toString()
            aspirant.email = emailNumberEditText.text.toString()
        }
    }
}