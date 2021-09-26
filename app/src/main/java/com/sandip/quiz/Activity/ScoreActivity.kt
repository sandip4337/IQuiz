package com.sandip.quiz.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.sandip.quiz.R
import com.sandip.quiz.model.quiz

class ScoreActivity : AppCompatActivity() {

    lateinit var quiz: quiz

    lateinit var scores: TextView

    lateinit var showanswer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        supportActionBar?.hide()

        val quizdata = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<quiz>(quizdata,com.sandip.quiz.model.quiz::class.java)

        scores = findViewById(R.id.score)

        score()

        showanswer = findViewById(R.id.answer)

        val date = intent.getStringExtra("DATE")

        showanswer.setOnClickListener {
            val intent = Intent(this,Answer::class.java)
            intent.putExtra("DATE",date)
            Log.d("DATE",date!!)
            startActivity(intent)
        }
    }

    private fun score(){
        var score = 0
        for (entry in quiz.Question.entries){
            val question = entry.value
            if (question.answer == question.userans){
                score += 1
            }
        }
        scores.text = "$score"
    }

}