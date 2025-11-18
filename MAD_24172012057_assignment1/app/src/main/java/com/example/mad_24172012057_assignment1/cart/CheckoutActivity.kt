package com.example.mad_24172012057_assignment1.cart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_24172012057_assignment1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class CheckoutActivity : AppCompatActivity(), PaymentResultListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Checkout.preload(applicationContext)

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            Toast.makeText(this, "Please login before checkout", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, com.example.mad_24172012057_assignment1.auth.LoginActivity::class.java))
            finish()
            return
        }

        val totalRupees = CartManager.total()
        val amountPaise = (totalRupees * 100).toLong()
        if (amountPaise <= 0L) {
            Toast.makeText(this, "Cart is empty or invalid amount", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val checkout = Checkout()
        val key = getString(R.string.razorpay_test_key)
        checkout.setKeyID(key)

        val options = JSONObject()
        try {
            options.put("name", "E-Pharmacy")
            options.put("description", "Order Payment")
            options.put("currency", "INR")
            options.put("amount", amountPaise)

            val prefill = JSONObject()
            val user = FirebaseAuth.getInstance().currentUser
            prefill.put("email", user?.email ?: "")
            prefill.put("contact", "")
            options.put("prefill", prefill)

            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error starting payment: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onPaymentSuccess(paymentId: String?) {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        val orderMap = hashMapOf(
            "userId" to uid,
            "status" to "Paid",
            "paymentId" to (paymentId ?: ""),
            "items" to CartManager.getItems().map {
                mapOf(
                    "productId" to it.product.id,
                    "name" to it.product.name,
                    "price" to it.product.price,
                    "quantity" to it.quantity,
                    "imageUrl" to it.product.imageUrl
                )
            },
            "total" to CartManager.total(),
            "createdAt" to com.google.firebase.Timestamp.now()
        )

        FirebaseFirestore.getInstance().collection("orders")
            .add(orderMap)
            .addOnSuccessListener {
                CartManager.clear()
                Toast.makeText(this, "Payment & order saved. Thank you!", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Payment succeeded but failed to save order: ${e.message}", Toast.LENGTH_LONG).show()
                finish()
            }
    }

    override fun onPaymentError(code: Int, message: String?) {
        Toast.makeText(this, "Payment failed: $message (code: $code)", Toast.LENGTH_LONG).show()
        finish()
    }
}
