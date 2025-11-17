package com.example.mad_24172012057

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class splashactivity : AppCompatActivity(), Animation.AnimationListener {
    lateinit var gunilogo: ImageView
    lateinit var guniframeanimation: AnimationDrawable
    lateinit var Animgrosspin : Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        gunilogo = findViewById(R.id.img_guni_logo)
        gunilogo.setBackgroundResource(R.drawable.uvpc_animation_list)
        guniframeanimation = gunilogo.background as AnimationDrawable
        Animgrosspin = AnimationUtils.loadAnimation(this,R.anim.twinanimation)
        Animgrosspin.setAnimationListener(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v,insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus){
            guniframeanimation.start()
            gunilogo.startAnimation(Animgrosspin)
        }else{
            guniframeanimation.stop()
        }
    }
    override fun onAnimationEnd(animation: Animation?) {
        intent = Intent(this,MainActivity::class.java).also { startActivity(it) }
    }

    override fun onAnimationRepeat(animation: Animation?) {

    }

    override fun onAnimationStart(animation: Animation?) {

    }
}