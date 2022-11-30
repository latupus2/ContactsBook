package com.example.sqlite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private val list: List<Contact>,
    // передаём коллбек нажатия на кнопку
    private val onItemDelete: (id: Int) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = (list[position].name + " " + list[position].surname)
            // обработчик нажатия кнопки
            holder.button.setOnClickListener {
                onItemDelete(list[position].id)
            }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.textView)
        // находим кнопку
        val button = itemView.findViewById<TextView>(R.id.button)

    }
}