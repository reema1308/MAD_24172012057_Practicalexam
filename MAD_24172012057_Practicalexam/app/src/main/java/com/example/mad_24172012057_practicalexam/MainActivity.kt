package com.example.mad_24172012057_practicalexam

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    // Local file path (from your uploaded file)
    private val LOCAL_SCREENSHOT = "file:///mnt/data/Screenshot 2025-11-21 091659.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // your provided xml

        // Header image
        val headerImage = findViewById<ImageView>(R.id.headerImage)
        Glide.with(this)
            .load(Uri.parse(LOCAL_SCREENSHOT))
            .into(headerImage)

        // Speaker images inside the nested horizontal speaker cards
        // In your layout speaker cards have ids img1, img2 ... so load those
        val img1 = findViewById<ImageView>(R.id.img1)
        val img2 = findViewById<ImageView>(R.id.img2)
        // If you add img3 etc, load similarly

        // Load the same screenshot as avatar (you can change per speaker)
        Glide.with(this).load(Uri.parse(LOCAL_SCREENSHOT)).into(img1)
        Glide.with(this).load(Uri.parse(LOCAL_SCREENSHOT)).into(img2)

        // Add small pulsing animation to redDot to mimic 'live' pulse (optional)
        val redDot = findViewById<View>(R.id.redDot)
        redDot?.animate()?.scaleX(1.3f)?.scaleY(1.3f)?.setDuration(700)?.withEndAction {
            redDot.animate().scaleX(1f).scaleY(1f).setDuration(700).start()
        }?.start()

        // Setup simple listener for schedule cards expand/collapse if you want:
        val cardA = findViewById<View>(R.id.cardA)
        val descA = findViewById<TextView>(R.id.descA)
        // Toggle details on card click
        cardA.setOnClickListener {
            descA.visibility = if (descA.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        // Similarly other cards
        val cardB = findViewById<View>(R.id.cardB)
        val descB = findViewById<TextView>(R.id.descB)
        cardB.setOnClickListener {
            descB.visibility = if (descB.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        val cardC = findViewById<View>(R.id.cardC)
        val descC = findViewById<TextView>(R.id.descC)
        cardC.setOnClickListener {
            descC.visibility = if (descC.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        // Buttons
        val btnTickets = findViewById<Button>(R.id.btnTickets)
        val btnCalendar = findViewById<Button>(R.id.btnCalendar)
        btnTickets.setOnClickListener {
            // placeholder: show a toast or start intent
            // Toast.makeText(this, "Buy Tickets clicked", Toast.LENGTH_SHORT).show()
        }
        btnCalendar.setOnClickListener {
            // placeholder
        }
    }
}
