package com.example.chattapp.models

import io.realm.annotations.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

open class Chat {

    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var usersInChat = ArrayList<String>()

    override fun toString(): String {
        return "Chat(id='$id', usersInChat=$usersInChat)"
    }
}