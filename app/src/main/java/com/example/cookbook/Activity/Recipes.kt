package com.example.cookbook.Activity

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbook.Adapter.CuisinesAdapter
import com.example.cookbook.Adapter.RecipeAdapter
import com.example.cookbook.Model.RecipeModel
import com.example.cookbook.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_recipes.*

class Recipes : AppCompatActivity() {

//    var imagesIndian: IntArray = intArrayOf(
//        R.drawable.chicken,
//        R.drawable.curry,
//        R.drawable.deserts,
//        R.drawable.starters,
//
//    )
//    var textsIndian: Array<String> = arrayOf(
//        "Indian chicken",
//        "Indian Curry",
//        "Indian deserts ",
//        "Indian starters",
//    )
//    var imageschinies: IntArray = intArrayOf(
//            R.drawable.chicken,
//            R.drawable.curry,
//            R.drawable.deserts,
//            R.drawable.starters,
//
//            )
//    var textsChinies: Array<String> = arrayOf(
//            "Indian chicken",
//            "Indian Curry",
//            "Indian deserts ",
//            "Indian starters",
//    )
    private lateinit var adapter:RecipeAdapter

    private  var list = ArrayList<RecipeModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)
        recipe_list.layoutManager = LinearLayoutManager(this)
        var connectivitymanager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo = connectivitymanager.activeNetworkInfo

        if (networkInfo != null) {
            if (networkInfo.isConnected)
            {
                getData()

            } else {
                Snackbar.make(findViewById(android.R.id.content),"No Internet Connection", Snackbar.LENGTH_LONG).show()

                shimmer_recipe.startShimmerAnimation()
            }
        } else {
            var snackbar =  Snackbar.make(findViewById(android.R.id.content),"No Internet Connection",
                Snackbar.LENGTH_LONG).show()
            shimmer_recipe.startShimmerAnimation()
        }

    }

    override fun onStart() {
        super.onStart()
        shimmer_recipe.startShimmerAnimation()
    }

    override fun onStop() {
        super.onStop()
        shimmer_recipe.stopShimmerAnimation()
    }

    fun getData()
    {
        var data = intent.getStringExtra("key")
        var  mDatabase = FirebaseDatabase.getInstance().getReference().child(data!!)
        mDatabase.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var size =  snapshot.childrenCount
                Log.i("size",size.toString())
                list = snapshot.children.map { it.getValue(RecipeModel::class.java)!! } as ArrayList<RecipeModel>
                adapter = RecipeAdapter(applicationContext,list)
                recipe_list.adapter = adapter
                adapter.notifyDataSetChanged()
                shimmer_recipe.stopShimmerAnimation()

                shimmer_recipe.visibility = View.GONE

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}