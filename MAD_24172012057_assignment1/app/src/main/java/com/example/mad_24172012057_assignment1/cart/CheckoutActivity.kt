package com.example.mad_24172012057_assignment1.cart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject


class CheckoutActivity : AppCompatActivity(), PaymentResultListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Razorpay call
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_yourkey") // <- yahan apna Razorpay test key daalo

        val options = JSONObject()
        options.put("name", "E-Pharmacy")
        options.put("currency", "INR")
        options.put("amount", (CartManager.total() * 100).toInt()) // paise me amount

        checkout.open(this, options)
    }

    override fun onPaymentSuccess(paymentId: String?) {
        Toast.makeText(this, "Payment Successful ✅", Toast.LENGTH_SHORT).show()

        // Save order to Firestore
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val order = hashMapOf(
            "userId" to uid,
            "status" to "Paid",
            "paymentId" to paymentId,
            "items" to CartManager.getItems(),
            "total" to CartManager.total()
        )

        FirebaseFirestore.getInstance().collection("orders").add(order)
    }

    override fun onPaymentError(code: Int, message: String?) {
        Toast.makeText(this, "Payment failed ❌ $message", Toast.LENGTH_SHORT).show()
    }
}
