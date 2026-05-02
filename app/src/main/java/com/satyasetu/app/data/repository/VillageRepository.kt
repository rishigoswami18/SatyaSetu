package com.satyasetu.app.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.satyasetu.app.data.model.Village
import com.satyasetu.app.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private val Context.dataStore by preferencesDataStore(name = "village_prefs")

@Singleton
class VillageRepository @Inject constructor(
    private val database: FirebaseDatabase,
    @ApplicationContext private val context: Context
) {
    private val villagesRef = database.reference.child(Constants.DB_VILLAGES)
    private val usersRef = database.reference.child(Constants.DB_USERS)

    private val villageIdKey = stringPreferencesKey("saved_village_id")
    private val villageNameKey = stringPreferencesKey("saved_village_name")

    suspend fun getCachedVillageId(): String? {
        return context.dataStore.data.map { it[villageIdKey] }.first()
    }

    suspend fun getCachedVillageName(): String? {
        return context.dataStore.data.map { it[villageNameKey] }.first()
    }

    suspend fun cacheVillage(villageId: String, villageName: String) {
        context.dataStore.edit { preferences ->
            preferences[villageIdKey] = villageId
            preferences[villageNameKey] = villageName
        }
    }

    suspend fun assignVillageToUser(
        userId: String,
        villageName: String,
        lat: Double,
        lng: Double,
        district: String,
        state: String
    ): Result<Village> {
        return try {
            val querySnapshot = villagesRef.orderByChild("name").equalTo(villageName).get().await()

            var village: Village? = null
            if (querySnapshot.exists() && querySnapshot.children.count() > 0) {
                village = querySnapshot.children.first().getValue(Village::class.java)
            }

            if (village == null) {
                val newVillageId = villagesRef.push().key ?: UUID.randomUUID().toString()
                village = Village(
                    villageId = newVillageId,
                    name = villageName,
                    lat = lat,
                    lng = lng,
                    district = district,
                    state = state,
                    userCount = 1
                )
                villagesRef.child(newVillageId).setValue(village.toMap()).await()
            } else {
                incrementCounter(
                    villagesRef.child(village.villageId).child("userCount"),
                    "Unable to update village member count."
                )
            }

            usersRef.child(userId).updateChildren(
                mapOf(
                    "villageId" to village.villageId,
                    "villageName" to village.name,
                    "lat" to lat,
                    "lng" to lng
                )
            ).await()

            cacheVillage(village.villageId, village.name)

            Result.success(village)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun observeVillage(villageId: String): Flow<Village?> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot.getValue(Village::class.java))
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        val ref = villagesRef.child(villageId)
        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    private suspend fun incrementCounter(reference: DatabaseReference, failureMessage: String) {
        suspendCancellableCoroutine { continuation ->
            reference.runTransaction(object : Transaction.Handler {
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val currentValue = currentData.getValue(Int::class.java) ?: 0
                    currentData.value = currentValue + 1
                    return Transaction.success(currentData)
                }

                override fun onComplete(
                    error: DatabaseError?,
                    committed: Boolean,
                    currentData: DataSnapshot?
                ) {
                    when {
                        error != null -> continuation.resumeWithException(error.toException())
                        committed -> continuation.resume(Unit)
                        else -> continuation.resumeWithException(IllegalStateException(failureMessage))
                    }
                }
            })
        }
    }
}
