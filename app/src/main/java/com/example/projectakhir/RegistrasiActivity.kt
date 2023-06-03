package com.example.projectakhir

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class RegistrasiActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnRegistrasi: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvLogin: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)

        edtEmail = findViewById(R.id.etEmail)
        edtPassword = findViewById(R.id.etPassword)
        btnRegistrasi = findViewById(R.id.btnSignUp)
        progressBar = findViewById(R.id.pbSignup)
        tvLogin =findViewById(R.id.tvlogin)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        btnRegistrasi.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (isEmailAvailable(email)) {
                    registerUser(email, password)
                } else {
                    Toast.makeText(this, "Email Sudah Terdaftar", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Isi Nilai Field", Toast.LENGTH_SHORT).show()
            }
        }
        tvLogin.setOnClickListener {
            val intent = Intent(this, RegistrasiActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isEmailAvailable(email: String): Boolean {
        val savedEmail = sharedPreferences.getString("email", "")
        return savedEmail != email
    }

    private fun registerUser(email: String, password: String) {
        progressBar.visibility = ProgressBar.VISIBLE

        android.os.Handler().postDelayed({
            val editor = sharedPreferences.edit()
            editor.putString("email", email)
            editor.putString("password", password)
            editor.apply()

            progressBar.visibility = ProgressBar.GONE
            Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}
