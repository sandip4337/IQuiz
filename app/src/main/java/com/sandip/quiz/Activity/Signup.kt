package com.sandip.quiz.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sandip.quiz.R
import java.util.regex.Pattern

class Signup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var alertbutton: Button
    lateinit var useremail: EditText
    lateinit var userpassword: EditText
    lateinit var confirmuserpassword: EditText
    lateinit var submit : Button
    lateinit var already2 :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE,
        )

        setContentView(R.layout.activity_signup)

        auth = Firebase.auth

        useremail = findViewById(R.id.useremail)
        userpassword = findViewById(R.id.passwordsignup)
        confirmuserpassword = findViewById(R.id.confirmpassword)
        submit = findViewById(R.id.SUBMIT1)
        already2 = findViewById(R.id.alreadysignup)

        supportActionBar?.hide()

        alertbutton = findViewById(R.id.alertpassword)

        already2.setOnClickListener {
            val intent = Intent(this@Signup, Login::class.java)
            startActivity(intent)
        }

        alertbutton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Password should contain")
            builder.setMessage(" At least 1 digit(0-9)\n At least 1 lower case letter(a-z)\n At least 1 upper case letter(A-Z)\n At least 1 special character(@,$..)\n No white spaces\n At least 8 characters")
            builder.setPositiveButton("ok") { dialog, id -> dialog.cancel() }

            builder.create()
            builder.show()
        }

        submit.setOnClickListener {
            register()
        }

    }

    private fun register(){

        var email:String = useremail.text.toString()
        val password:String = userpassword.text.toString()
        val confirmpassword:String = confirmuserpassword.text.toString()

        email = email.replace("\\s".toRegex(), "")

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && isValidPasswordFormat(password) && password == confirmpassword)
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                if (it.isSuccessful){
                    cheakmail()
                }else{
                    Toast.makeText(this,"some error occurred",Toast.LENGTH_SHORT).show()
                }
            }
        else {
            Toast.makeText(this,"please cheak the details again",Toast.LENGTH_SHORT).show()
        }
    }

    private fun cheakmail(){
        val  firebaseUser =  auth.currentUser
        firebaseUser?.sendEmailVerification()?.addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this,"verification mail sent",Toast.LENGTH_SHORT).show()
                auth.signOut()
                finish()
                startActivity(Intent(this, Login::class.java))
            }
            else{
                Toast.makeText(this,"error occurred",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$")
        return passwordREGEX.matcher(password).matches()
    }
}