package com.example.mad_24172012057_assignment1.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.example.mad_24172012057_assignment1.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddEditProductActivity: AppCompatActivity() {
    private val PICK_IMG = 123
    private var imageUri: Uri? = null
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_product)
        val img = findViewById<ImageView>(R.id.imgPick)
        val etName = findViewById<EditText>(R.id.etName)
        val etBrand = findViewById<EditText>(R.id.etBrand)
        val etPrice = findViewById<EditText>(R.id.etPrice)
        val etDesc = findViewById<EditText>(R.id.etDesc)
        val btnPick = findViewById<Button>(R.id.btnPick)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnPick.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            startActivityForResult(Intent.createChooser(i, "Select Image"), PICK_IMG)
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val brand = etBrand.text.toString().trim()
            val price = etPrice.text.toString().trim().toDoubleOrNull() ?: 0.0
            val desc = etDesc.text.toString().trim()
            if (imageUri == null) {
                // save without image (or show error)
                return@setOnClickListener
            }
            val ref = storage.child("products/${UUID.randomUUID()}.jpg")
            ref.putFile(imageUri!!).continueWithTask { it ->
                ref.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val url = task.result.toString()
                    val prod = hashMapOf(
                        "name" to name, "brand" to brand, "price" to price, "desc" to desc, "imageUrl" to url, "stock" to 100
                    )
                    db.collection("products").add(prod).addOnSuccessListener {
                        finish()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMG && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            findViewById<ImageView>(R.id.imgPick).setImageURI(imageUri)
        }
    }
}
