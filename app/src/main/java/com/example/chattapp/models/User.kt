package com.example.chattapp.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class User : RealmObject() {

    @PrimaryKey
    var id = UUID.randomUUID().toString()

    var name: String = "dummy"
    var lastName: String = "user"

    @Required
    var userName: String = "user:$id"

    @Required
    var eMail: String = "user@chatApp.com"

    @Required
    var password: String = "password"


    override fun toString(): String {
        return "User(id='$id', name='$name', lastName='$lastName', userName='$userName', eMail='$eMail', password='$password')"
    }

}