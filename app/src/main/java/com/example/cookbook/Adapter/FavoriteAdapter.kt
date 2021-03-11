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

class FavoriteAdapter(var context: Context,var list:ArrayList<RecipeModel>):RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view= LayoutInflater.from(context).inflate(R.layout.favorite_layout,parent,false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var recipeModel = list[position]
        holder.text_Name.text = recipeModel.recipeName
        Glide.with(context)
                .load(recipeModel.imageUri)
                .into(holder.image_cuisine)

        holder.remove.setOnClickListener()
        {
            var databaseHelper = DatabaseHelper(context)
            databaseHelper.deleteData(recipeModel)
            list.removeAt(position)
            notifyDataSetChanged()
            Toast.makeText(context,"Remove",Toast.LENGTH_LONG).show()

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
        var text_Name =itemview.findViewById<TextView>(R.id.frecipe_text)
        var image_cuisine = itemview.findViewById<ImageView>(R.id.frecipe_imageOnline)
        var remove = itemview.findViewById<Button>(R.id.remove)


    }
}