package com.satyasetu.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.satyasetu.app.data.model.User
import com.satyasetu.app.util.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository handling Firebase Authentication and user profile management
 */
@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) {

    val currentUser: FirebaseUser? get() = auth.currentUser

    val isLoggedIn: Boolean get() = auth.currentUser != null

    /**
     * Observe auth state changes as a Flow
     */
    fun observeAuthState(): Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    /**
     * Register new user with email and password
     */
    suspend fun registerWithEmail(
        email: String,
        password: String,
        name: String,
        village: String,
        district: String,
        state: String
    ): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("Registration failed")

            firebaseUser.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(name.trim())
                    .build()
            ).await()

            // Create user profile in Realtime Database
            val user = User(
                uid = firebaseUser.uid,
                name = name.trim(),
                email = email.trim(),
                villageName = village,
                district = district,
                state = state,
                role = User.ROLE_USER,
                joinedAt = System.currentTimeMillis()
            )

            database.reference
                .child(Constants.DB_USERS)
                .child(firebaseUser.uid)
                .setValue(user.toMap())
                .await()

            Result.success(firebaseUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Login with email and password
     */
    suspend fun loginWithEmail(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("Login failed")
            Result.success(firebaseUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get user profile from Realtime Database
     */
    suspend fun getUserProfile(uid: String): Result<User> {
        return try {
            val snapshot = database.reference
                .child(Constants.DB_USERS)
                .child(uid)
                .get()
                .await()

            val user = snapshot.getValue(User::class.java)
                ?: throw Exception("User profile not found")
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Update user profile
     */
    suspend fun updateUserProfile(user: User): Result<Unit> {
        return try {
            database.reference
                .child(Constants.DB_USERS)
                .child(user.uid)
                .updateChildren(user.toMap() as Map<String, Any>)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Sign out
     */
    fun signOut() {
        auth.signOut()
    }

    /**
     * Send password reset email
     */
    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
