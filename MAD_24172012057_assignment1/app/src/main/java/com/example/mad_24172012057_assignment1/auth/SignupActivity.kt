package com.example.mad_24172012057_assignment1.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_24172012057_assignment1.MainActivity
import com.example.mad_24172012057_assignment1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = FirebaseAuth.getInstance()

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPass = findViewById<EditText>(R.id.etPassword)
        val btnSignup = findViewById<Button>(R.id.btnSignup)

        btnSignup.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass = etPass.text.toString().trim()
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener { res ->
                    val uid = res.user?.uid ?: return@addOnSuccessListener
                    val user = hashMapOf("name" to name, "email" to email, "isAdmin" to false)
                    db.collection("users").document(uid).set(user)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener { e ->
                    Toast.makeText(this, e.message ?: "Signup failed", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
