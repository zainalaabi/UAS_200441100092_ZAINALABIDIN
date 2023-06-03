package com.example.projectakhir


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class ProfileFragment : Fragment() {


    private lateinit var emailTextView: TextView
    private lateinit var passwordTextView: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        emailTextView = view.findViewById(R.id.tvEmail)
        passwordTextView = view.findViewById(R.id.tvPassword)

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val email = sharedPreferences.getString("email", "")
        val password = sharedPreferences.getString("password", "")

        emailTextView.text = "Email           : $email"
        passwordTextView.text = "Password   : $password"

        return view
    }
}