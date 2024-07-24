package com.example.threadsclone.viewmodel

import androidx.lifecycle.ViewModel
import com.example.threadsclone.model.ThreadModel
import com.example.threadsclone.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel:ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    private val threadRef = db.getReference("threads")
    private val _threads = MutableStateFlow<List<Pair<ThreadModel,UserModel>>>(emptyList())
    val threads:StateFlow<List<Pair<ThreadModel,UserModel>>> = _threads


    init {
        fetchThreadsAndUsers {
            _threads.value = it
        }
    }

    private fun fetchThreadsAndUsers(onResult:(List<Pair<ThreadModel,UserModel>>)->Unit) {
        threadRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<Pair<ThreadModel,UserModel>>()
                for(threadSnapshot in snapshot.children) {
                    val thread = threadSnapshot.getValue(ThreadModel::class.java)

                    thread?.let{
                        fetchUserFromThread(it){
                            user->
                            result.add(0,it to user)
                            if (result.size == snapshot.childrenCount.toInt()){
                                onResult(result)
                            }
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
    private fun fetchUserFromThread(thread:ThreadModel,onResult:(UserModel)->Unit){

        db.getReference("users").child(thread.userId).addListenerForSingleValueEvent(
            object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    user?.let(onResult)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
        )
    }

}