# SatyaSetu - Setup Guide

## सत्यसेतु - Rural Awareness & Education Platform
**"सत्य की राह, ज्ञान का साथ"**

---

## 📋 Prerequisites

1. **Android Studio** Hedgehog (2023.1.1) or later
2. **JDK 17** (bundled with Android Studio)
3. **Firebase Account** (free tier works)
4. **OpenAI API Key** (for chatbot feature)
5. **Android device/emulator** with API 24+ (Android 7.0+)

---

## 🚀 Step-by-Step Setup

### Step 1: Open in Android Studio

1. Open Android Studio
2. Select **File → Open** and navigate to this project folder (`SatyaSetu/`)
3. Wait for Gradle sync to complete

### Step 2: Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click **"Add Project"** → Name it **"SatyaSetu"**
3. Once created, click **"Add App"** → Select **Android**
4. Enter package name: `com.satyasetu.app`
5. Download `google-services.json`
6. Place it in: `app/google-services.json`

#### Enable Firebase Services:
- **Authentication** → Enable **Email/Password** sign-in method
- **Realtime Database** → Create database → Start in **test mode** (update rules later)
- **Storage** → Create storage bucket
- **Cloud Messaging** → Enabled by default

#### Deploy Security Rules:
- Copy rules from `firebase-database-rules.json` to Firebase Console → Realtime Database → Rules
- Copy rules from `firebase-storage-rules.json` to Firebase Console → Storage → Rules

### Step 3: OpenAI API Key

1. Go to [OpenAI Platform](https://platform.openai.com/api-keys)
2. Create a new API key
3. Open `app/build.gradle.kts`
4. Replace `"your-openai-api-key-here"` with your actual key:
   ```kotlin
   buildConfigField("String", "OPENAI_API_KEY", "\"sk-your-key-here\"")
   ```

> **Security Note:** For production, use `local.properties` or a secrets management tool instead of hardcoding the key.

### Step 4: Build & Run

1. Connect an Android device or start an emulator
2. Click **Run ▶** (or Shift+F10)
3. The app will install and launch

### Step 5: Seed Sample Data

On first launch after logging in:
1. **Video Feed** → Tap "सैंपल डेटा लोड करें" to load sample videos
2. **सच या गलत** → Tap "सैंपल डेटा लोड करें" to load myth/fact cards
3. **सीखें** → Tap "सैंपल डेटा लोड करें" to load learning content
4. **विकास ट्रैकर** → Tap "सैंपल डेटा लोड करें" to load development data

---

## 📱 App Features

| Feature | Description |
|---------|-------------|
| 🎬 Video Feed | Instagram Reels-like vertical video feed with categories |
| 🤔 Sach Ya Galat | Myth vs Fact cards with expandable explanations |
| 📚 Learning | Duolingo-inspired lessons with interactive quizzes |
| 📊 Netaji Tracker | Neutral development status with YES/NO indicators |
| 🤖 AI Chatbot | Hindi-language AI assistant powered by OpenAI |
| 🛡️ Report System | Safe reporting with admin approval workflow |
| 🔐 Auth | Email authentication with village profile |

---

## 🏗️ Architecture

```
MVVM Clean Architecture
├── UI Layer (Jetpack Compose)
│   ├── Screens (Composables)
│   └── ViewModels (StateFlow)
├── Data Layer
│   ├── Repositories (Single Source of Truth)
│   ├── Models (Data Classes)
│   └── Remote (Retrofit/OpenAI)
└── DI Layer (Hilt)
```

---

## 🗄️ Firebase Database Structure

```
satyasetu-db/
├── users/
│   └── {userId}/
│       ├── name, email, phone
│       ├── village, district, state
│       ├── role (user/admin)
│       ├── lessonsCompleted[]
│       └── quizScores{}
├── videos/
│   └── {videoId}/
│       ├── title, description, category
│       ├── url, thumbnailUrl
│       └── likes, views, shares
├── pending_reports/
│   └── {reportId}/
│       ├── title, description, category
│       ├── location, imageUrls
│       ├── status (pending/approved/rejected)
│       └── isAnonymous
├── approved_reports/
│   └── {reportId}/ (same structure)
├── myths/
│   └── {mythId}/
│       ├── myth, fact, explanation
│       ├── category, videoUrl
│       └── likes
├── lessons/
│   └── {lessonId}/
│       ├── title, description, content
│       ├── category, order
│       └── quiz[]
└── development/
    └── {itemId}/
        ├── area, district, state
        ├── category, title
        ├── status (true/false)
        └── source
```

---

## 🔐 Security Notes

1. **Reports are NEVER auto-published** — they go to `pending_reports` and require admin approval
2. **Anonymous reporting** available to protect user identity
3. **Disclaimer** displayed: "यह प्लेटफॉर्म सिर्फ जानकारी और शिक्षा के लिए है"
4. **Firebase rules** enforce role-based access control
5. **OpenAI prompt** prevents harmful medical advice

---

## 📁 Complete File Structure

```
SatyaSetu/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/satyasetu/app/
│       │   ├── SatyaSetuApp.kt
│       │   ├── di/AppModule.kt
│       │   ├── data/
│       │   │   ├── model/ (User, Video, Report, Myth, Lesson, DevelopmentItem, ChatMessage)
│       │   │   ├── repository/ (Auth, Video, Report, Myth, Learning, Tracker, Chat)
│       │   │   └── remote/OpenAIService.kt
│       │   ├── ui/
│       │   │   ├── theme/ (Color, Type, Theme)
│       │   │   ├── navigation/ (Screen, NavGraph+BottomNavBar)
│       │   │   ├── auth/ (AuthViewModel, LoginScreen, RegisterScreen)
│       │   │   ├── feed/ (FeedViewModel, VideoFeedScreen)
│       │   │   ├── myths/ (MythViewModel, MythFactScreen)
│       │   │   ├── report/ (ReportViewModel, ReportScreen)
│       │   │   ├── learning/ (LearningViewModel, LearningScreen, QuizScreen)
│       │   │   ├── tracker/ (TrackerViewModel, TrackerScreen)
│       │   │   ├── chatbot/ (ChatViewModel, ChatScreen)
│       │   │   └── main/MainActivity.kt
│       │   └── util/ (Constants, Extensions)
│       └── res/
│           └── values/ (strings.xml, colors.xml, themes.xml)
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── firebase-database-rules.json
└── firebase-storage-rules.json
```

---

## 🚀 Production Checklist

- [ ] Replace test OpenAI API key with production key
- [ ] Update Firebase security rules from test mode
- [ ] Add `google-services.json` for release
- [ ] Generate signed APK/AAB for Play Store
- [ ] Add app icons (launcher icons)
- [ ] Test on low-end devices (target rural audience)
- [ ] Add offline caching for lessons and myths
- [ ] Set up Firebase Cloud Functions for report notifications
- [ ] Add voice input for chatbot (Speech-to-Text API)
- [ ] Implement content moderation pipeline
