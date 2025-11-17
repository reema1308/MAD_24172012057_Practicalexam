package com.example.mad_24172012057_assignment1.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.mad_24172012057_assignment1.R

class PharmacyFragment : Fragment() {

    private val address = "Ganpat University, Mehsana, Gujarat"
    private val phone = "+919876543210"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pharmacy, container, false)

        val btnMap: Button = view.findViewById(R.id.btnOpenMap)
        val btnCall: Button = view.findViewById(R.id.btnCall)

        btnMap.setOnClickListener {
            val uri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        btnCall.setOnClickListener {
            val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            startActivity(call)
        }

        return view
    }
}
