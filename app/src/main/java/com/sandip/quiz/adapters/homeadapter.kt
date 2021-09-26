package com.sandip.quiz.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sandip.quiz.Activity.QuestionActivity
import com.sandip.quiz.R
import com.sandip.quiz.model.quiz
import com.sandip.quiz.utils.Colorpicker
import com.sandip.quiz.utils.IconPicker

class homeadapter(val context: Context, val Queslist : List<quiz>): RecyclerView.Adapter<homeadapter.adaptarholder>() {

    class adaptarholder(view: View) : RecyclerView.ViewHolder(view) {
        var title = view.findViewById<TextView>(R.id.quiztitle)
        var image = view.findViewById<ImageView>(R.id.cardimage)
        var card =  view.findViewById<CardView>(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptarholder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.quiz_card_view,parent,false)
        return adaptarholder(view)
    }

    override fun onBindViewHolder(holder: adaptarholder, position: Int) {
        val item = Queslist[position]
        holder.title.text = item.title
        holder.card.setCardBackgroundColor(Color.parseColor(Colorpicker.getcolor()))
        holder.image.setImageResource(IconPicker.getIcon())

        holder.itemView.setOnClickListener{
//            Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
            val intent = Intent(context,QuestionActivity::class.java)
            intent.putExtra("DATE",item.title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  Queslist.size
    }
}