package com.sandip.quiz.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sandip.quiz.R
import com.sandip.quiz.model.ques_element

class optionadapter (val context: Context, val Question : ques_element):
    RecyclerView.Adapter<optionadapter.adaptarholder>() {

    private var options: List<String> = listOf(Question.option1 , Question.option2 , Question.option3 , Question.option4)

    inner class adaptarholder(itemview: View) : RecyclerView.ViewHolder(itemview)
    {
        var optionView = itemView.findViewById<TextView>(R.id.quiz_option)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptarholder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.option_item , parent, false)
        return adaptarholder(view)
    }

    override fun onBindViewHolder(holder: adaptarholder, position: Int) {
        Log.d("option", options.toString())
        val item = options[position]
        holder.optionView.text = item

        holder.itemView.setOnClickListener{
                Question.userans = item
                notifyDataSetChanged()
        }

        if(Question.userans == item){
            holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg)
        }
        else
        {
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg)
        }
    }

    override fun getItemCount(): Int {
        return options.size
    }
}