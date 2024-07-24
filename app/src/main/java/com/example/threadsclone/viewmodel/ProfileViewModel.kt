package com.example.threadsclone.viewmodel

import androidx.lifecycle.ViewModel
import com.example.threadsclone.model.ThreadModel
import com.example.threadsclone.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    private val threadRef = db.getReference("threads")
    private val usersRef = db.getReference("users")
    private val firestoreDb = Firebase.firestore
    private val _threads = MutableStateFlow<List<ThreadModel>>(emptyList())
    val threads: StateFlow<List<ThreadModel>> = _threads
    private val _user = MutableStateFlow(UserModel())
    val users: StateFlow<UserModel> = _user
    private val _followersList = MutableStateFlow<List<String>>(emptyList())
    val followersList: StateFlow<List<String>> = _followersList
    private val _followingList = MutableStateFlow<List<String>>(emptyList())
    val followingList: StateFlow<List<String>> = _followingList


    fun fetchUser(uid: String) {
        usersRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                if (user != null) {
                    _user.value = user
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun fetchThreads(uid: String) {
        threadRef.orderByChild("userId").equalTo(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val threadList = snapshot.children.mapNotNull {
                        it.getValue(ThreadModel::class.java)
                    }
                    _threads.value = threadList
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }

    fun followUsers(userId: String, currentUserId: String) {
        val ref = firestoreDb.collection("following").document(currentUserId)
        val followerRef = firestoreDb.collection("followers").document(userId)

        ref.update("followingIds", FieldValue.arrayUnion(userId))
        followerRef.update("followerIds", FieldValue.arrayUnion(currentUserId))
    }

    fun getFollowers(userId:String) {
        firestoreDb.collection("followers").document(userId)
            .addSnapshotListener { value, error ->
                val followerIds = value?.get("followerIds") as? List<String>?: listOf()
                _followersList.value=followerIds
            }
    }

    fun getFollowing(userId: String) {
        firestoreDb.collection("following").document(userId)
            .addSnapshotListener { value, error ->
                val followingIds = value?.get("followingIds") as? List<String>?: listOf()
                _followingList.value=followingIds
            }
    }

}