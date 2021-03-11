package com.example.cookbook.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cookbook.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_add_recipe.*
import java.lang.Exception

class AddRecipe : AppCompatActivity() ,AdapterView.OnItemSelectedListener{
    private lateinit var spinner_list:Array<String>
    private lateinit var selected_item: String
private var imageUri: Uri? = null
private lateinit var mStorage :StorageReference
private lateinit var mDatabase: DatabaseReference
private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)
        addDataToSpinner()
        cuisine_list_spinner.onItemSelectedListener= this
        progressDialog = ProgressDialog(this)


        recipe_image.setOnClickListener()
        {
            Toast.makeText(this,"hello",Toast.LENGTH_LONG).show()
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 1)
        }
        add_recipe.setOnClickListener()
        {
            sentDataToRealTime()
        }
    }

    fun addDataToSpinner()
    {
        spinner_list = resources.getStringArray(R.array.recipe_list)
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, spinner_list)
        cuisine_list_spinner.adapter = adapter
    }


    fun sentDataToRealTime()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(selected_item)
        var firebaseStorage = FirebaseStorage.getInstance()
        mStorage =  firebaseStorage.getReference().child("Recipe")

        var recipeName = recipe_name.text.toString()
        var recipeTime = recipe_time.text.toString()
        var recipe_prize = recipe_prize.text.toString()
        var comment = comment.text.toString()
        var recipe_description = abouttherecipe.text.toString()
        if (TextUtils.isEmpty(recipeName))
        {
            Toast.makeText(this,"Enter recipe name",Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(recipeTime))
        {
            Toast.makeText(this,"Enter recipe Time",Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(recipe_prize))
        {
            Toast.makeText(this,"Enter recipe prize",Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(comment))
        {
            Toast.makeText(this,"Enter any comment for recipe",Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(recipe_description))
        {
            Toast.makeText(this,"Enter recipe description",Toast.LENGTH_LONG).show()
            return
        }
        if (imageUri==null)
        {
            Toast.makeText(this,"Please import recipe image",Toast.LENGTH_LONG).show()
            return
        }
        progressDialog.setMessage("Please wait Adding...")
        progressDialog.show()
            val filepath = mStorage.child(imageUri!!.getLastPathSegment()!!)
            filepath.putFile(imageUri!!).addOnCompleteListener(object:OnCompleteListener<UploadTask.TaskSnapshot>{
                override fun onComplete(p0: Task<UploadTask.TaskSnapshot>) {
                    if (p0.isSuccessful)
                    {
                        var downloadUri = filepath.downloadUrl.addOnSuccessListener(object:OnSuccessListener<Uri>{
                            override fun onSuccess(p0: Uri?) {
                                var mdb =  mDatabase.push()
                                mdb.child("recipeName").setValue(recipeName)
                                mdb.child("recipeTime").setValue(recipeTime)
                                mdb.child("imageUri").setValue(p0.toString())
                                mdb.child("recipe_prize").setValue(recipe_prize)
                                mdb.child("recipe_des").setValue(recipe_description)
                                mdb.child("comment").setValue(comment)
                                Toast.makeText(applicationContext,"Send",Toast.LENGTH_LONG).show()
                                progressDialog.dismiss()
                            }

                        })

                    }
                }

            })





    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selected_item = spinner_list[position]
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageUri = data!!.data!!
            recipe_image.setImageURI(imageUri)
        }
    }
}