package com.example.cookbook.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbook.Adapter.FavoriteAdapter
import com.example.cookbook.Adapter.RecipeAdapter
import com.example.cookbook.R
import com.example.cookbook.db.DatabaseHelper
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var reipeAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        favorite_recycler.layoutManager = LinearLayoutManager(this)
        var databaseHelper = DatabaseHelper(this)
        if (DatabaseHelper(this).getAllRecipe().size==0)
        {
            animation_view.visibility = View.VISIBLE
        }

        reipeAdapter = FavoriteAdapter(this,databaseHelper.getAllRecipe())
        favorite_recycler.adapter = reipeAdapter
        reipeAdapter.notifyDataSetChanged()



    }
}