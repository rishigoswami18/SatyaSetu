package com.satyasetu.app.data.repository

import com.satyasetu.app.BuildConfig
import com.satyasetu.app.data.model.*
import com.satyasetu.app.data.remote.OpenAIService
import com.satyasetu.app.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ChatRepository — Handles AI chatbot responses with offline fallback.
 *
 * Architecture:
 * 1. First tries Groq API (LLaMA 3.1 via OpenAI-compatible endpoint)
 * 2. If API fails or key missing → uses offline knowledge base (keyword matching)
 * 3. Offline KB uses HashMap-based O(1) lookup with fuzzy keyword matching
 *
 * This ensures the chatbot ALWAYS works — even without internet.
 */
@Singleton
class ChatRepository @Inject constructor(
    private val openAIService: OpenAIService
) {

    /**
     * Get AI response with automatic fallback to offline knowledge base.
     */
    suspend fun getAIResponse(userMessage: String, history: List<ChatMessage>): Result<String> {
        // Try online API first
        val apiResult = tryOnlineResponse(userMessage, history)
        if (apiResult.isSuccess) return apiResult

        // Fallback to offline knowledge base
        val offlineResponse = getOfflineResponse(userMessage)
        if (offlineResponse != null) {
            return Result.success(offlineResponse)
        }

        // Last resort: generic helpful response
        return Result.success(
            "माफ़ कीजिए, अभी इंटरनेट कनेक्शन नहीं है। 🌐\n\n" +
            "कुछ चीज़ें जो आप आज़मा सकते हैं:\n" +
            "• \"बुखार\" टाइप करें - स्वास्थ्य सलाह\n" +
            "• \"OTP फ्रॉड\" - डिजिटल सुरक्षा\n" +
            "• \"किसान योजना\" - सरकारी योजनाएँ\n" +
            "• \"काला जादू\" - अंधविश्वास का सच\n\n" +
            "इंटरनेट आने पर AI सहायक पूरी तरह काम करेगा। 🙏"
        )
    }

    /**
     * Try getting response from Groq API.
     */
    private suspend fun tryOnlineResponse(
        userMessage: String,
        history: List<ChatMessage>
    ): Result<String> {
        return try {
            val apiKey = BuildConfig.OPENAI_API_KEY
            if (apiKey.isBlank()) {
                return Result.failure(
                    IllegalStateException("API key not configured")
                )
            }

            val messages = mutableListOf(
                ApiMessage(
                    role = "system",
                    content = Constants.CHATBOT_SYSTEM_PROMPT.trimIndent()
                )
            )

            // Add recent history (last 6 messages for context window)
            history.takeLast(6).forEach { msg ->
                messages.add(
                    ApiMessage(
                        role = if (msg.isFromUser) "user" else "assistant",
                        content = msg.content
                    )
                )
            }
            messages.add(ApiMessage(role = "user", content = userMessage))

            val request = ChatRequest(
                model = Constants.OPENAI_MODEL,
                messages = messages,
                max_tokens = 500,
                temperature = 0.7
            )

            val response = openAIService.getChatCompletion(
                authorization = "Bearer $apiKey",
                request = request
            )

            val reply = response.choices?.firstOrNull()?.message?.content
                ?: return Result.failure(Exception("Empty response from AI"))

            Result.success(reply)
        } catch (e: Exception) {
            Result.failure(Exception("API Error: ${e.message}"))
        }
    }

    /**
     * Offline Knowledge Base — HashMap-based keyword matching.
     *
     * Algorithm:
     * 1. Tokenize user message
     * 2. Match tokens against keyword sets for each topic
     * 3. Score each topic by number of matching keywords
     * 4. Return the best matching response
     *
     * Time Complexity: O(T * K) where T = tokens, K = total keywords
     * With HashMap: effectively O(T) amortized
     */
    private fun getOfflineResponse(userMessage: String): String? {
        val tokens = userMessage.lowercase().trim()
            .split(Regex("[\\s,।!?:;\"'()]+"))
            .filter { it.isNotBlank() }
            .toSet()

        // Score each topic
        var bestTopic: String? = null
        var bestScore = 0

        OFFLINE_KB.forEach { (topic, entry) ->
            val score = tokens.count { token ->
                entry.keywords.any { keyword -> token.contains(keyword) || keyword.contains(token) }
            }
            if (score > bestScore) {
                bestScore = score
                bestTopic = topic
            }
        }

        // Require at least 1 keyword match
        if (bestScore < 1 || bestTopic == null) return null

        return OFFLINE_KB[bestTopic]?.response
    }

    companion object {

        private data class KBEntry(
            val keywords: Set<String>,
            val response: String
        )

        /**
         * Pre-built offline knowledge base.
         * Covers the most common questions from rural users.
         */
        private val OFFLINE_KB = mapOf(
            "fever" to KBEntry(
                keywords = setOf("बुखार", "fever", "तापमान", "ज्वर", "बदन", "गर्म"),
                response = "🏥 **बुखार में क्या करें?**\n\n" +
                    "✅ सही कदम:\n" +
                    "• खूब पानी और तरल पदार्थ पिएं\n" +
                    "• हल्का भोजन करें (खिचड़ी, दलिया)\n" +
                    "• आराम करें, ठंडे कपड़े से माथा पोंछें\n" +
                    "• पैरासिटामॉल (Paracetamol) ले सकते हैं\n\n" +
                    "⚠️ डॉक्टर के पास जाएं अगर:\n" +
                    "• 3 दिन से ज्यादा बुखार हो\n" +
                    "• तापमान 103°F से ऊपर हो\n" +
                    "• छोटे बच्चे को बुखार हो\n" +
                    "• उल्टी या दस्त भी हो\n\n" +
                    "❌ तांत्रिक/ओझा के पास जाना सही नहीं है!\n\n" +
                    "⚠ यह जानकारी शैक्षिक है। गंभीर स्थिति में डॉक्टर से मिलें।"
            ),

            "otp_fraud" to KBEntry(
                keywords = setOf("otp", "ओटीपी", "फ्रॉड", "fraud", "ठगी", "बैंक", "पिन", "कॉल", "लॉटरी", "kyc"),
                response = "🚨 **OTP / बैंक फ्रॉड से बचें!**\n\n" +
                    "⚠️ याद रखें:\n" +
                    "• कोई भी बैंक फोन पर OTP/PIN नहीं मांगता\n" +
                    "• 'लॉटरी जीती' वाले मेसेज 100% फ्रॉड हैं\n" +
                    "• KYC के लिए कोई फोन पर नहीं कहता\n" +
                    "• अनजान लिंक पर कभी क्लिक न करें\n\n" +
                    "📞 फ्रॉड होने पर:\n" +
                    "• तुरंत **1930** पर कॉल करें\n" +
                    "• बैंक को तुरंत सूचित करें\n" +
                    "• cybercrime.gov.in पर शिकायत करें\n" +
                    "• नजदीकी पुलिस स्टेशन में FIR दर्ज कराएं\n\n" +
                    "💡 सतर्क रहें, सुरक्षित रहें!"
            ),

            "schemes" to KBEntry(
                keywords = setOf("योजना", "scheme", "सरकार", "सरकारी", "किसान", "आयुष्मान", "आवास", "मुद्रा", "उज्ज्वला", "पेंशन", "राशन"),
                response = "💰 **प्रमुख सरकारी योजनाएँ:**\n\n" +
                    "1️⃣ **पीएम किसान** - ₹6,000/साल किसानों को\n" +
                    "   📱 pmkisan.gov.in\n\n" +
                    "2️⃣ **आयुष्मान भारत** - ₹5 लाख मुफ्त इलाज\n" +
                    "   📱 pmjay.gov.in\n\n" +
                    "3️⃣ **पीएम आवास** - ₹1.20 लाख मकान बनाने के लिए\n" +
                    "   📱 pmayg.nic.in\n\n" +
                    "4️⃣ **उज्ज्वला योजना** - मुफ्त गैस कनेक्शन\n\n" +
                    "5️⃣ **मुद्रा योजना** - ₹10 लाख तक बिजनेस लोन\n\n" +
                    "📍 आवेदन कहां:\n" +
                    "• नजदीकी जन सेवा केंद्र (CSC)\n" +
                    "• ग्राम पंचायत कार्यालय\n\n" +
                    "बताइए किस योजना के बारे में विस्तार से जानना है?"
            ),

            "superstition" to KBEntry(
                keywords = setOf("भूत", "प्रेत", "जादू", "काला", "टोना", "टोटका", "तांत्रिक", "ओझा", "झाड़", "फूंक", "शकुन", "अपशकुन", "बिल्ली"),
                response = "🔬 **अंधविश्वास का विज्ञान:**\n\n" +
                    "❌ ये सब गलत है:\n" +
                    "• भूत-प्रेत का अस्तित्व नहीं है\n" +
                    "• काला जादू काम नहीं करता\n" +
                    "• बिल्ली रास्ता काटना = कोई अपशकुन नहीं\n" +
                    "• झाड़-फूंक से बीमारी ठीक नहीं होती\n\n" +
                    "✅ विज्ञान की बात:\n" +
                    "• 'भूत दिखना' = मानसिक तनाव या बीमारी\n" +
                    "• पीपल पर भूत = रात में CO2 गैस का असर\n" +
                    "• तांत्रिक = ज्यादातर ठग होते हैं\n\n" +
                    "⚠️ खतरा:\n" +
                    "• ओझा के चक्कर में इलाज में देरी होती है\n" +
                    "• महिलाओं और बच्चों को सबसे ज्यादा नुकसान\n\n" +
                    "💡 सोच बदलो, विज्ञान अपनाओ!"
            ),

            "snake" to KBEntry(
                keywords = setOf("सांप", "snake", "काटा", "काटने", "जहर", "नाग"),
                response = "🐍 **सांप काटने पर क्या करें?**\n\n" +
                    "✅ सही कदम:\n" +
                    "• शांत रहें, घबराएं नहीं\n" +
                    "• काटी हुई जगह को हिलाएं नहीं\n" +
                    "• तुरंत **108** एम्बुलेंस कॉल करें\n" +
                    "• सरकारी अस्पताल जाएं\n" +
                    "• **एंटी-वेनम इंजेक्शन मुफ्त** मिलता है\n\n" +
                    "❌ गलत काम (कभी न करें):\n" +
                    "• तांत्रिक/ओझा के पास न जाएं\n" +
                    "• चीरा न लगाएं, न चूसें\n" +
                    "• कसकर न बांधें (tourniquet)\n\n" +
                    "⏱️ समय बहुत कीमती है — तुरंत अस्पताल जाएं!"
            ),

            "farming" to KBEntry(
                keywords = setOf("खेती", "किसान", "फसल", "बीज", "खाद", "सिंचाई", "कृषि", "मिट्टी", "पराली", "farming", "crop"),
                response = "🌾 **खेती के काम की बातें:**\n\n" +
                    "📋 मिट्टी की जांच:\n" +
                    "• KVK (कृषि विज्ञान केंद्र) पर मुफ्त\n" +
                    "• सही खाद = कम खर्च, ज्यादा फसल\n\n" +
                    "🚫 पराली न जलाएं:\n" +
                    "• मिट्टी की ताकत कम होती है\n" +
                    "• हैप्पी सीडर मशीन का इस्तेमाल करें\n\n" +
                    "💰 किसान योजनाएं:\n" +
                    "• पीएम किसान: ₹6,000/साल\n" +
                    "• फसल बीमा: pmfby.gov.in\n" +
                    "• किसान हेल्पलाइन: **1800-180-1551** (टोल फ्री)\n\n" +
                    "किसी विशेष विषय पर जानकारी चाहिए?"
            ),

            "water" to KBEntry(
                keywords = setOf("पानी", "water", "शुद्ध", "गंदा", "दस्त", "डायरिया", "उबालना"),
                response = "💧 **साफ पानी पीना जरूरी!**\n\n" +
                    "गंदे पानी से होने वाली बीमारियां:\n" +
                    "• दस्त (डायरिया)\n" +
                    "• टाइफाइड\n" +
                    "• पीलिया\n" +
                    "• हेपेटाइटिस\n\n" +
                    "✅ पानी साफ करने के तरीके:\n" +
                    "1. **उबालें** — सबसे आसान और सस्ता\n" +
                    "2. क्लोरीन की गोली डालें\n" +
                    "3. RO/UV फिल्टर लगाएं\n" +
                    "4. कपड़े से छानकर मटके में रखें\n\n" +
                    "🌧️ बरसात में विशेष ध्यान रखें!"
            ),

            "education" to KBEntry(
                keywords = setOf("स्कूल", "पढ़ाई", "शिक्षा", "छात्रवृत्ति", "scholarship", "परीक्षा", "school", "education"),
                response = "📚 **शिक्षा से जुड़ी जानकारी:**\n\n" +
                    "🎓 छात्रवृत्ति:\n" +
                    "• scholarships.gov.in पर आवेदन करें\n" +
                    "• SC/ST/OBC छात्रों के लिए विशेष छात्रवृत्ति\n" +
                    "• ट्यूशन फीस + किताबें + भत्ता\n\n" +
                    "📱 मुफ्त ऑनलाइन पढ़ाई:\n" +
                    "• DIKSHA App — NCERT की किताबें\n" +
                    "• Khan Academy — गणित/विज्ञान\n" +
                    "• Swayam Portal — कॉलेज कोर्सेज\n\n" +
                    "💡 शिक्षा = सबसे बड़ा हथियार!\n" +
                    "बताइए किस विषय में मदद चाहिए?"
            ),

            "child_marriage" to KBEntry(
                keywords = setOf("बाल", "विवाह", "शादी", "marriage", "child"),
                response = "🚨 **बाल विवाह एक अपराध है!**\n\n" +
                    "📜 कानून:\n" +
                    "• लड़की: 18 साल से पहले शादी = अपराध\n" +
                    "• लड़का: 21 साल से पहले शादी = अपराध\n" +
                    "• दोषी को 2 साल जेल + जुर्माना\n\n" +
                    "⚠️ बाल विवाह से नुकसान:\n" +
                    "• पढ़ाई रुक जाती है\n" +
                    "• कम उम्र में गर्भधारण = खतरनाक\n" +
                    "• गरीबी बढ़ती है\n\n" +
                    "📞 शिकायत कहां करें:\n" +
                    "• चाइल्डलाइन: **1098**\n" +
                    "• नजदीकी पुलिस स्टेशन\n" +
                    "• महिला हेल्पलाइन: **181**"
            ),

            "mosquito" to KBEntry(
                keywords = setOf("मच्छर", "डेंगू", "मलेरिया", "mosquito", "dengue", "malaria"),
                response = "🦟 **मच्छर जनित बीमारियों से बचाव:**\n\n" +
                    "🛡️ बचाव:\n" +
                    "• मच्छरदानी लगाकर सोएं\n" +
                    "• कूलर/गमले में पानी रोज बदलें\n" +
                    "• पूरी बाजू के कपड़े पहनें\n" +
                    "• घर के आसपास पानी जमा न होने दें\n\n" +
                    "⚠️ डेंगू के लक्षण:\n" +
                    "• तेज बुखार + जोड़ों में दर्द\n" +
                    "• शरीर पर लाल दाने\n" +
                    "• प्लेटलेट्स कम होना\n\n" +
                    "📞 लक्षण दिखें तो तुरंत डॉक्टर से मिलें!\n" +
                    "सरकारी अस्पताल में इलाज मुफ्त है।"
            )
        )
    }
}
