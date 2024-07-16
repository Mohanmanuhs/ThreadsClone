package com.example.threadsclone.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.threadsclone.model.UserModel
import com.example.threadsclone.util.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class AuthViewModel:ViewModel() {


    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    private val userRef = db.getReference("users")
    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")
    private val _firebaseUser = MutableStateFlow(auth.currentUser)
    val firebaseUser:StateFlow<FirebaseUser?> = _firebaseUser

    private val _error = MutableStateFlow<String?>(null)
    val error:StateFlow<String?> = _error

    fun login(email: String, password: String, context: Context){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.value = auth.currentUser
                    getData(auth.currentUser!!.uid,context)
                }else{
                    _error.value = it.exception?.message
                }
            }
    }

    private fun getData(uid:String,context: Context) {
        userRef.child(uid).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(UserModel::class.java)
                if (userData != null) {
                    SharedPref.saveData(userData.name,userData.email,userData.username,context,userData.bio,userData.toString)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun register(email:String, password:String,name:String,username:String,bio:String,imageUri:Uri,context:Context){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.value = auth.currentUser
                    saveImage(email,password,name,username,bio,imageUri, auth.currentUser?.uid,context)
                }else{
                    _error.value = "Something went wrong"
                }
            }
    }

    private fun saveImage(email: String, password: String, name: String, username: String, bio: String, imageUri: Uri, uid: String?,context:Context) {
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(email, password, name, username, bio, it.toString(), uid,context)
            }

        }
    }

    private fun saveData(
        email: String,
        password: String,
        name: String,
        username: String,
        bio: String,
        toString: String,
        uid: String?,
        context:Context
    ) {
        val user = UserModel(email, password, name, username, bio, toString,uid!!)
        userRef.child(uid).setValue(user)
            .addOnSuccessListener {
                SharedPref.saveData(name,email,username,context,bio,toString)
            }
            .addOnFailureListener {  }

    }
    fun logout(){
        auth.signOut()
        _firebaseUser.value = null
    }


}