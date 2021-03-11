package com.example.cookbook.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cookbook.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {


    private lateinit var mAuth:FirebaseAuth
    private lateinit var progressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        register_button.setOnClickListener()
        {
            registerUser()
        }
    }


    fun registerUser()
    {
        val email: String = auth_email.text.toString().trim()
        val password: String = auth_password.text.toString().trim()

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
        progressDialog.setMessage("Registering Please Wait...")
        progressDialog.show()

        //creating a new user

        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this,object :OnCompleteListener<AuthResult>{
                override fun onComplete(p0: Task<AuthResult>) {
                    if(p0.isSuccessful)
                    {
                        finish()
                        var intent = Intent(applicationContext,LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(applicationContext,"Registration Error",Toast.LENGTH_LONG).show()
                    }
                    progressDialog.dismiss()
                }
            })
    }
}