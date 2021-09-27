package com.sandip.quiz.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.sandip.quiz.Home
import com.sandip.quiz.R
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var firestore: FirebaseFirestore

    lateinit var datebtnpicker: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        datebtnpicker = findViewById(R.id.btnDatePicker)

        setUpDatePicker()

//      create navigation view object
        val bottomNavigationItemView = findViewById<BottomNavigationView>(R.id.bottomnavigationview)

        // create navigation controller object
        val navController = findNavController(R.id.fragment)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.home,
            R.id.profile,
            R.id.about_us
//            R.id.menu
        ))
        setupActionBarWithNavController(navController,appBarConfiguration)

        // merge navigation view and navigation controller
        bottomNavigationItemView.setupWithNavController(navController)
    }


    //    datepicker function in activity
    @SuppressLint("SimpleDateFormat")
    private fun setUpDatePicker() {
        datebtnpicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)

//              convert date format
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))
                firestore = FirebaseFirestore.getInstance()

                // cheak the date exits or not
                firestore.collection("Quizzes").whereEqualTo("title", date)
                    .get().addOnSuccessListener {
                        if (it != null && !it.isEmpty)
                        {
                            val intent = Intent(this,QuestionActivity::class.java)
                            intent.putExtra("DATE",date) // <--- pass the date here
                            startActivity(intent)
                        }
                        else{
                            val intent = Intent(this,noquestion::class.java)
                            startActivity(intent)
                        }
                    }

                datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)

            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "Date Picker Cancelled")
            }
        }
    }
    }
}
