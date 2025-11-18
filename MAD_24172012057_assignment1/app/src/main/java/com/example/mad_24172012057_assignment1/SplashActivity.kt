package com.example.mad_24172012057_assignment1

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val lottie = findViewById<LottieAnimationView>(R.id.lottie)
        lottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                else startActivity(Intent(this@SplashActivity, com.example.mad_24172012057_assignment1.auth.LoginActivity::class.java))
                finish()
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }
}
