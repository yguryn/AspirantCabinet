package com.example.feature_login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.core.di.CoreInjectHelper
import com.example.di.DaggerLoginComponent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.postgraduate.cabinet.feature_login.R
import com.postgraduate.cabinet.feature_login.databinding.FragmentLoginBinding
import javax.inject.Inject

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

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
        binding.logInButton.setOnClickListener {
            login()
        }
    }

    private fun register() {
        val email = binding.loginInputEditText.text.toString()
        val password = binding.password.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                findNavController().navigate(R.id.go_to_profile)
                db.collection("users")
                    .document(auth.uid!!)
                    .set(mapOf("role" to "Aspirant"))
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun login() {

        val email = binding.loginInputEditText.text.toString().trimEnd()
        val password = binding.password.text.toString().trimEnd()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModel.checkAspirant(email)
                viewModel.aspirant.observe(viewLifecycleOwner) {
                    viewModel.writeUserInfo(it.id, "Aspirant")
                    findNavController().navigate(R.id.go_to_profile)
                }

                viewModel.supervisor.observe(viewLifecycleOwner) {
                    viewModel.writeUserInfo(it.id, "Supervisor")
                    findNavController().navigate(R.id.go_to_profile)
                }

                viewModel.administrator.observe(viewLifecycleOwner) {
                    viewModel.writeUserInfo(it.id, "Administrator")
                    findNavController().navigate(R.id.go_to_profile)
                }

            }
        }.addOnFailureListener { exception ->
            Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun initDagger() {
        DaggerLoginComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}
