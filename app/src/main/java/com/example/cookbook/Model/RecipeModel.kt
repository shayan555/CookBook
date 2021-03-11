package com.example.cookbook.Model

import android.os.Parcel
import android.os.Parcelable
import android.text.SpannableString
import com.google.android.material.internal.ParcelableSparseArray

data class RecipeModel(var recipeName:String,var recipeTime:String,var imageUri:String,var recipe_prize:String,var recipe_des:String,var comment:String)
{


    constructor():this("","","","","","")
}