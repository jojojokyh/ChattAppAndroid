package com.example.chattapp.obsolete.realm

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.example.chattapp.ContactDao
import com.example.chattapp.R

object DialogMaker {
    /**
     * creates and manages the dialog that pops up when clicking on the add user button to check the DB for users with the same
     * Username or mail that the user is writing
     */
    fun createChat(context: Context, contactDao: ContactDao){

        val userDao = UserDao()

        val createChatDialog = Dialog(context)
        createChatDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        createChatDialog.setContentView(R.layout.create_chat_layout)

        val addBtn = createChatDialog.findViewById<Button>(R.id.new_chat_btn)
        val cancelBtn = createChatDialog.findViewById<Button>(R.id.cancel_button)
        val textInputField = createChatDialog.findViewById<EditText>(R.id.name_input_field)

        addBtn.setOnClickListener {

//            val input = textInputField.text.toString()
//
//            if (userDao.checkIfUserExists(input)){
//                contactDao.addContact(input)
//                createChatDialog.dismiss()
//            } else {
//                textInputField.error = "user does not exists"
//            }

        }

        cancelBtn.setOnClickListener {createChatDialog.dismiss()}

        createChatDialog.show()

    }
}