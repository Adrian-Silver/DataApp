package com.example.dataapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal  var dbHelper = SQLiteConfig(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

////        Create an instance of a database
//        val db:SQLiteDatabase = openOrCreateDatabase("appdb", Context.MODE_PRIVATE, null)
//
//        db.execSQL("CREATE TABLE IF NOT EXISTS users(name VARCHAR, profession VARCHAR, residence VARCHAR, password VARCHAR)")

//        LISTEN TO A CLICK EVENT ON THE ADD DATA IMAGE
        etAdd.setOnClickListener{
//            grab data from form
            val name:String =etName.text.trim().toString()
            val profession:String =etProfession.text.trim().toString()
            val residence:String =etResidence.text.trim().toString()
            val password:String =etPassword.text.trim().toString()

            if (name.isEmpty() or profession.isEmpty() or residence.isEmpty() or password.isEmpty()){
//                Show a message to the user
                show_message("Missing Data", "Please fill in all the fields")
            }else{
////                Store data in the database
////                db.execSQL("INSERT INTO users VALUES('"+name+"', '"+profession+"', '"+residence+"', '"+password+"')")
//                db.execSQL("INSERT INTO users VALUES('"+name+"', '"+profession+"', '"+residence+"', '"+password+"')")
//                show_message("Success", "Data added successfully")

                dbHelper.insertData(name, profession, residence, password)
                show_message("Success", "Data by name the ${name} has been added successfully")
//                Clear the edit text after successfully adding data into the database
                cleartext()
            }
        }

        etNext.setOnClickListener{
            val intent = Intent(this,UserActivity::class.java)
            startActivity(intent)
        }
    }



    private fun show_message(title:String, message:String){
        val alertDialog: AlertDialog.Builder =AlertDialog.Builder(this)
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)

        alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener{dialog, which -> dialog.dismiss()})
        alertDialog.create().show()
    }

    private fun cleartext(){
        etName.setText("")
        etProfession.setText("")
        etResidence.setText("")
        etPassword.setText("")
    }
}
