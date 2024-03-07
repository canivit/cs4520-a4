package com.cs4520.assignment4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.cs4520.assignment4.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupLoginButton()
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            val username = binding.usernameField.text
            val password = binding.passwordField.text
            if ((username.toString() == "admin") && (password.toString() == "admin")) {
                findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
            } else {
                Toast.makeText(context, R.string.invalid_username_or_password, Toast.LENGTH_LONG)
                    .show()
            }

            username.clear()
            password.clear()
        }
    }
}