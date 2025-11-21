package com.example.mad_24172012057_practicalexam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class SpeakerAdapter(private val items: List<Speaker>): RecyclerView.Adapter<SpeakerAdapter.VH>() {
    inner class VH(view: View): RecyclerView.ViewHolder(view) {
        val iv: ImageView = view.findViewById(R.id.ivAvatar)
        val name: TextView = view.findViewById(R.id.tvName)
        val role: TextView = view.findViewById(R.id.tvRole)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_speaker, parent, false)
        return VH(v)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = items[position]
        holder.name.text = s.name
        holder.role.text = s.role
        if (!s.avatarUrl.isNullOrEmpty()) {
            Glide.with(holder.iv).load(s.avatarUrl).into(holder.iv)
        } else {
            holder.iv.setImageResource(android.R.drawable.sym_def_app_icon)
        }
    }


    override fun getItemCount() = items.size
}