package com.example.desafiofirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        _view.findViewById<Button>(R.id.btnCreateAccount).setOnClickListener {
            _view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        _view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val emailContainer = _view.findViewById<TextInputEditText>(R.id.txtEmailLogin)
            val passwordContainer = _view.findViewById<TextInputEditText>(R.id.txtPasswordLogin)

            fazerLogin(emailContainer, passwordContainer)
        }
    }


    private fun fazerLogin(
        emailContainer: TextInputEditText?,
        passwordContainer: TextInputEditText?
    ) {

        val email = emailContainer!!.text.toString()
        val password = passwordContainer!!.text.toString()

        if (email.isEmpty()) {
            emailContainer.error = "Campo vazio!"
        } else if (password.isEmpty()) {
            passwordContainer.error = "Campo vazio!"
        } else {
            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _view.findNavController()
                            .navigate(R.id.action_loginFragment_to_registerFragment)

                    } else {
                        Toast.makeText(_view.context, "Usuário ou senha incorretos", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }
}

