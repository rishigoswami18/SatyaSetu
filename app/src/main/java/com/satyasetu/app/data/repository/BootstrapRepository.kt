package com.satyasetu.app.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.database.FirebaseDatabase
import com.satyasetu.app.data.model.Post
import com.satyasetu.app.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

private val Context.bootstrapDataStore by preferencesDataStore(name = "bootstrap_prefs")

/**
 * BootstrapRepository — Seeds the database with initial content on first launch.
 *
 * This ensures the app doesn't feel empty when new users install it.
 * Contains 30+ curated posts covering:
 * - Health awareness
 * - Digital safety / fraud prevention
 * - Government schemes information
 * - Science / superstition debunking
 * - Village news templates
 * - Farming tips
 *
 * Strategy: "Start Data = Tu + Friends" — seed the feed so users engage from day 1.
 */
@Singleton
class BootstrapRepository @Inject constructor(
    private val database: FirebaseDatabase,
    @ApplicationContext private val context: Context
) {
    private val seededKey = booleanPreferencesKey("data_seeded_v2")

    /**
     * Check if bootstrap data has been seeded already.
     */
    suspend fun isSeeded(): Boolean {
        return context.bootstrapDataStore.data.map { it[seededKey] ?: false }.first()
    }

    /**
     * Seed the database with initial bootstrap posts.
     * Only runs once — skips if already seeded.
     *
     * @param villageId The village ID to seed data for
     * @return Number of posts seeded
     */
    suspend fun seedData(villageId: String): Int {
        if (isSeeded()) return 0

        val postsRef = database.reference.child(Constants.DB_POSTS)
        var count = 0

        BOOTSTRAP_POSTS.forEach { postTemplate ->
            try {
                val postId = postsRef.push().key ?: return@forEach
                val post = postTemplate.copy(
                    postId = postId,
                    villageId = villageId,
                    timestamp = System.currentTimeMillis() - (count * 3600_000L) // Stagger timestamps
                )
                postsRef.child(postId).setValue(post.toMap()).await()
                count++
            } catch (_: Exception) {
                // Continue seeding even if one fails
            }
        }

        // Mark as seeded
        context.bootstrapDataStore.edit { it[seededKey] = true }

        return count
    }

    /**
     * Force re-seed (admin function).
     */
    suspend fun forceSeed(villageId: String): Int {
        context.bootstrapDataStore.edit { it[seededKey] = false }
        return seedData(villageId)
    }

    companion object {
        /**
         * 30 curated bootstrap posts that make the app feel alive.
         * Mix of awareness, news, issues, and education.
         */
        val BOOTSTRAP_POSTS = listOf(
            // ── Health Awareness ──────────────────────────────────────────
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🏥 बुखार में क्या करें?\n\n• खूब पानी पिएं\n• हल्का भोजन करें\n• पैरासिटामॉल ले सकते हैं\n• 3 दिन से ज्यादा हो तो डॉक्टर के पास जाएं\n\n⚠️ तांत्रिक के पास जाना सही नहीं है!",
                type = Post.TYPE_AWARENESS,
                likes = 45
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "💧 गंदा पानी = बीमारी\n\nगंदा पानी पीने से हो सकती हैं:\n• दस्त (डायरिया)\n• टाइफाइड\n• पीलिया\n\n✅ पानी हमेशा उबालकर पीएं\n✅ बरसात में विशेष ध्यान रखें",
                type = Post.TYPE_AWARENESS,
                likes = 67
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🐍 सांप काटने पर सही कदम:\n\n✅ शांत रहें, घबराएं नहीं\n✅ तुरंत 108 कॉल करें\n✅ सरकारी अस्पताल जाएं — एंटी-वेनम मुफ्त मिलता है\n\n❌ तांत्रिक/ओझा के पास जाना = जान का खतरा!\n❌ जहर चूसना बिल्कुल गलत है!",
                type = Post.TYPE_AWARENESS,
                likes = 210
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "👶 टीकाकरण (Vaccination) जरूरी है!\n\nबच्चों को समय पर टीके लगवाएं:\n• पोलियो\n• BCG\n• हेपेटाइटिस B\n• खसरा (Measles)\n\n📍 नजदीकी आंगनबाड़ी या PHC में मुफ्त टीकाकरण होता है।",
                type = Post.TYPE_AWARENESS,
                likes = 156
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "☀️ गर्मी से बचाव:\n\n• दोपहर 12-3 बजे धूप में न निकलें\n• ORS का घोल पीते रहें\n• सिर ढक कर रखें\n• हल्के रंग के कपड़े पहनें\n\nलू लगने पर तुरंत ठंडी जगह पर जाएं और डॉक्टर को दिखाएं।",
                type = Post.TYPE_AWARENESS,
                likes = 89
            ),

            // ── Digital Safety ────────────────────────────────────────────
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🚨 OTP फ्रॉड से बचें!\n\nकभी किसी को OTP न दें — चाहे वो:\n• बैंक अधिकारी बने\n• KYC के लिए कहे\n• लॉटरी जीतने का बोले\n\n📞 फ्रॉड होने पर तुरंत 1930 पर कॉल करें!\n🌐 cybercrime.gov.in पर शिकायत करें",
                type = Post.TYPE_AWARENESS,
                likes = 320
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "⚠️ फेक न्यूज़ कैसे पहचानें?\n\n1. WhatsApp पर 'forwarded many times' लिखा हो — सोचकर शेयर करें\n2. कोई सोर्स (स्रोत) न दिया हो — शायद झूठ है\n3. बहुत डराने वाला हो — शायद फेक है\n4. Google पर सर्च करें — सच पता चलेगा\n\n🤝 फेक न्यूज़ फैलाना = समाज को नुकसान",
                type = Post.TYPE_AWARENESS,
                likes = 178
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🔐 पासवर्ड सुरक्षा:\n\n✅ मजबूत पासवर्ड कैसे बनाएं:\n• कम से कम 8 अक्षर\n• अक्षर + नंबर + चिन्ह मिलाएं\n• हर अकाउंट के लिए अलग पासवर्ड\n\n❌ कभी न करें:\n• जन्मदिन को पासवर्ड न बनाएं\n• 12345678 जैसा आसान पासवर्ड न रखें\n• पासवर्ड किसी को न बताएं",
                type = Post.TYPE_AWARENESS,
                likes = 95
            ),

            // ── Government Schemes ───────────────────────────────────────
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "💰 पीएम किसान सम्मान निधि:\n\n• हर साल ₹6,000 सीधे बैंक खाते में\n• ₹2,000 हर 4 महीने\n• 2 हेक्टेयर तक जमीन वाले किसान पात्र\n\n📱 pmkisan.gov.in पर स्टेटस चेक करें\n📍 CSC केंद्र पर जाकर आवेदन करें",
                type = Post.TYPE_NEWS,
                likes = 234
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🏥 आयुष्मान भारत योजना:\n\n• ₹5 लाख तक का मुफ्त इलाज\n• सरकारी और निजी दोनों अस्पतालों में\n• BPL परिवार पात्र हैं\n\n📱 pmjay.gov.in पर चेक करें\n📍 जन सेवा केंद्र पर कार्ड बनवाएं\n\nबीमारी में पैसे की चिंता नहीं!",
                type = Post.TYPE_NEWS,
                likes = 189
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🏠 प्रधानमंत्री आवास योजना (ग्रामीण):\n\n• पक्का मकान बनाने के लिए ₹1.20 लाख\n• बेघर और कच्चे मकान वाले पात्र\n• SC/ST/अल्पसंख्यक को प्राथमिकता\n\n📍 ग्राम पंचायत में आवेदन करें\n📱 pmayg.nic.in पर जानकारी देखें",
                type = Post.TYPE_NEWS,
                likes = 145
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "👩 उज्ज्वला योजना:\n\n• गरीब महिलाओं को मुफ्त गैस कनेक्शन\n• पहला सिलेंडर और चूल्हा मुफ्त\n• BPL परिवार पात्र\n\n📍 LPG वितरक या CSC पर आवेदन करें\nज़रूरी: BPL कार्ड + आधार + बैंक खाता",
                type = Post.TYPE_NEWS,
                likes = 112
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "📚 छात्रवृत्ति (Scholarship) के लिए आवेदन करें!\n\nSC/ST/OBC छात्रों के लिए:\n• ट्यूशन फीस माफ\n• किताबों के लिए अनुदान\n• मेंटेनेंस भत्ता\n\n📱 scholarships.gov.in पर ऑनलाइन आवेदन\nजरूरी: आय प्रमाण पत्र + जाति प्रमाण पत्र + मार्कशीट",
                type = Post.TYPE_NEWS,
                likes = 167
            ),

            // ── Superstition Debunking ────────────────────────────────────
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🔬 सच या गलत: बिल्ली रास्ता काटे तो अपशकुन?\n\n❌ गलत! यह सिर्फ पुराना डर है।\n\n✅ विज्ञान कहता है:\nबिल्ली एक जानवर है। उसके रास्ता काटने का आपकी किस्मत से कोई संबंध नहीं।\n\n💡 सोच बदलो, विज्ञान अपनाओ!",
                type = Post.TYPE_AWARENESS,
                likes = 267
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🌳 सच या गलत: पीपल के पेड़ पर भूत होते हैं?\n\n❌ गलत!\n\n✅ विज्ञान:\n• रात में पेड़ CO2 (कार्बन डाइऑक्साइड) छोड़ते हैं\n• इससे सांस लेने में दिक्कत हो सकती है\n• भूत नहीं, बस गैस का असर है!\n\n💡 डर नहीं, समझ चाहिए!",
                type = Post.TYPE_AWARENESS,
                likes = 198
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "👻 सच या गलत: झाड़-फूंक से बीमारी ठीक होती है?\n\n❌ बिल्कुल गलत!\n\n• कोई भी बीमारी झाड़-फूंक से ठीक नहीं होती\n• इलाज में देरी से जान का खतरा\n• तांत्रिक अक्सर पैसे ठगते हैं\n\n✅ बीमार हों तो डॉक्टर के पास जाएं, ओझा के पास नहीं!",
                type = Post.TYPE_AWARENESS,
                likes = 312
            ),

            // ── Farming Tips ─────────────────────────────────────────────
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🌾 पराली न जलाएं!\n\n❌ पराली जलाने से:\n• मिट्टी के फायदेमंद कीट मर जाते हैं\n• मिट्टी की ताकत कम होती है\n• प्रदूषण फैलता है\n\n✅ बेहतर विकल्प:\n• पराली को मिट्टी में मिला दें\n• कंपोस्ट बनाएं\n• हैप्पी सीडर मशीन का इस्तेमाल करें",
                type = Post.TYPE_AWARENESS,
                likes = 134
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🧪 मिट्टी की जांच करवाएं — मुफ्त!\n\n• नजदीकी कृषि विज्ञान केंद्र (KVK) पर जांच मुफ्त\n• सही खाद = कम खर्च, ज्यादा फसल\n• बुवाई से पहले जांच जरूर कराएं\n\n📍 KVK का पता: ब्लॉक कार्यालय से पूछें",
                type = Post.TYPE_AWARENESS,
                likes = 78
            ),

            // ── Template Village Issues (show users what kind of posts to make) ──
            Post(
                userId = "ramsingh_example",
                userName = "राम सिंह",
                description = "⚡ वार्ड नंबर 3 में कल रात से बिजली नहीं आ रही। ट्रांसफार्मर खराब है। कृपया बिजली विभाग को सूचना दी जाए।",
                type = Post.TYPE_ISSUE,
                likes = 45
            ),
            Post(
                userId = "sita_example",
                userName = "सीता देवी",
                description = "🚰 पीने के पानी की टंकी 4 दिन से साफ नहीं हुई। पानी गंदा आ रहा है। सरपंच जी कृपया ध्यान दें।",
                type = Post.TYPE_ISSUE,
                likes = 67
            ),
            Post(
                userId = "mukesh_example",
                userName = "मुकेश कुमार",
                description = "🛣️ मुख्य सड़क पर बड़ा गड्ढा हो गया है। बारिश में यहां दुर्घटना होने का डर है। PWD विभाग से अनुरोध है कि जल्द मरम्मत करवाएं।",
                type = Post.TYPE_ISSUE,
                likes = 89
            ),
            Post(
                userId = "pooja_example",
                userName = "पूजा शर्मा",
                description = "💡 मुख्य सड़क की स्ट्रीट लाइट एक हफ्ते से बंद है। रात में आने-जाने में डर लगता है। कृपया ठीक करवाएं।",
                type = Post.TYPE_ISSUE,
                likes = 56
            ),

            // ── News Templates ───────────────────────────────────────────
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "📢 आज आंगनबाड़ी केंद्र में बच्चों का मुफ्त टीकाकरण शिविर लगा है!\n\n⏰ समय: सुबह 10 बजे से दोपहर 3 बजे तक\n📍 स्थान: प्राथमिक स्वास्थ्य केंद्र\n\nसभी माता-पिता अपने बच्चों को जरूर लाएं।",
                type = Post.TYPE_NEWS,
                likes = 78
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🏫 सरकारी स्कूल में 10वीं कक्षा के लिए अतिरिक्त कक्षाएं (Extra Classes) शुरू!\n\n⏰ रोज शाम 4 से 6 बजे\n📚 गणित, विज्ञान और अंग्रेजी\n\nसभी छात्र समय पर आएं। 📖",
                type = Post.TYPE_NEWS,
                likes = 56
            ),
            Post(
                userId = "anil_example",
                userName = "अनिल वर्मा",
                description = "🌾 राशन की दुकान पर इस महीने का अनाज आ गया है!\n\n📋 जरूरी:\n• राशन कार्ड ले जाएं\n• आधार कार्ड भी साथ रखें\n\n⏰ दुकान खुलने का समय: सुबह 9 से शाम 5 बजे तक",
                type = Post.TYPE_NEWS,
                likes = 93
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "👩‍🍳 आज पंचायत भवन में महिला स्वयं सहायता समूह (SHG) की बैठक है।\n\n📋 विषय:\n• नई ट्रेनिंग कार्यक्रम\n• लोन की जानकारी\n• अचार/पापड़ बनाने की ट्रेनिंग\n\n⏰ दोपहर 2 बजे | सभी महिलाएं आमंत्रित हैं!",
                type = Post.TYPE_NEWS,
                likes = 67
            ),

            // ── More Awareness ───────────────────────────────────────────
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🦟 मच्छरों से बचाव = डेंगू/मलेरिया से बचाव:\n\n✅ करें:\n• मच्छरदानी लगाकर सोएं\n• कूलर/गमले में पानी बदलते रहें\n• पूरी बाजू के कपड़े पहनें\n\n❌ न करें:\n• घर के आसपास पानी जमा न होने दें\n• टूटे बर्तनों में बारिश का पानी जमा न होने दें",
                type = Post.TYPE_AWARENESS,
                likes = 145
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "📱 मोबाइल का सही इस्तेमाल:\n\n✅ फायदे:\n• ऑनलाइन पढ़ाई\n• सरकारी योजनाओं की जानकारी\n• बैंकिंग\n• खेती की जानकारी\n\n❌ नुकसान:\n• ज्यादा देर तक इस्तेमाल = आंखों को नुकसान\n• फेक न्यूज़ पर भरोसा करना\n• बच्चों को बिना निगरानी देना\n\n💡 संतुलित इस्तेमाल करें!",
                type = Post.TYPE_AWARENESS,
                likes = 98
            ),
            Post(
                userId = "satyasetu_team",
                userName = "SatyaSetu Team",
                description = "🤝 बाल विवाह एक अपराध है!\n\n📜 कानून:\n• लड़की की शादी 18 साल से पहले = अपराध\n• लड़के की शादी 21 साल से पहले = अपराध\n\n⚠️ बाल विवाह से:\n• पढ़ाई रुक जाती है\n• स्वास्थ्य को खतरा\n• गरीबी बढ़ती है\n\n📞 शिकायत: 1098 (चाइल्डलाइन)",
                type = Post.TYPE_AWARENESS,
                likes = 234
            )
        )
    }
}
