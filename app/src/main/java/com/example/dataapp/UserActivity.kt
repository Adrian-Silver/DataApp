package com.example.dataapp
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    internal var dbHelper =SQLiteConfig(this)

    //      @SuppressLint("WrongConstant", "Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)


//        Enter a new user
        adduser.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        imgrefresh.setOnClickListener {
            refresh()
        }

            refresh()
//
////        Pull data from a db and put it in a listview
////        Open db
//        val db: SQLiteDatabase = openOrCreateDatabase("appdb", Context.MODE_PRIVATE, null)
//
////        Pull data from the users table
//        val sql = "SELECT * FROM users"
//
////        Create user list/array
//        val users: ArrayList<DataItem> = ArrayList()
//
//        val cursor = db.rawQuery(sql, null)
//
//        if(cursor.count ==0){
//            show_message("No users","Seems like there is no user")
//        }else{
//            while (cursor.moveToNext()){
//                users.add(
//                    DataItem(
//                        cursor.getString(0) ,//name,
//                        cursor.getString(1) ,//profession,
//                        cursor.getString(2) ,//residence,
//                        cursor.getString(3)  //password,
//                    )
//                )
//            }
//            userlist.adapter =CustomAdapter(this, users)
//
//        }
    }


    fun refresh(){
        val users: ArrayList<DataItem> = ArrayList()

        val cursor = dbHelper.alldata

        if ( cursor.count ==0){
            show_message("No records", "You do not have any data here")
        }else{
            while(cursor.moveToNext()){
                users.add(
                    DataItem(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                    )
                )
            }

            userlist.adapter = CustomAdapter(this, users)
        }
    }


    private fun show_message(title:String, message:String){
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)

        alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener{ dialog, which -> dialog.dismiss()})
        alertDialog.create().show()
    }
}
