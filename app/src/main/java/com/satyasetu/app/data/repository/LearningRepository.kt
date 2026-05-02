package com.satyasetu.app.data.repository

import com.google.firebase.database.*
import com.satyasetu.app.data.model.Lesson
import com.satyasetu.app.data.model.QuizQuestion
import com.satyasetu.app.util.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearningRepository @Inject constructor(
    private val database: FirebaseDatabase
) {
    private val lessonsRef = database.reference.child(Constants.DB_LESSONS)

    fun observeLessons(): Flow<List<Lesson>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lessons = snapshot.children.mapNotNull { it.getValue(Lesson::class.java) }
                    .filter { it.isPublished }.sortedBy { it.order }
                trySend(lessons)
            }
            override fun onCancelled(error: DatabaseError) { close(error.toException()) }
        }
        lessonsRef.addValueEventListener(listener)
        awaitClose { lessonsRef.removeEventListener(listener) }
    }

    fun observeLessonsByCategory(category: String): Flow<List<Lesson>> = callbackFlow {
        val query = lessonsRef.orderByChild("category").equalTo(category)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lessons = snapshot.children.mapNotNull { it.getValue(Lesson::class.java) }
                    .sortedBy { it.order }
                trySend(lessons)
            }
            override fun onCancelled(error: DatabaseError) { close(error.toException()) }
        }
        query.addValueEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }

    suspend fun markLessonComplete(userId: String, lessonId: String): Result<Unit> = try {
        database.reference.child(Constants.DB_USERS).child(userId)
            .child("lessonsCompleted").child(lessonId).setValue(true).await()
        Result.success(Unit)
    } catch (e: Exception) { Result.failure(e) }

    suspend fun saveQuizScore(userId: String, lessonId: String, score: Int): Result<Unit> = try {
        database.reference.child(Constants.DB_USERS).child(userId)
            .child("quizScores").child(lessonId).setValue(score).await()
        Result.success(Unit)
    } catch (e: Exception) { Result.failure(e) }

    suspend fun seedSampleLessons() {
        val samples = listOf(
            Lesson("l1", "हाथ धोने का महत्व", "जानिए क्यों हाथ धोना ज़रूरी है",
                Lesson.CATEGORY_HEALTH,
                "हाथ धोना सबसे आसान और प्रभावी तरीका है बीमारियों से बचने का।\n\n" +
                "1. खाना खाने से पहले हाथ धोएँ\n2. शौचालय के बाद साबुन से धोएँ\n" +
                "3. कम से कम 20 सेकंड तक धोएँ\n4. साफ़ पानी और साबुन का उपयोग करें",
                order = 1, estimatedMinutes = 5,
                quiz = listOf(
                    QuizQuestion("q1", "हाथ कितने सेकंड तक धोने चाहिए?",
                        listOf("5 सेकंड", "10 सेकंड", "20 सेकंड", "1 मिनट"), 2,
                        "कम से कम 20 सेकंड तक हाथ धोना चाहिए"),
                    QuizQuestion("q2", "हाथ कब धोने चाहिए?",
                        listOf("सिर्फ सुबह", "खाने से पहले और शौचालय के बाद", "सिर्फ रात को", "कभी नहीं"), 1,
                        "खाने से पहले और शौचालय जाने के बाद ज़रूर धोएँ")
                )),
            Lesson("l2", "OTP और पासवर्ड सुरक्षा", "डिजिटल धोखाधड़ी से कैसे बचें",
                Lesson.CATEGORY_DIGITAL_FRAUD,
                "ऑनलाइन धोखाधड़ी से बचने के नियम:\n\n" +
                "1. OTP किसी को न बताएँ\n2. अनजान लिंक पर क्लिक न करें\n" +
                "3. बैंक कभी फोन पर जानकारी नहीं माँगता\n4. मज़बूत पासवर्ड बनाएँ",
                order = 1, estimatedMinutes = 7,
                quiz = listOf(
                    QuizQuestion("q3", "OTP किसे बताना चाहिए?",
                        listOf("बैंक कर्मचारी को", "दोस्त को", "किसी को नहीं", "पुलिस को"), 2,
                        "OTP कभी किसी को नहीं बताना चाहिए"),
                    QuizQuestion("q4", "अनजान लिंक पर क्या करना चाहिए?",
                        listOf("तुरंत क्लिक करें", "अनदेखा करें और डिलीट करें", "शेयर करें", "डाउनलोड करें"), 1,
                        "अनजान लिंक पर कभी क्लिक न करें")
                )),
            Lesson("l3", "मतदान का अधिकार", "आपका वोट, आपकी ताकत",
                Lesson.CATEGORY_VOTING,
                "मतदान के बारे में जानकारी:\n\n" +
                "1. 18 साल से ऊपर हर नागरिक वोट दे सकता है\n" +
                "2. वोटर ID बनवाना मुफ़्त है\n" +
                "3. आपका वोट गोपनीय है\n" +
                "4. कोई आपको वोट देने के लिए मजबूर नहीं कर सकता",
                order = 1, estimatedMinutes = 6,
                quiz = listOf(
                    QuizQuestion("q5", "वोट देने की उम्र क्या है?",
                        listOf("16 साल", "18 साल", "21 साल", "25 साल"), 1,
                        "18 साल से ऊपर हर नागरिक वोट दे सकता है"),
                    QuizQuestion("q6", "क्या कोई आपका वोट देख सकता है?",
                        listOf("हाँ", "नहीं, वोट गोपनीय है", "सिर्फ सरकार", "सिर्फ पार्टी"), 1,
                        "आपका वोट पूरी तरह गोपनीय है")
                ))
        )
        samples.forEach { lessonsRef.child(it.lessonId).setValue(it.toMap()) }
    }
}
