package com.example.mad_24172012057_assignment1.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_24172012057_assignment1.MainActivity
import com.example.mad_24172012057_assignment1.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtSignup: TextView
    private var progress: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtSignup = findViewById(R.id.txtSignup)
        progress = findViewById(R.id.progressBar)

        btnLogin.setOnClickListener { attemptLogin() }
        txtSignup.setOnClickListener { startActivity(Intent(this, SignupActivity::class.java)) }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun attemptLogin() {
        val email = etEmail.text.toString().trim()
        val pass = etPassword.text.toString()

        if (email.isEmpty()) { etEmail.error = "Please enter email"; etEmail.requestFocus(); return }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { etEmail.error = "Enter a valid email"; etEmail.requestFocus(); return }
        if (pass.isEmpty()) { etPassword.error = "Enter password"; etPassword.requestFocus(); return }
        if (pass.length < 6) { etPassword.error = "Password must be at least 6 characters"; etPassword.requestFocus(); return }

        btnLogin.isEnabled = false
        progress?.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                progress?.visibility = View.GONE
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                progress?.visibility = View.GONE
                btnLogin.isEnabled = true
                Toast.makeText(this, e.message ?: "Login failed. Check credentials.", Toast.LENGTH_LONG).show()
            }
    }
}
