package com.sandip.quiz.Activity

import android.app.AlertDialog
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.sandip.quiz.R

import com.sandip.quiz.adapters.optionadapter
import com.sandip.quiz.model.ques_element
import com.sandip.quiz.model.quiz
import com.sandip.quiz.utils.ConnectionManager

class QuestionActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    private var quizzes : MutableList<quiz>? = null
    private var questions: MutableMap<String,ques_element>? = null
    private var index = 1

    lateinit var Description: TextView
    lateinit var recyclerqestion: RecyclerView
    lateinit var previous: Button
    lateinit var submit: Button
    lateinit var Next: Button
    lateinit var optionAdapter: optionadapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        if (ConnectionManager().cheakConnectivity(this)){
            setContentView(R.layout.activity_question)

            previous = findViewById(R.id.previous)
            Next = findViewById(R.id.Next)
            submit = findViewById(R.id.submit)


            Description = findViewById(R.id.description)
            recyclerqestion = findViewById(R.id.optionlist)

            setUpFirestore()

            setUpEventListener()
        }else{

            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                finish()
            }
            dialog.setNegativeButton("Exit"){text,listener ->
                ActivityCompat.finishAffinity(this)
            }
            dialog.create()
            dialog.show()

        }
    }

    private fun bindviews(){

        previous.visibility = View.GONE
        Next.visibility = View.GONE
        submit.visibility = View.GONE

        when (index) {
            1 -> {
                Next.visibility = View.VISIBLE
            }
            questions!!.size -> {
                previous.visibility = View.VISIBLE
                submit.visibility = View.VISIBLE
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
            Description.text = it.description
            optionAdapter = optionadapter(this, it)
            layoutManager = LinearLayoutManager(this)
            recyclerqestion.adapter = optionAdapter
            recyclerqestion.layoutManager = layoutManager
            recyclerqestion.setHasFixedSize(true)
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
        submit.setOnClickListener {
            Log.d("FINAL QUIZ",questions.toString())

            val date = intent.getStringExtra("DATE")
            val intent = Intent(this,ScoreActivity::class.java)
            val json = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ",json)
            intent.putExtra("DATE",date)
            startActivity(intent)
            finish()
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
}