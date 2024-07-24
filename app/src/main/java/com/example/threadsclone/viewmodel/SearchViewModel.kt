package com.example.threadsclone.viewmodel

import androidx.lifecycle.ViewModel
import com.example.threadsclone.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel:ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    private val userRef = db.getReference("users")
    private val _users = MutableStateFlow<List<UserModel>>(emptyList())
    val users:StateFlow<List<UserModel>> = _users


    init {
        fetchUsers {
            _users.value = it
        }
    }

    private fun fetchUsers(onResult:(List<UserModel>)->Unit) {
        userRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<UserModel>()
                for(userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserModel::class.java)

                    user?.let{
                            result.add(0,it)
                    }

                }
                onResult(result)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}