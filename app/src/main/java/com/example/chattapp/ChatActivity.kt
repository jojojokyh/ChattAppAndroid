package com.example.chattapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chattapp.databinding.ActivityChatBinding
import com.example.chattapp.firebase.FirestoreChatDao
import com.example.chattapp.firebase.FirestoreMessageDao
import com.example.chattapp.models.Chat
import com.example.chattapp.models.Message

class ChatActivity : AppCompatActivity() {

    private lateinit var binder: ActivityChatBinding
    private lateinit var firestoreMessageDao: FirestoreMessageDao
    private lateinit var firestoreChatDao: FirestoreChatDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binder.root)

        var id = intent.getStringExtra("chatID")

        firestoreMessageDao = if(id != null) {
            FirestoreMessageDao(this, id)
        } else {
            FirestoreMessageDao()
        }

        firestoreChatDao = FirestoreChatDao()

        //Send messages
        binder.send.setOnClickListener {

            val newMessage = binder.textInput.text.toString()

            binder.textInput.text.clear()

            //Create new chat
            if(id == null){
                val chat = Chat()
                id = chat.id
                chat.usersInChat.add("dave")
                chat.usersInChat.addAll(intent.getStringArrayListExtra("userList") as ArrayList<String>)
                firestoreChatDao.saveChat(chat)
                //create listener
                firestoreMessageDao = FirestoreMessageDao(this, id!!)
            }
            createMessage(newMessage, id!!)
        }
    }

    fun loadMessages(messageList: ArrayList<Message>) {
        val layoutManager = LinearLayoutManager(this)
        //layoutManager.reverseLayout = true
        binder.recyclerMessages.layoutManager = layoutManager
        val adapter = MessageAdapter(this, messageList)
        binder.recyclerMessages.adapter = adapter
    }

    private fun createMessage(text: String, chatID: String){
        val msg = Message(text = text)
        firestoreMessageDao.saveMessage(msg, chatID)
    }
}