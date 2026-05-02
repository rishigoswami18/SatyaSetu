package com.satyasetu.app.data.repository

import com.google.firebase.database.*
import com.satyasetu.app.data.model.DevelopmentItem
import com.satyasetu.app.util.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackerRepository @Inject constructor(
    private val database: FirebaseDatabase
) {
    private val devRef = database.reference.child(Constants.DB_DEVELOPMENT)

    fun observeDevelopmentItems(): Flow<List<DevelopmentItem>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull { it.getValue(DevelopmentItem::class.java) }
                    .sortedByDescending { it.lastUpdated }
                trySend(items)
            }
            override fun onCancelled(error: DatabaseError) { close(error.toException()) }
        }
        devRef.addValueEventListener(listener)
        awaitClose { devRef.removeEventListener(listener) }
    }

    fun observeByArea(area: String): Flow<List<DevelopmentItem>> = callbackFlow {
        val query = devRef.orderByChild("area").equalTo(area)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull { it.getValue(DevelopmentItem::class.java) }
                trySend(items)
            }
            override fun onCancelled(error: DatabaseError) { close(error.toException()) }
        }
        query.addValueEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }

    suspend fun seedSampleData() {
        val samples = listOf(
            DevelopmentItem("d1", "रामपुर", "लखनऊ", "उत्तर प्रदेश",
                DevelopmentItem.CATEGORY_ROADS, "मुख्य सड़क निर्माण",
                "गाँव से शहर तक पक्की सड़क", true, source = "जिला कार्यालय"),
            DevelopmentItem("d2", "रामपुर", "लखनऊ", "उत्तर प्रदेश",
                DevelopmentItem.CATEGORY_ELECTRICITY, "24 घंटे बिजली आपूर्ति",
                "गाँव में नियमित बिजली", false, source = "बिजली विभाग"),
            DevelopmentItem("d3", "रामपुर", "लखनऊ", "उत्तर प्रदेश",
                DevelopmentItem.CATEGORY_SCHOOLS, "प्राथमिक विद्यालय",
                "सरकारी प्राथमिक विद्यालय की स्थिति", true, source = "शिक्षा विभाग"),
            DevelopmentItem("d4", "रामपुर", "लखनऊ", "उत्तर प्रदेश",
                DevelopmentItem.CATEGORY_WATER, "स्वच्छ पेयजल",
                "पाइप से साफ़ पानी की आपूर्ति", false, source = "जल विभाग"),
            DevelopmentItem("d5", "रामपुर", "लखनऊ", "उत्तर प्रदेश",
                DevelopmentItem.CATEGORY_HOSPITAL, "प्राथमिक स्वास्थ्य केंद्र",
                "गाँव में स्वास्थ्य सेवा", true, source = "स्वास्थ्य विभाग"),
            DevelopmentItem("d6", "रामपुर", "लखनऊ", "उत्तर प्रदेश",
                DevelopmentItem.CATEGORY_INTERNET, "इंटरनेट कनेक्टिविटी",
                "4G/5G नेटवर्क कवरेज", false, source = "दूरसंचार विभाग")
        )
        samples.forEach { devRef.child(it.itemId).setValue(it.toMap()) }
    }
}
