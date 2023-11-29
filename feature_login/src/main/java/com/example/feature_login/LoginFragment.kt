package com.example.feature_login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.core.di.CoreInjectHelper
import com.example.core.utils.Constants.ADMINISTRATOR
import com.example.core.utils.Constants.ASPIRANT
import com.example.core.utils.Constants.SUPERVISOR
import com.example.di.DaggerLoginComponent
import com.google.firebase.auth.FirebaseAuth
import com.postgraduate.cabinet.feature_login.R
import com.postgraduate.cabinet.feature_login.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            if (viewModel.getUserInfo() == ASPIRANT) {
                findNavController().navigate(R.id.go_to_profile)
            } else if (viewModel.getUserInfo() == SUPERVISOR) {
                findNavController().navigate(R.id.go_to_event_list)
            } else if (viewModel.getUserInfo() == ADMINISTRATOR) {
                findNavController().navigate(R.id.go_to_administrator)
            }
        }
        binding.logInButton.setOnClickListener {
            login()
        }
        binding.eyeImageView.setOnClickListener {
            changeEyeState(it)
        }
    }

    private fun login() {
        val email = binding.loginInputEditText.text.toString().trimEnd()
        val password = binding.password.text.toString().trimEnd()
        isProgressBarVisible(true)
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                handleUserLogin(email)
            }
        }.addOnFailureListener { exception ->
            isProgressBarVisible(false)
            Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun handleUserLogin(email: String) {
        lifecycleScope.launch {
            viewModel.determineUserType(email)
        }
        viewModel.userType.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    UserType.ASPIRANT -> {
                        navigateToProfile()
                        isProgressBarVisible(false)
                    }

                    UserType.SUPERVISOR -> {
                        navigateToEventList()
                        isProgressBarVisible(false)
                    }

                    UserType.ADMINISTRATOR -> {
                        navigateToAdministrator()
                        isProgressBarVisible(false)
                    }
                }
            }
        }
    }

    private fun isProgressBarVisible(isProgressBarVisible: Boolean) {
        binding.logInButton.isClickable = !isProgressBarVisible
        if (isProgressBarVisible) {
            binding.logInButton.backgroundTintList = ContextCompat.getColorStateList(
                requireContext(),
                com.postgraduate.cabinet.ui.R.color.gray
            )
        } else {
            binding.logInButton.backgroundTintList = ContextCompat.getColorStateList(
                requireContext(),
                com.postgraduate.cabinet.ui.R.color.brand_yellow
            )
        }
        binding.progressBar.isVisible = isProgressBarVisible
    }

    private fun navigateToProfile() {
        findNavController().navigate(R.id.go_to_profile)
    }

    private fun navigateToEventList() {
        findNavController().navigate(R.id.go_to_event_list)
    }

    private fun navigateToAdministrator() {
        findNavController().navigate(R.id.go_to_administrator)
    }


    private fun changeEyeState(it: View) {
        it.isActivated = !it.isActivated
        if (!it.isActivated) {
            binding.password.transformationMethod =
                PasswordTransformationMethod.getInstance()
            binding.password.setSelection(binding.password.text!!.length)
        } else {
            binding.password.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            binding.password.setSelection(binding.password.text!!.length)
        }
    }


    private fun initDagger() {
        DaggerLoginComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}
