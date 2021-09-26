package com.sandip.quiz.Activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.sandip.quiz.R

class Splashscreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //vanish the window manager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        setContentView(R.layout.activity_splashscreen)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser

        //handle the splashscreen
        Handler(Looper.getMainLooper()).postDelayed({

            if (user != null) {
                redirect("MAIN")
            }
            else{
                redirect("LOGIN")
            } } , 1800)
    }

    private fun redirect(name : String){
        val intent = when(name){
            "LOGIN" -> Intent(this, Login::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("Some Error Happened")
        }
        startActivity(intent)
        finish()
    }
}