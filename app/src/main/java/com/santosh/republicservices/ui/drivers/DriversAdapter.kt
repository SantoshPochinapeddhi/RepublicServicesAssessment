package com.santosh.republicservices.ui.drivers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.santosh.republicservices.model.data.Driver
import com.santosh.republicservices.databinding.ItemDriverBinding

class DriversAdapter(
    private val itemClick: (item: Driver) -> Unit
) : RecyclerView.Adapter<DriversAdapter.DriverViewHolder>() {
    private val drivers = ArrayList<Driver>()
    fun setDrivers(list: List<Driver>) {
        this.drivers.clear()
        this.drivers.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val binding =
            ItemDriverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DriverViewHolder(binding) {
            itemClick(drivers[it])
        }
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        holder.bind(drivers[position])
    }

    override fun getItemCount(): Int {
        return drivers.size
    }

    class DriverViewHolder(
        private val binding: ItemDriverBinding,
        itemClick: (item: Int) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemClick.invoke(adapterPosition)
            }
        }

        fun bind(driver: Driver) {
            binding.apply {
                driverId.text = driver.id
                driverName.text = driver.name
            }
        }
    }
}