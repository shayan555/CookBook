package com.example.cookbook.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.Activity.RecipeDetails
import com.example.cookbook.Activity.Recipes
import com.example.cookbook.Model.RecipeModel
import com.example.cookbook.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hashmi.whatsunknown.db.Animation.MyBounceInterpolator

class CuisinesAdapter(var context: Context, var images: IntArray, var text:kotlin.Array<String>):RecyclerView.Adapter<CuisinesAdapter.MyViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view= LayoutInflater.from(context).inflate(R.layout.cuisines_layout,parent,false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.text_Name.text= text[position]
        holder.image_cuisine.setImageResource(images[position])
        holder.itemview.setOnClickListener()
        {
            var data = holder.text_Name.text
            var intent = Intent(context,Recipes::class.java)
            intent.putExtra("key",data)
            context.startActivity(intent)
        }
        var animation = AnimationUtils.loadAnimation(context,R.anim.button_animation)
        var maybounceInterpolator = MyBounceInterpolator(0.1,20.0)
        animation.interpolator = maybounceInterpolator
        holder.itemview.animation = animation
    }
    override fun getItemCount(): Int {
        return text.size
    }
    class MyViewHolder(var itemview : View):RecyclerView.ViewHolder(itemview){
        var text_Name =itemview.findViewById<TextView>(R.id.cuisine_text)
        var image_cuisine = itemview.findViewById<ImageView>(R.id.cuisines_image)
    }
}