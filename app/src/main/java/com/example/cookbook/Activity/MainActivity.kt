package com.example.cookbook.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbook.Adapter.CuisinesAdapter
import com.example.cookbook.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var images: IntArray = intArrayOf(
        R.drawable.indian,
        R.drawable.chines,
        R.drawable.french,
        R.drawable.asian,
        R.drawable.italian,
        R.drawable.thai,
        )
    var texts: Array<String> = arrayOf(
        "Indian",
        "Chinese",
        "French",
        "Asian",
        "Itailian",
        "Thai"
    )

    private lateinit var adapter:CuisinesAdapter
private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        mAuth = FirebaseAuth.getInstance()
        var loginStatus = intent.getStringExtra("key")
        if (mAuth.currentUser!=null)
        {
            fab.visibility = View.VISIBLE
            login_main_button.visibility = View.INVISIBLE
            logout_button.visibility = View.VISIBLE
            favoritefab.visibility = View.VISIBLE
        }
login_main_button.setOnClickListener()
{
    startActivity(Intent(this,LoginActivity::class.java))
    finish()
}
        logout_button.setOnClickListener()
        {
            login_main_button.visibility = View.VISIBLE
            fab.visibility = View.INVISIBLE
            logout_button.visibility = View.INVISIBLE
            favoritefab.visibility = View.INVISIBLE
            mAuth.signOut()

        }

        fab.setOnClickListener()
        {
            startActivity(Intent(this,AddRecipe::class.java))
        }
        favoritefab.setOnClickListener()
        {
            startActivity(Intent(this,FavoriteActivity::class.java))
        }
    }
    fun initRecyclerView()
    {
        cuisine_list.layoutManager = LinearLayoutManager(this)
        adapter = CuisinesAdapter(this,images,texts)
        cuisine_list.adapter  = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser!=null)
        {
            fab.visibility = View.VISIBLE
            login_main_button.visibility = View.INVISIBLE
            logout_button.visibility = View.VISIBLE
            favoritefab.visibility = View.VISIBLE
        }

    }
}