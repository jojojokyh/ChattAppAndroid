package com.example.chattapp.firebase

import android.util.Log
import com.example.chattapp.ChatActivity
import com.example.chattapp.models.Chat
import com.example.chattapp.MainActivity
import com.example.chattapp.UserManager
import com.example.chattapp.realm.ChatDao
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreChatDao {

    private val ID_KEY = "id"
    private val USERS_IN_CHAT_KEY = "users_in_chat"
    private val CHAT_NAME_KEY = "chat_name"
    private val LAST_MESSAGE_KEY = "last_message"
    private val TIMESTAMP_KEY = "timestamp"

    private val CHATS_COLLECTION = "chats"

    private val firebaseDB = FirebaseFirestore.getInstance()

    val chatList = ArrayList<Chat>()

    fun firestoreListener(activity: MainActivity){
        firebaseDB.collection(CHATS_COLLECTION).orderBy(TIMESTAMP_KEY).addSnapshotListener(activity) { value, error ->
            if (error != null) {
                Log.e("FIRESTORE", "Failed to listen for chats", error)
            }
            if (value != null) {
                chatList.clear()
                for(doc in value) {
                    val chat = Chat()

                    chat.id = doc.getString(ID_KEY)!!
                    chat.usersInChat = doc.get(USERS_IN_CHAT_KEY) as ArrayList<String>

                    if(doc.getString(CHAT_NAME_KEY) != null){
                        chat.chatName = doc.getString(CHAT_NAME_KEY)!!
                    } else {
                        chat.chatName = "No Name"
                    }
                    if(doc.getString(LAST_MESSAGE_KEY) != null){
                        chat.lastMessage = doc.getString(LAST_MESSAGE_KEY)!!
                    } else {
                        chat.lastMessage = "No Last Message"
                    }
                    if(doc.getDate(TIMESTAMP_KEY) != null){
                        chat.timestamp = doc.getDate(TIMESTAMP_KEY)!!
                    }

                    for (i in 0 until chat.usersInChat.size) {
                        if (UserManager.currentUser?.id == chat.usersInChat[i]){
                            chatList.add(chat)
                            ChatDao.insertChat(chat)
                            break
                        }
                    }
                }
                activity.loadList(chatList)

                Log.d("FIRESTORE", "Updated Chat List")
            } else {
                Log.d("FIRESTORE", "Data null")
            }
        }
    }

    fun saveChat(chat : Chat){
        val chatHashMap = hashMapOf(
            ID_KEY to chat.id,
            USERS_IN_CHAT_KEY to chat.usersInChat,
            CHAT_NAME_KEY to chat.chatName,
            LAST_MESSAGE_KEY to chat.lastMessage,
        )

        firebaseDB
            .collection(CHATS_COLLECTION)
            .document(chat.id)
            .set(chatHashMap)
            .addOnSuccessListener { Log.d("FIRESTORE", "Chat saved to Firestore") }
            .addOnFailureListener { Log.d("FIRESTORE", "Failed to save chat") }
    }

    fun chatNotExist(selectedUsersId: ArrayList<String>): String {
        println(chatList)
        for(chat in chatList){
            val sortedSelected = selectedUsersId.sorted()
            val sortedStored = chat.usersInChat.sorted()
            println("selected users id:${sortedSelected}")
            println("users in chat id:${sortedStored}")
            if(sortedSelected == sortedStored){
                return chat.id
            }
        }
        return "no chat"
    }
}