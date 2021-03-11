package com.example.cookbook.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookbook.Activity.RecipeDetails
import com.example.cookbook.Activity.Recipes
import com.example.cookbook.Model.RecipeModel
import com.example.cookbook.R
import com.example.cookbook.db.DatabaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.hashmi.whatsunknown.db.Animation.MyBounceInterpolator

class RecipeAdapter(var context: Context,var list:ArrayList<RecipeModel>):RecyclerView.Adapter<RecipeAdapter.MyViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view= LayoutInflater.from(context).inflate(R.layout.recipe_layout,parent,false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var recipeModel = list[position]
        holder.text_Name.text = recipeModel.recipeName
        Glide.with(context)
            .load(recipeModel.imageUri)
            .into(holder.image_cuisine)
        holder.itemview.setOnClickListener()
        {
            var intent  = Intent(context,RecipeDetails::class.java)
            intent.putExtra("racipeName",recipeModel.recipeName)
            intent.putExtra("racipe_des",recipeModel.recipe_des)
            intent.putExtra("recipe_time",recipeModel.recipeTime)
            intent.putExtra("recipe_prize",recipeModel.recipe_prize)
            intent.putExtra("comment",recipeModel.comment)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        holder.favorite_Button.setOnClickListener()
        {
            var databaseHelper = DatabaseHelper(context)
            var result = databaseHelper.insertData(recipeModel)
            if (result == (-1).toLong())
            {
                Toast.makeText(context,"Already Saved", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context,"Saved", Toast.LENGTH_LONG).show()
            }
        }
        var mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser!=null)
        {
            holder.favorite_Button.visibility = View.VISIBLE
            holder.alertText.visibility = View.INVISIBLE
        }
        var animation = AnimationUtils.loadAnimation(context,R.anim.button_animation)
        var maybounceInterpolator = MyBounceInterpolator(0.1,20.0)
        animation.interpolator = maybounceInterpolator
        holder.itemview.animation = animation

    }
    override fun getItemCount(): Int {
        return list.size
    }
    class MyViewHolder(var itemview : View):RecyclerView.ViewHolder(itemview){
        var text_Name =itemview.findViewById<TextView>(R.id.recipe_text)
        var image_cuisine = itemview.findViewById<ImageView>(R.id.recipe_imageOnline)
        var favorite_Button = itemview.findViewById<Button>(R.id.favorite)
        var alertText = itemview.findViewById<TextView>(R.id.alerttext)

    }
}