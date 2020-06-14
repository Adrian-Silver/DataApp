package com.example.dataapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.alert_dialog_box.view.*

class CustomAdapter(var context: Context, var data: ArrayList<DataItem>): BaseAdapter() {
    internal var dbHelper = SQLiteConfig(context)

    private class ViewHolder(row: View?){
        var name:TextView
        var profession:TextView
        var residence:TextView
        var password:TextView
        var imgdelete:ImageView
        var imgedit:ImageView

        init{

            this.name = row?.findViewById(R.id.tvname) as TextView
            this.profession = row?.findViewById(R.id.tvprofession) as TextView
            this.residence = row?.findViewById(R.id.tvresidence) as TextView
            this.password = row?.findViewById(R.id.tvpassword) as TextView
            this.imgdelete = row?.findViewById(R.id.imgdelete) as ImageView
            this.imgedit = row?.findViewById(R.id.imgedit) as ImageView

        }
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View?
        val viewHolder:ViewHolder
        if (convertView ==null){
            val layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.card_item_row,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val dataitem:DataItem = getItem(position) as DataItem
        viewHolder.name.text = dataitem.name
        viewHolder.profession.text = dataitem.profession
        viewHolder.residence.text = dataitem.residence
        viewHolder.password.text = dataitem.password

//        viewHolder.imgdelete.setOnClickListener {
//            val item_position = getItemId(position)
//        }


        val name = dataitem.name
        val profession = dataitem.profession
        val residence = dataitem.residence
        val password = dataitem.password

//        update feature
        viewHolder.imgedit.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val dialogview = inflater.inflate(R.layout.alert_dialog_box, parent, false)
            dialogBuilder.setView(dialogview)

//            Populating the edit text with Dataitem data
            dialogview.detName.setText(name)
            dialogview.detProfession.setText(profession)
            dialogview.detResidence.setText(residence)
            dialogview.detPassword.setText(password)

//            Creating the builder on the front face
            dialogBuilder.setTitle("Edit ${name}")
            dialogBuilder.setMessage("Do you want to edit ${name} ?")

//            When a user selects edit
            dialogBuilder.setPositiveButton("Edit", {dialog, which ->
//                Pull/ grab user data
                val updatedname = dialogview.detName.text.toString()
                val updatedprofession = dialogview.detProfession.text.toString()
                val updatedresidence = dialogview.detResidence.text.toString()
                val updatedpassword = dialogview.detPassword.text.toString()


                val cursor = dbHelper.alldata

                while (cursor.moveToNext()){
                    if (name == cursor.getString(1)){
                        dbHelper.updateData(
                            cursor.getString(0),
                            updatedname,
                            updatedprofession,
                            updatedresidence,
                            updatedpassword

                        )
                    }
                }
                this.notifyDataSetChanged()
                Toast.makeText(context,"${name} updated succesfully",Toast.LENGTH_SHORT).show()
            })
//            When a user selects cancel
            dialogBuilder.setNegativeButton("Cancel", {dialog, which -> dialog.dismiss()  })
            dialogBuilder.create().show()

        }


        viewHolder.imgdelete.setOnClickListener{
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Delete")
            dialogBuilder.setMessage("Confirm delete ${name}")

            dialogBuilder.setPositiveButton("Delete", {dialog, which ->
//                delete data
                dbHelper.deleteData(name)
                val users: ArrayList<DataItem> = ArrayList()

//                Pull all data from db
                val cursor = dbHelper.alldata

                if(cursor.count ==0){
                    Toast.makeText(context,"No data available", Toast.LENGTH_SHORT).show()
                }else{
                    while (cursor.moveToNext()){
                        users.add(
                            DataItem(
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4)
                            )
                        )
                    }
                    this.notifyDataSetChanged()
                }

            })

            dialogBuilder.setNegativeButton("Cancel", {dialog, which -> dialog.dismiss() })
            dialogBuilder.create().show()
        }

        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }


}