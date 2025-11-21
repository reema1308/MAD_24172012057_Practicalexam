package com.example.mad_24172012057_practicalexam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ScheduleAdapter(private val items: MutableList<ScheduleItem>): RecyclerView.Adapter<ScheduleAdapter.VH>() {
    inner class VH(view: View): RecyclerView.ViewHolder(view) {
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDetails: TextView = view.findViewById(R.id.tvDetails)
        val ivExpand: ImageView = view.findViewById(R.id.ivExpand)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return VH(v)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvTime.text = item.time
        holder.tvTitle.text = item.title
        holder.tvDetails.text = item.details
        holder.tvDetails.visibility = if (item.expanded) View.VISIBLE else View.GONE
        holder.ivExpand.rotation = if (item.expanded) 180f else 0f


        holder.itemView.setOnClickListener {
            item.expanded = !item.expanded
            notifyItemChanged(position)
        }
    }


    override fun getItemCount() = items.size
}