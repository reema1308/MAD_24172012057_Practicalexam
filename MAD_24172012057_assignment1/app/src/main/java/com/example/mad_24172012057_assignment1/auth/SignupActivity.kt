package com.example.mad_24172012057_assignment1.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_24172012057_assignment1.MainActivity
import com.example.mad_24172012057_assignment1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var btnSignup: Button
    private var progress: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPass = findViewById(R.id.etPassword)
        btnSignup = findViewById(R.id.btnSignup)
        progress = findViewById(R.id.progressBar)

        btnSignup.setOnClickListener { attemptSignup() }
    }

    private fun attemptSignup() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val pass = etPass.text.toString()

        if (name.isEmpty()) { etName.error = "Enter your name"; etName.requestFocus(); return }
        if (email.isEmpty()) { etEmail.error = "Enter email"; etEmail.requestFocus(); return }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { etEmail.error = "Enter valid email"; etEmail.requestFocus(); return }
        if (pass.isEmpty()) { etPass.error = "Enter password"; etPass.requestFocus(); return }
        if (pass.length < 6) { etPass.error = "Password must be at least 6 characters"; etPass.requestFocus(); return }

        btnSignup.isEnabled = false
        progress?.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener { authRes ->
                val firebaseUser = authRes.user
                val uid = firebaseUser?.uid
                if (uid == null) {
                    progress?.visibility = View.GONE
                    btnSignup.isEnabled = true
                    Toast.makeText(this, "Signup failed (no uid). Try again.", Toast.LENGTH_LONG).show()
                    return@addOnSuccessListener
                }
                val userDoc = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "isAdmin" to false,
                    "createdAt" to com.google.firebase.Timestamp.now()
                )
                db.collection("users").document(uid).set(userDoc)
                    .addOnSuccessListener {
                        firebaseUser.sendEmailVerification().addOnCompleteListener {
                            progress?.visibility = View.GONE
                            btnSignup.isEnabled = true
                            Toast.makeText(this, "Signup successful. Verification email sent.", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                    .addOnFailureListener { e ->
                        progress?.visibility = View.GONE
                        btnSignup.isEnabled = true
                        Toast.makeText(this, "Failed to save user: ${e.message}", Toast.LENGTH_LONG).show()
                    }
            }
            .addOnFailureListener { e ->
                progress?.visibility = View.GONE
                btnSignup.isEnabled = true
                Toast.makeText(this, e.message ?: "Signup failed. Try again.", Toast.LENGTH_LONG).show()
            }
    }
}
