package com.sandip.quiz.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sandip.quiz.R


class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var useremaillogin: EditText
    lateinit var userpasswordlogin: EditText
    lateinit var submit: Button
    lateinit var already: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        useremaillogin = findViewById(R.id.useremaillogin)
        userpasswordlogin = findViewById(R.id.loginpassword)
        already = findViewById(R.id.alreadysignup)

        already.setOnClickListener {
            val intent = Intent(this@Login, Signup::class.java)
            startActivity(intent)
            finish()
        }

        auth = Firebase.auth

        submit = findViewById(R.id.SUBMITlogin)

        submit.setOnClickListener {
            login()
        }
    }

    private fun login(){

        val email:String = useremaillogin.text.toString()
        val password:String = userpasswordlogin.text.toString()

        if (email.isBlank() || password.isBlank())
        {
            Toast.makeText(this, "please check email and password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful){

                checkIfEmailVerified()

            }else{
                Toast.makeText(this, "please check email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkIfEmailVerified() {
        val user = auth.currentUser
        if (user != null) {
            if (user.isEmailVerified) {
                // user is verified, so you can finish this activity or send user to activity which you want.
                finish()
                Toast.makeText(this@Login, "Successfully logged in", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // email is not verified, so just prompt the message to the user and restart this activity.
                // NOTE: don't forget to log out the user.
                auth.signOut()
                Toast.makeText(this@Login, "Verification not done yet", Toast.LENGTH_SHORT).show()
                //restart this activity
            }
        }
    }
}