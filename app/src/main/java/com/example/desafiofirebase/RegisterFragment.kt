package com.example.desafiofirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _auth: FirebaseAuth
    private lateinit var _navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _auth = FirebaseAuth.getInstance()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        _view.findViewById<MaterialButton>(R.id.btnCreateAccountRegister).setOnClickListener {
            val name = _view.findViewById<TextInputEditText>(R.id.txtNameRegister)
            val email = _view.findViewById<TextInputEditText>(R.id.txtEmailRegister)
            val password = _view.findViewById<TextInputEditText>(R.id.txtPasswordRegister)
            val repeatPassword =
                _view.findViewById<TextInputEditText>(R.id.txtRepeatPasswordRegister)

            registrar(name, email, password, repeatPassword)

        }

    }

    private fun registrar(
        name: TextInputEditText,
        email: TextInputEditText,
        password: TextInputEditText,
        repeatPassword: TextInputEditText
    ) {

        val nameRegister = name.text.toString()
        val emailRegister: String = email.text.toString()
        val passwordRegister = password.text.toString()
        val repeatPassRegister = repeatPassword.text.toString()

        if (nameRegister.isEmpty()) {
            view?.findViewById<TextInputEditText>(R.id.txtNameRegister)!!.error = "Campo vazio!"
        } else if (emailRegister.isEmpty()) {
            view?.findViewById<TextInputEditText>(R.id.txtEmailRegister)!!.error = "Campo vazio!"
        } else if (passwordRegister.isEmpty()) {
            view?.findViewById<TextInputEditText>(R.id.txtPasswordRegister)!!.error = "Campo vazio!"
        } else if (repeatPassRegister.isEmpty()) {
            view?.findViewById<TextInputEditText>(R.id.txtRepeatPasswordRegister)!!.error =
                "Campo vazio!"
        } else if (txtPasswordRegister != txtRepeatPasswordRegister) {
            view?.findViewById<TextInputEditText>(R.id.txtRepeatPasswordRegister)!!.error =
                "As senhas não condizem!."
        } else {

            val mAuth = FirebaseAuth.getInstance()

            mAuth.createUserWithEmailAndPassword(emailRegister, passwordRegister)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser

                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(nameRegister).build()

                        user!!.updateProfile(profileUpdates)

                        _view.findNavController()
                            .navigate(R.id.action_registerFragment_to_loginFragment)

                    } else {
                        Toast.makeText(_view.context, it.exception.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }
    }
}

