package com.example.mad_24172012057_assignment1.admin

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.mad_24172012057_assignment1.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddEditProductActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private val db = FirebaseFirestore.getInstance()
    private val storageRef = FirebaseStorage.getInstance().reference

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            findViewById<ImageView>(R.id.imgPick).load(it) {
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_placeholder)
            }
        }
    }

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
        val progress = findViewById<ProgressBar?>(R.id.progressBar)

        btnPick.setOnClickListener { pickImageLauncher.launch("image/*") }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val brand = etBrand.text.toString().trim()
            val price = etPrice.text.toString().trim().toDoubleOrNull()
            val desc = etDesc.text.toString().trim()

            if (name.isEmpty()) { etName.error = "Enter product name"; etName.requestFocus(); return@setOnClickListener }
            if (brand.isEmpty()) { etBrand.error = "Enter brand"; etBrand.requestFocus(); return@setOnClickListener }
            if (price == null) { etPrice.error = "Enter valid price"; etPrice.requestFocus(); return@setOnClickListener }
            if (imageUri == null) { Toast.makeText(this, "Please pick an image", Toast.LENGTH_SHORT).show(); return@setOnClickListener }

            btnSave.isEnabled = false
            progress?.visibility = View.VISIBLE

            val fileName = "products/${UUID.randomUUID()}.jpg"
            val imgRef = storageRef.child(fileName)
            imgRef.putFile(imageUri!!)
                .addOnSuccessListener {
                    imgRef.downloadUrl.addOnSuccessListener { uri ->
                        val prod = hashMapOf(
                            "name" to name,
                            "brand" to brand,
                            "price" to price,
                            "desc" to desc,
                            "imageUrl" to uri.toString(),
                            "stock" to 100,
                            "createdAt" to com.google.firebase.Timestamp.now()
                        )
                        db.collection("products").add(prod)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                btnSave.isEnabled = true
                                progress?.visibility = View.GONE
                                Toast.makeText(this, "Save failed: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                    }.addOnFailureListener { e ->
                        btnSave.isEnabled = true
                        progress?.visibility = View.GONE
                        Toast.makeText(this, "Failed to get URL: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { e ->
                    btnSave.isEnabled = true
                    progress?.visibility = View.GONE
                    Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }

        img.setOnClickListener { pickImageLauncher.launch("image/*") }
    }
}
