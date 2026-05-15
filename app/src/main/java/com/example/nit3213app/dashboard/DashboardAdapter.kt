package com.example.nit3213app.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nit3213app.R
import com.example.nit3213app.model.Entity

class DashboardAdapter(
    private val onItemClick: (Entity) -> Unit
) : RecyclerView.Adapter<DashboardAdapter.EntityViewHolder>() {

    private var dataList: List<Entity> = listOf()

    class EntityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleText: TextView = view.findViewById(R.id.titleText)
        private val subtitleText: TextView = view.findViewById(R.id.subtitleText)

        fun bind(entity: Entity, onItemClick: (Entity) -> Unit) {
            val properties = entity.properties.filter { it.key != "description" }
            val entries = properties.entries.toList()

            titleText.text = if (entries.isNotEmpty()) entries[0].value else "Item"

            val remaining = entries.drop(1).joinToString(" | ") {
                "${it.key}: ${it.value}"
            }
            subtitleText.text = remaining

            itemView.setOnClickListener {
                onItemClick(entity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entity, parent, false)
        return EntityViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(dataList[position], onItemClick)
    }

    override fun getItemCount() = dataList.size

    fun setData(newDataList: List<Entity>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
}