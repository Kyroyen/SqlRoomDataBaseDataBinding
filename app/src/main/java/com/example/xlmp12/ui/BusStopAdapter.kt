package com.example.xlmp12.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.xlmp12.database.schedule.Schedule
import com.example.xlmp12.databinding.FullRecyclerViewElementBinding

class BusStopAdapter(
    private val onItemClicked: (Schedule) -> Unit
) : ListAdapter<Schedule, BusStopAdapter.BusStopViewHolder>(DiffCallBack){

    class BusStopViewHolder(
        private var binding : FullRecyclerViewElementBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(schedule: Schedule){
            binding.schedule = schedule
            binding.executePendingBindings()
        }
    }

    companion object{
        private val DiffCallBack = object : DiffUtil.ItemCallback<Schedule>(){
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.stopName==newItem.stopName
                        &&
                        oldItem.arrivalTime==newItem.arrivalTime
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        return BusStopViewHolder(
            FullRecyclerViewElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(item)
        }
        holder.bind(item)
    }
}