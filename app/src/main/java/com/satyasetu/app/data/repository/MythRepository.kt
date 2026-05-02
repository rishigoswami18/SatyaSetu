package com.satyasetu.app.data.repository

import com.google.firebase.database.*
import com.satyasetu.app.data.model.Myth
import com.satyasetu.app.util.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MythRepository @Inject constructor(
    private val database: FirebaseDatabase
) {
    private val mythsRef = database.reference.child(Constants.DB_MYTHS)

    fun observeMyths(): Flow<List<Myth>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val myths = snapshot.children.mapNotNull { it.getValue(Myth::class.java) }
                    .sortedByDescending { it.createdAt }
                trySend(myths)
            }
            override fun onCancelled(error: DatabaseError) { close(error.toException()) }
        }
        mythsRef.addValueEventListener(listener)
        awaitClose { mythsRef.removeEventListener(listener) }
    }

    suspend fun likeMythFact(mythId: String): Result<Unit> = try {
        val ref = mythsRef.child(mythId).child("likes")
        val current = ref.get().await().getValue(Int::class.java) ?: 0
        ref.setValue(current + 1).await()
        Result.success(Unit)
    } catch (e: Exception) { Result.failure(e) }

    suspend fun seedSampleMyths() {
        val samples = listOf(
            Myth("m1", "काला जादू से बीमारी होती है",
                "बीमारियाँ बैक्टीरिया, वायरस से होती हैं। डॉक्टर से इलाज कराएँ।",
                Myth.CATEGORY_HEALTH,
                "चिकित्सा विज्ञान के अनुसार सभी बीमारियों के वैज्ञानिक कारण होते हैं।",
                likes = 156),
            Myth("m2", "ग्रहण में खाना नहीं खाना चाहिए",
                "ग्रहण प्राकृतिक खगोलीय घटना है। खाना खाने से कोई नुकसान नहीं।",
                Myth.CATEGORY_SCIENCE,
                "सूर्य ग्रहण तब होता है जब चंद्रमा सूर्य और पृथ्वी के बीच आ जाता है।",
                likes = 234),
            Myth("m3", "OTP बताने से बैंक अकाउंट सुरक्षित होता है",
                "OTP कभी किसी को न बताएँ! बैंक कभी OTP नहीं माँगता।",
                Myth.CATEGORY_GENERAL,
                "OTP आपके अकाउंट की सुरक्षा के लिए है।",
                likes = 567),
            Myth("m4", "ताबीज़ से बच्चों की नज़र उतरती है",
                "नज़र लगना अंधविश्वास है। बच्चों के लिए टीकाकरण और पोषण ज़रूरी है।",
                Myth.CATEGORY_HEALTH,
                "बच्चों को बीमारियों से बचाने के लिए सही टीकाकरण ज़रूरी है।",
                likes = 312),
            Myth("m5", "ज़्यादा केमिकल से फसल हमेशा अच्छी होती है",
                "ज़्यादा केमिकल से मिट्टी खराब होती है। जैविक खेती अपनाएँ।",
                Myth.CATEGORY_FARMING,
                "अत्यधिक रासायनिक उर्वरक मिट्टी की उर्वरता कम करते हैं।",
                likes = 198)
        )
        samples.forEach { mythsRef.child(it.mythId).setValue(it.toMap()) }
    }
}
