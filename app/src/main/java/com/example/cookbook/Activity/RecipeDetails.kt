package com.example.cookbook.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.cookbook.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_add_recipe.*
import kotlinx.android.synthetic.main.activity_recipe_details.*

class RecipeDetails : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        mAuth = FirebaseAuth.getInstance()

        var recipeName = intent.getStringExtra("racipeName")
        var recipeDes = intent.getStringExtra("racipe_des")
        var recipeTime = intent.getStringExtra("recipe_time")
        var recipePrize = intent.getStringExtra("recipe_prize")
        var recipeComment = intent.getStringExtra("comment")
        drecipe_name.text = recipeName
        drecipe_des.text = recipeDes
        drecipe_time.text = recipeTime
        drecipe_prize.text = recipePrize
        drecipe_comment.text = recipeComment

        if (mAuth.currentUser==null)
        {
            note_button.visibility = View.INVISIBLE
            note_edittext.visibility = View.INVISIBLE
        }

    }
}