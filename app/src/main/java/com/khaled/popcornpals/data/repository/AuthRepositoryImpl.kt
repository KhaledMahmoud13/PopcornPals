package com.khaled.popcornpals.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.khaled.popcornpals.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {
    override fun registerUser(
        email: String,
        password: String,
        onComplete: () -> Unit,
        onFail: (message: String) -> Unit,
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete()
                } else {
                    try {
                        throw task.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        onFail("Authentication failed, Password should be at least 6 characters")
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        onFail("Authentication failed, Invalid email entered")
                    } catch (e: FirebaseAuthUserCollisionException) {
                        onFail("Authentication failed, Email already registered.")
                    } catch (e: Exception) {
                        onFail(e.message.toString())
                    }
                }
            }
            .addOnFailureListener { exception ->
                exception.localizedMessage?.let { e -> onFail(e) }
            }
    }

    override fun loginUser(
        email: String,
        password: String,
        onComplete: () -> Unit,
        onFail: (message: String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete()
                }
            }
            .addOnFailureListener {
                onFail("Authentication failed, Check email and password")
            }
    }

    override fun forgotPassword(
        email: String,
        onComplete: () -> Unit,
        onFail: (message: String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete()
                } else {
                    onFail(task.exception?.message!!)
                }
            }
            .addOnFailureListener {
                onFail("Check email")
            }
    }

    override fun logout(result: () -> Unit) {
        auth.signOut()
        result.invoke()
    }
}