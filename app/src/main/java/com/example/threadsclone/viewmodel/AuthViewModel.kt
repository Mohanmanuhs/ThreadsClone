package com.example.threadsclone.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel:ViewModel() {


    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")

    private val _firebaseUser = MutableStateFlow(auth.currentUser)
    val firebaseUser:StateFlow<FirebaseUser?> = _firebaseUser

    private val _error = MutableStateFlow("")
    val error:StateFlow<String> = _error

    fun login(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.value = auth.currentUser
                }else{
                    _error.value = "Something went wrong"
                }
            }
    }
    fun register(email:String, password:String,name:String,username:String,bio:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.value = auth.currentUser
                }else{
                    _error.value = "Something went wrong"
                }
            }
    }


}