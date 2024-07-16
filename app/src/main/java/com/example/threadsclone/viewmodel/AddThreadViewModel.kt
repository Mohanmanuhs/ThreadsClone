package com.example.threadsclone.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.threadsclone.model.ThreadModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class AddThreadViewModel:ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    private val userRef = db.getReference("threads")
    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("threads/${UUID.randomUUID()}.jpg")
    private val _isPosted = MutableStateFlow(false)
    val isPosted:StateFlow<Boolean> = _isPosted



     fun saveImage(thread:String,imageUri: Uri, userId: String) {
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(thread,userId,it.toString())
            }

        }
    }

    fun saveData(
        thread: String,
        userId: String,
        imageUrl:String
    ) {
        val user = ThreadModel(thread,userId,imageUrl,System.currentTimeMillis().toString())
        userRef.child(userRef.push().key!!).setValue(user)
            .addOnSuccessListener {
                _isPosted.value =true
            }
            .addOnFailureListener {
                _isPosted.value=false
            }

    }

}