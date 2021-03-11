package com.example.cookbook.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cookbook.Model.RecipeModel

val DATABASENAME = "MY DATABASE"
val TABLENAME = "favorite"
val COL_NAME = "recipe_Name"
val COL_NAME2 = "recipe_Image"
val COL_ID = "id"
class DatabaseHelper(var context: Context):SQLiteOpenHelper(context, DATABASENAME,null,1)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =  "CREATE TABLE " + TABLENAME + " (" +
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME2 +" VARCHAR(256) UNIQUE, " +
                COL_NAME +" VARCHAR(256) UNIQUE " +");"
        db?.execSQL(createTable)    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(recipeModel: RecipeModel):Long
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME,recipeModel.recipeName)
        contentValues.put(COL_NAME2,recipeModel.imageUri)
        val result = db.insertWithOnConflict(TABLENAME, null, contentValues,SQLiteDatabase.CONFLICT_IGNORE)
        return result
    }
    fun getAllRecipe():ArrayList<RecipeModel>
    {
        var favorite_list = ArrayList<RecipeModel>()
        val db = this.writableDatabase
        val res = db.rawQuery("select * from "+ TABLENAME,null)
        while (res.moveToNext())
        {
            var recipe_name = res.getString(2)
            var image_url = res.getString(1)
            favorite_list.add(RecipeModel(recipe_name,"",image_url,"","",""))
        }
        return favorite_list
    }

    fun deleteData(recipeModel: RecipeModel)  {
        val db = this.writableDatabase
        db.delete(TABLENAME,"${COL_NAME} = ?", arrayOf(recipeModel.recipeName))
        db.delete(TABLENAME,"${COL_NAME2} = ?", arrayOf(recipeModel.imageUri))
    }
}