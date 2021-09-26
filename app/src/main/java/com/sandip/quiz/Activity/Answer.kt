package com.sandip.quiz.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.sandip.quiz.R
import com.sandip.quiz.model.ques_element
import com.sandip.quiz.model.quiz

class Answer : AppCompatActivity() {

    lateinit var firestore: FirebaseFirestore

    private var quizzes : MutableList<quiz>? = null
    private var questions: MutableMap<String, ques_element>? = null
    private var index = 1

    lateinit var question: TextView
    lateinit var answer:  TextView
    lateinit var previous: Button
    lateinit var Next: Button
    lateinit var ok: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        question = findViewById(R.id.question)
        answer = findViewById(R.id.answer)
        previous = findViewById(R.id.previous)
        Next = findViewById(R.id.Next)
        ok = findViewById(R.id.ok)

        setUpFirestore()
        setUpEventListener()
    }

    @SuppressLint("SetTextI18n")
    private fun bindviews(){

        previous.visibility = View.GONE
        Next.visibility = View.GONE
        ok.visibility = View.GONE

        when (index) {
            1 -> {
                Next.visibility = View.VISIBLE
            }
            questions!!.size -> {
                previous.visibility = View.VISIBLE
                ok.visibility = View.VISIBLE
            }
            else -> {
                previous.visibility = View.VISIBLE
                Next.visibility = View.VISIBLE
            }
        }

        Log.d("questions", questions.toString())

        val ques = questions?.get("Question$index")

        Log.d("ques", ques.toString())

        ques?.let {
            question.text = it.description
            answer.text = "Answer: "+it.answer
        }
    }

    private fun setUpFirestore() {

        firestore = FirebaseFirestore.getInstance()

//        access the date from intent(mainactivity)
        val date = intent.getStringExtra("DATE")

        if (date != null) {
            Log.d("DATE", date)
            firestore.collection("Quizzes").whereEqualTo("title", date)
                .get().addOnSuccessListener {
                    if (it != null && !it.isEmpty)
                    {
                        Log.d("DATA", it.toObjects(quiz::class.java).toString())
                        quizzes =  it.toObjects(quiz::class.java)
                        questions = quizzes!![0].Question
                        Log.d("questions", questions.toString())
                        bindviews()
                    }

                }
        }
    }

    private fun setUpEventListener(){

        previous.setOnClickListener {
            index--
            bindviews()
        }
        Next.setOnClickListener {
            index++
            bindviews()
        }
        ok.setOnClickListener {

            val intent = Intent(this@Answer , MainActivity::class.java)
            startActivity(intent)

        }
    }
}