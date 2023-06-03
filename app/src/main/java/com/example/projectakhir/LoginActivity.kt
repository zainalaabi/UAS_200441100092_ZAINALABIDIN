package com.example.projectakhir

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvRegitrasi: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtEmail = findViewById(R.id.etEmail)
        edtPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.pbLogin)
        tvRegitrasi = findViewById(R.id.tvNewUser)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (isValidCredentials(email, password)) {
                    loginUser()
                } else {
                    Toast.makeText(this, "Password Salah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Isi semua Field", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegitrasi.setOnClickListener {
            val intent = Intent(this, RegistrasiActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isValidCredentials(email: String, password: String): Boolean {
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")
        return savedEmail == email && savedPassword == password
    }

    private fun loginUser() {
        progressBar.visibility = View.VISIBLE

        android.os.Handler().postDelayed({
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }, 2000)
    }
}
