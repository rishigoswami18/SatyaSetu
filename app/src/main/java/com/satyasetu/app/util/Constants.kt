package com.satyasetu.app.util

/**
 * Application-wide constants
 */
object Constants {

    // Firebase Database Paths
    const val DB_USERS = "users"
    const val DB_VIDEOS = "videos"
    const val DB_REPORTS = "pending_reports"
    const val DB_APPROVED_REPORTS = "approved_reports"
    const val DB_MYTHS = "myths"
    const val DB_LESSONS = "lessons"
    const val DB_DEVELOPMENT = "development"
    const val DB_CHAT_HISTORY = "chat_history"
    const val DB_VILLAGES = "villages"
    const val DB_POSTS = "posts"

    // Firebase Storage Paths
    const val STORAGE_VIDEOS = "videos"
    const val STORAGE_REPORT_IMAGES = "report_images"
    const val STORAGE_REPORT_VIDEOS = "report_videos"
    const val STORAGE_PROFILE_IMAGES = "profile_images"
    const val STORAGE_POST_MEDIA = "post_media"

    // Groq (OpenAI Compatible)
    const val OPENAI_BASE_URL = "https://api.groq.com/openai/v1/"
    const val OPENAI_MODEL = "llama-3.1-8b-instant"

    // System prompt for Hindi AI chatbot
    const val CHATBOT_SYSTEM_PROMPT = """
तुम "सत्यसेतु सहायक" हो — एक दोस्ताना, शैक्षिक AI सहायक जो ग्रामीण भारतीय उपयोगकर्ताओं की मदद करता है।

नियम:
1. हमेशा सरल हिंदी में जवाब दो जो गाँव के लोग आसानी से समझ सकें
2. वैज्ञानिक और तथ्यात्मक जानकारी दो
3. अंधविश्वास का विनम्रता से खंडन करो
4. कभी भी हानिकारक चिकित्सा सलाह मत दो - हमेशा डॉक्टर से मिलने की सलाह दो
5. सरकारी योजनाओं के बारे में सही जानकारी दो
6. राजनीतिक पक्षपात से बचो
7. जवाब छोटे और स्पष्ट रखो (200 शब्दों से कम)
8. जहाँ जरूरी हो, उदाहरण दो
9. "काला जादू", "भूत-प्रेत" जैसी बातों का वैज्ञानिक स्पष्टीकरण दो
10. हमेशा शिक्षाप्रद और सम्मानजनक लहजे में बात करो

अस्वीकरण: "यह जानकारी शैक्षिक उद्देश्य से है। गंभीर समस्याओं के लिए विशेषज्ञ से संपर्क करें।"
"""

    // Disclaimer text
    const val DISCLAIMER_HINDI = "यह प्लेटफॉर्म सिर्फ जानकारी और शिक्षा के लिए है। " +
            "किसी भी गंभीर स्वास्थ्य या कानूनी समस्या के लिए कृपया विशेषज्ञ से संपर्क करें।"

    const val DISCLAIMER_ENGLISH = "This platform is only for information and education. " +
            "For any serious health or legal issue, please contact a specialist."

    // Pagination
    const val PAGE_SIZE = 20

    // Video
    const val MAX_VIDEO_DURATION_MS = 120_000L // 2 minutes
}
