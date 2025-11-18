package com.example.mad_24172012057_assignment1

import android.app.Application
import com.google.firebase.FirebaseApp
import com.razorpay.Checkout

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Checkout.preload(this)
    }
}
