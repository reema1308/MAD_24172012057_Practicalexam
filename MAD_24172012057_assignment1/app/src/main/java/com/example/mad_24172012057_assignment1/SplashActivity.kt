package com.example.mad_24172012057_assignment1


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // add LottieAnimationView in layout
        val lottie = findViewById<LottieAnimationView>(R.id.lottie)
        lottie.addAnimatorUpdateListener {
            // no-op
        }
        lottie.addAnimatorListener(object: com.airbnb.lottie.LottieAnimatorListener {
            override fun onAnimationEnd(p0: com.airbnb.lottie.LottieComposition?) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                else startActivity(Intent(this@SplashActivity, com.example.mad_24172012057_assignment1.auth.LoginActivity::class.java))
                finish()
            }
            override fun onAnimationCancel(p0: com.airbnb.lottie.LottieComposition?) {}
            override fun onAnimationRepeat(p0: com.airbnb.lottie.LottieComposition?) {}
            override fun onAnimationStart(p0: com.airbnb.lottie.LottieComposition?) {}
        })
    }
}
