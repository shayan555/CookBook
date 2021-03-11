package com.example.cookbook.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cookbook.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth:FirebaseAuth
    private lateinit var progressBar:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        progressBar = ProgressDialog(this)



        login_button.setOnClickListener()
        {
            loginUser()
        }
        registration_activity.setOnClickListener()
        {
            startActivity(Intent(applicationContext,RegisterActivity::class.java))
        }


    }

    private fun loginUser() {
        val email: String = login_email.text.toString().trim()
        val password: String = login_password.text.toString().trim()


        //checking if email and passwords are empty


        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show()
            return
        }

        //if the email and password are not empty
        //displaying a progress dialog


        //if the email and password are not empty
        //displaying a progress dialog
        progressBar.setMessage("Registering Please Wait...")
        progressBar.show()
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,object :OnCompleteListener<AuthResult>{
            override fun onComplete(p0: Task<AuthResult>) {
                if (p0.isSuccessful)
                {
                    finish()
                    var intent = Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)

                }
                progressBar.dismiss()
            }

        })
    }
}