package com.satyasetu.app.data.repository

import com.satyasetu.app.data.model.GovernmentScheme
import com.satyasetu.app.data.model.EducationalContent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for Government Schemes and Educational Content.
 * Provides curated, ready-made data from data.gov.in, MyGov, and PMGSY.
 *
 * This is the "Support Data" layer — pre-loaded information that makes
 * the app useful from day 1 without user-generated content.
 */
@Singleton
class SchemeRepository @Inject constructor() {

    /**
     * Get all government schemes.
     * Uses Trie-based search when query is provided.
     */
    fun getSchemes(query: String? = null, category: String? = null): List<GovernmentScheme> {
        var schemes = GOVERNMENT_SCHEMES

        if (!category.isNullOrBlank()) {
            schemes = schemes.filter { it.category == category }
        }

        if (!query.isNullOrBlank()) {
            val queryLower = query.lowercase()
            schemes = schemes.filter { scheme ->
                scheme.nameHindi.lowercase().contains(queryLower) ||
                scheme.nameEnglish.lowercase().contains(queryLower) ||
                scheme.descriptionHindi.lowercase().contains(queryLower) ||
                scheme.descriptionEnglish.lowercase().contains(queryLower)
            }
        }

        return schemes
    }

    /**
     * Get a specific scheme by ID.
     */
    fun getSchemeById(id: String): GovernmentScheme? {
        return GOVERNMENT_SCHEMES.find { it.id == id }
    }

    /**
     * Get all educational content.
     */
    fun getEducationalContent(category: String? = null): List<EducationalContent> {
        return if (category != null) {
            EDUCATIONAL_CONTENT.filter { it.category == category }
        } else {
            EDUCATIONAL_CONTENT
        }
    }

    /**
     * Get scheme categories with counts for the UI.
     */
    fun getSchemeCategories(): List<Pair<String, Int>> {
        return GOVERNMENT_SCHEMES.groupBy { it.category }
            .map { (category, schemes) -> Pair(category, schemes.size) }
            .sortedByDescending { it.second }
    }

    companion object {

        // ── Pre-loaded Government Schemes ────────────────────────────────

        val GOVERNMENT_SCHEMES = listOf(
            GovernmentScheme(
                id = "pmkisan",
                nameHindi = "पीएम किसान सम्मान निधि",
                nameEnglish = "PM-KISAN",
                descriptionHindi = "किसानों को सालाना ₹6,000 तीन किस्तों में सीधे बैंक खाते में मिलते हैं।",
                descriptionEnglish = "Farmers receive ₹6,000 annually in three installments directly to bank account.",
                eligibilityHindi = "सभी छोटे और सीमांत किसान (2 हेक्टेयर तक जमीन)।",
                eligibilityEnglish = "All small and marginal farmers (up to 2 hectares of land).",
                benefitHindi = "₹6,000 प्रति वर्ष, ₹2,000 हर 4 महीने।",
                benefitEnglish = "₹6,000 per year, ₹2,000 every 4 months.",
                howToApplyHindi = "नजदीकी CSC केंद्र या pmkisan.gov.in पर ऑनलाइन आवेदन करें। आधार, बैंक खाता और खतौनी जरूरी है।",
                howToApplyEnglish = "Apply online at pmkisan.gov.in or nearest CSC. Aadhaar, bank account and land records required.",
                websiteUrl = "https://pmkisan.gov.in",
                category = GovernmentScheme.CATEGORY_FARMING
            ),
            GovernmentScheme(
                id = "ayushman",
                nameHindi = "आयुष्मान भारत योजना",
                nameEnglish = "Ayushman Bharat - PMJAY",
                descriptionHindi = "गरीब परिवारों को ₹5 लाख तक का मुफ्त इलाज सरकारी और निजी अस्पतालों में।",
                descriptionEnglish = "₹5 lakh free treatment for poor families at government and private hospitals.",
                eligibilityHindi = "BPL परिवार, SECC-2011 डेटाबेस में शामिल परिवार।",
                eligibilityEnglish = "BPL families, families in SECC-2011 database.",
                benefitHindi = "₹5 लाख तक मुफ्त इलाज प्रति परिवार प्रति वर्ष।",
                benefitEnglish = "₹5 lakh free treatment per family per year.",
                howToApplyHindi = "जन सेवा केंद्र (CSC) जाकर पात्रता जांचें। आधार और राशन कार्ड ले जाएं। pmjay.gov.in पर ऑनलाइन भी जांच सकते हैं।",
                howToApplyEnglish = "Visit CSC to check eligibility. Carry Aadhaar and ration card. Check online at pmjay.gov.in.",
                websiteUrl = "https://pmjay.gov.in",
                category = GovernmentScheme.CATEGORY_HEALTH
            ),
            GovernmentScheme(
                id = "pmay",
                nameHindi = "प्रधानमंत्री आवास योजना (ग्रामीण)",
                nameEnglish = "PM Awas Yojana (Rural)",
                descriptionHindi = "बेघर परिवारों को पक्का मकान बनाने के लिए ₹1.20 लाख से ₹1.30 लाख की सहायता।",
                descriptionEnglish = "₹1.20-1.30 lakh assistance for homeless families to build a pucca house.",
                eligibilityHindi = "बेघर परिवार, कच्चे मकान वाले, SC/ST, अल्पसंख्यक।",
                eligibilityEnglish = "Homeless families, families with kutcha houses, SC/ST, minorities.",
                benefitHindi = "मैदानी इलाकों में ₹1.20 लाख और पहाड़ी इलाकों में ₹1.30 लाख।",
                benefitEnglish = "₹1.20 lakh for plains, ₹1.30 lakh for hilly areas.",
                howToApplyHindi = "ग्राम पंचायत में आवेदन करें। SECC-2011 सूची में नाम होना जरूरी।",
                howToApplyEnglish = "Apply at Gram Panchayat. Name must be in SECC-2011 list.",
                websiteUrl = "https://pmayg.nic.in",
                category = GovernmentScheme.CATEGORY_HOUSING
            ),
            GovernmentScheme(
                id = "ujjwala",
                nameHindi = "प्रधानमंत्री उज्ज्वला योजना",
                nameEnglish = "PM Ujjwala Yojana",
                descriptionHindi = "गरीब महिलाओं को मुफ्त LPG गैस कनेक्शन।",
                descriptionEnglish = "Free LPG gas connection for women from poor households.",
                eligibilityHindi = "BPL परिवारों की 18 वर्ष से अधिक उम्र की महिलाएं।",
                eligibilityEnglish = "Women above 18 years from BPL families.",
                benefitHindi = "मुफ्त गैस कनेक्शन, पहला सिलेंडर और चूल्हा मुफ्त।",
                benefitEnglish = "Free gas connection, first cylinder and stove free.",
                howToApplyHindi = "नजदीकी LPG वितरक या CSC केंद्र पर जाएं। BPL कार्ड, आधार और बैंक खाता जरूरी।",
                howToApplyEnglish = "Visit nearest LPG distributor or CSC. BPL card, Aadhaar and bank account required.",
                websiteUrl = "https://www.pmuy.gov.in",
                category = GovernmentScheme.CATEGORY_WOMEN
            ),
            GovernmentScheme(
                id = "mudra",
                nameHindi = "प्रधानमंत्री मुद्रा योजना",
                nameEnglish = "PM MUDRA Yojana",
                descriptionHindi = "छोटे व्यापारियों को बिना गारंटी ₹10 लाख तक का कर्ज।",
                descriptionEnglish = "Collateral-free loans up to ₹10 lakh for small entrepreneurs.",
                eligibilityHindi = "कोई भी भारतीय नागरिक जो छोटा व्यापार शुरू करना चाहता है।",
                eligibilityEnglish = "Any Indian citizen who wants to start a small business.",
                benefitHindi = "शिशु: ₹50,000 | किशोर: ₹5 लाख | तरुण: ₹10 लाख तक बिना गारंटी।",
                benefitEnglish = "Shishu: ₹50K | Kishor: ₹5L | Tarun: ₹10L collateral-free.",
                howToApplyHindi = "किसी भी बैंक शाखा में जाकर मुद्रा लोन के लिए आवेदन करें। बिजनेस प्लान, आधार और पैन कार्ड ले जाएं।",
                howToApplyEnglish = "Apply at any bank branch. Carry business plan, Aadhaar and PAN card.",
                websiteUrl = "https://www.mudra.org.in",
                category = GovernmentScheme.CATEGORY_FINANCIAL
            ),
            GovernmentScheme(
                id = "scholarship",
                nameHindi = "राष्ट्रीय छात्रवृत्ति पोर्टल",
                nameEnglish = "National Scholarship Portal",
                descriptionHindi = "SC/ST/OBC/EBC छात्रों के लिए केंद्र और राज्य सरकार की छात्रवृत्तियां।",
                descriptionEnglish = "Central and state government scholarships for SC/ST/OBC/EBC students.",
                eligibilityHindi = "स्कूल और कॉलेज के छात्र जिनकी पारिवारिक आय ₹2.5 लाख से कम।",
                eligibilityEnglish = "School and college students with family income less than ₹2.5 lakh.",
                benefitHindi = "ट्यूशन फीस, मेंटेनेंस भत्ता और किताबों के लिए अनुदान।",
                benefitEnglish = "Tuition fee, maintenance allowance and book grants.",
                howToApplyHindi = "scholarships.gov.in पर ऑनलाइन आवेदन करें। आय प्रमाण पत्र, जाति प्रमाण पत्र और मार्कशीट जरूरी।",
                howToApplyEnglish = "Apply online at scholarships.gov.in. Income certificate, caste certificate and marksheet required.",
                websiteUrl = "https://scholarships.gov.in",
                category = GovernmentScheme.CATEGORY_EDUCATION
            ),
            GovernmentScheme(
                id = "jandhan",
                nameHindi = "जन धन योजना",
                nameEnglish = "Jan Dhan Yojana",
                descriptionHindi = "हर परिवार को जीरो बैलेंस बैंक खाता, RuPay डेबिट कार्ड और ₹2 लाख का बीमा।",
                descriptionEnglish = "Zero balance bank account, RuPay debit card and ₹2 lakh insurance for every family.",
                eligibilityHindi = "कोई भी भारतीय नागरिक जिसका बैंक खाता नहीं है।",
                eligibilityEnglish = "Any Indian citizen without a bank account.",
                benefitHindi = "जीरो बैलेंस खाता, RuPay कार्ड, ₹2 लाख दुर्घटना बीमा, ₹10,000 ओवरड्राफ्ट।",
                benefitEnglish = "Zero balance account, RuPay card, ₹2L accident insurance, ₹10K overdraft.",
                howToApplyHindi = "किसी भी बैंक शाखा में जाएं। आधार कार्ड या अन्य पहचान पत्र ले जाएं।",
                howToApplyEnglish = "Visit any bank branch. Carry Aadhaar card or other ID proof.",
                websiteUrl = "https://pmjdy.gov.in",
                category = GovernmentScheme.CATEGORY_FINANCIAL
            ),
            GovernmentScheme(
                id = "sukanya",
                nameHindi = "सुकन्या समृद्धि योजना",
                nameEnglish = "Sukanya Samriddhi Yojana",
                descriptionHindi = "बेटियों के लिए बचत योजना, 8.2% ब्याज दर, टैक्स छूट।",
                descriptionEnglish = "Savings scheme for daughters, 8.2% interest rate, tax exemption.",
                eligibilityHindi = "10 साल से कम उम्र की बेटी के माता-पिता/अभिभावक।",
                eligibilityEnglish = "Parents/guardians of a girl child under 10 years.",
                benefitHindi = "8.2% ब्याज दर, ₹250 से शुरू, 21 साल में परिपक्वता, 80C के तहत टैक्स छूट।",
                benefitEnglish = "8.2% interest, starts from ₹250, 21 year maturity, 80C tax exemption.",
                howToApplyHindi = "बैंक या पोस्ट ऑफिस में जाएं। बेटी का जन्म प्रमाण पत्र, आधार और फोटो ले जाएं।",
                howToApplyEnglish = "Visit bank or post office. Carry daughter's birth certificate, Aadhaar and photo.",
                websiteUrl = "https://www.india.gov.in/sukanya-samriddhi-yojna",
                category = GovernmentScheme.CATEGORY_WOMEN
            )
        )

        // ── Pre-loaded Educational Content ──────────────────────────────

        val EDUCATIONAL_CONTENT = listOf(
            EducationalContent(
                id = "edu_fever",
                titleHindi = "बुखार क्यों होता है?",
                titleEnglish = "Why does fever happen?",
                contentHindi = "बुखार शरीर की एक प्राकृतिक रक्षा प्रणाली है। जब कोई कीटाणु (वायरस या बैक्टीरिया) शरीर में घुसता है, तो शरीर तापमान बढ़ाकर उन्हें मारने की कोशिश करता है।\n\n🔬 क्या करें:\n• पानी खूब पिएं\n• हल्का भोजन करें\n• आराम करें\n• पैरासिटामॉल ले सकते हैं\n\n⚠️ डॉक्टर के पास कब जाएं:\n• 3 दिन से ज्यादा बुखार हो\n• तापमान 103°F से ऊपर हो\n• बच्चे को बुखार हो",
                contentEnglish = "Fever is the body's natural defense mechanism. When germs (virus or bacteria) enter the body, it raises temperature to kill them.\n\n🔬 What to do:\n• Drink plenty of water\n• Eat light food\n• Rest well\n• Can take Paracetamol\n\n⚠️ When to see a doctor:\n• Fever lasting more than 3 days\n• Temperature above 103°F\n• If a child has fever",
                category = "health"
            ),
            EducationalContent(
                id = "edu_otp_fraud",
                titleHindi = "OTP फ्रॉड से कैसे बचें?",
                titleEnglish = "How to avoid OTP fraud?",
                contentHindi = "OTP (One Time Password) आपके बैंक खाते की चाबी है। कोई भी बैंक, कंपनी या सरकारी अधिकारी कभी फोन पर OTP नहीं मांगता।\n\n🛡️ याद रखें:\n• OTP किसी को न बताएं\n• अनजान लिंक पर क्लिक न करें\n• 'लॉटरी जीती' वाले मेसेज फ्रॉड हैं\n• बैंक कभी फोन पर पिन/CVV नहीं मांगता\n\n📞 फ्रॉड होने पर:\n• तुरंत 1930 पर कॉल करें\n• नजदीकी पुलिस स्टेशन जाएं\n• cybercrime.gov.in पर शिकायत करें",
                contentEnglish = "OTP (One Time Password) is the key to your bank account. No bank, company or government official ever asks for OTP on phone.\n\n🛡️ Remember:\n• Never share OTP with anyone\n• Don't click unknown links\n• 'Lottery won' messages are fraud\n• Banks never ask for PIN/CVV on phone\n\n📞 If fraud happens:\n• Call 1930 immediately\n• Visit nearest police station\n• Complain at cybercrime.gov.in",
                category = "digital_safety"
            ),
            EducationalContent(
                id = "edu_superstition",
                titleHindi = "काला जादू और भूत-प्रेत का सच",
                titleEnglish = "The truth about black magic and ghosts",
                contentHindi = "विज्ञान के अनुसार भूत-प्रेत और काला जादू का कोई अस्तित्व नहीं है।\n\n🔬 असलियत:\n• 'भूत दिखना' = मानसिक तनाव या बीमारी हो सकती है\n• 'टोना-टोटका' = कोई वैज्ञानिक प्रमाण नहीं\n• 'झाड़-फूंक' से बीमारी ठीक होने का कोई प्रमाण नहीं\n\n⚠️ खतरा:\n• तांत्रिक के चक्कर में पड़ने से इलाज में देरी होती है\n• कई बार तांत्रिक लोगों से पैसे ठगते हैं\n• बच्चों और महिलाओं को सबसे ज्यादा नुकसान होता है\n\n✅ सही कदम:\n• बीमार होने पर डॉक्टर के पास जाएं\n• मानसिक परेशानी में काउंसलर से मिलें",
                contentEnglish = "According to science, ghosts and black magic don't exist.\n\n🔬 Reality:\n• 'Seeing ghosts' = Could be mental stress or illness\n• 'Witchcraft' = No scientific evidence\n• No evidence of 'exorcism' curing diseases\n\n⚠️ Danger:\n• Going to quacks delays actual treatment\n• Many quacks cheat people of money\n• Children and women suffer the most\n\n✅ Right steps:\n• See a doctor when sick\n• Meet a counselor for mental health issues",
                category = "superstition"
            ),
            EducationalContent(
                id = "edu_farming_soil",
                titleHindi = "मिट्टी की जांच क्यों जरूरी है?",
                titleEnglish = "Why is soil testing important?",
                contentHindi = "अच्छी फसल के लिए मिट्टी की जांच बहुत जरूरी है।\n\n🌾 फायदे:\n• सही खाद की मात्रा पता चलती है\n• पैसे बचते हैं (जरूरत से ज्यादा खाद नहीं डालनी पड़ती)\n• फसल की पैदावार बढ़ती है\n• मिट्टी की सेहत बनी रहती है\n\n📋 कैसे कराएं:\n• नजदीकी कृषि विज्ञान केंद्र (KVK) जाएं\n• मिट्टी का सैंपल ले जाएं\n• जांच मुफ्त या मामूली शुल्क में होती है\n• रिपोर्ट में खाद की सिफारिश मिलती है",
                contentEnglish = "Soil testing is essential for good crop yield.\n\n🌾 Benefits:\n• Know exact fertilizer requirements\n• Save money (no excess fertilizer)\n• Increase crop yield\n• Maintain soil health\n\n📋 How to do it:\n• Visit nearest Krishi Vigyan Kendra (KVK)\n• Take a soil sample\n• Testing is free or at nominal cost\n• Report provides fertilizer recommendations",
                category = "farming"
            ),
            EducationalContent(
                id = "edu_clean_water",
                titleHindi = "साफ पानी पीना क्यों जरूरी?",
                titleEnglish = "Why is clean drinking water important?",
                contentHindi = "गंदा पानी पीने से हर साल लाखों लोग बीमार होते हैं।\n\n💧 गंदे पानी से होने वाली बीमारियां:\n• दस्त (डायरिया)\n• टाइफाइड\n• हेपेटाइटिस\n• पीलिया\n• पेट में कीड़े\n\n✅ पानी साफ करने के तरीके:\n• उबालकर पीएं (सबसे आसान)\n• क्लोरीन की गोली डालें\n• RO/UV फिल्टर लगाएं\n• मटके में कपड़े से छानें\n\n⚠️ कभी न करें:\n• खुले नल या तालाब का पानी सीधे न पीएं\n• बरसात में विशेष ध्यान रखें",
                contentEnglish = "Millions fall sick every year from contaminated water.\n\n💧 Waterborne diseases:\n• Diarrhea\n• Typhoid\n• Hepatitis\n• Jaundice\n• Stomach worms\n\n✅ Water purification methods:\n• Boil and drink (easiest)\n• Add chlorine tablets\n• Install RO/UV filter\n• Filter through cloth in earthen pot\n\n⚠️ Never do:\n• Don't drink tap or pond water directly\n• Take special care during rainy season",
                category = "health"
            ),
            EducationalContent(
                id = "edu_snake_bite",
                titleHindi = "सांप काटने पर क्या करें?",
                titleEnglish = "What to do if bitten by a snake?",
                contentHindi = "भारत में हर साल लगभग 50,000 लोग सांप के काटने से मरते हैं — ज्यादातर इलाज न मिलने से।\n\n✅ सही कदम:\n• घबराएं नहीं, शांत रहें\n• काटी हुई जगह को हिलाएं नहीं\n• तुरंत नजदीकी सरकारी अस्पताल जाएं\n• एंटी-वेनम इंजेक्शन मुफ्त मिलता है\n\n❌ गलत काम:\n• तांत्रिक/ओझा के पास न जाएं\n• चीरा न लगाएं, न चूसें\n• टूर्निकेट (कसकर बांधना) न करें\n• जहर चूसना = बिल्कुल गलत\n\n📞 इमरजेंसी: 108 एम्बुलेंस कॉल करें",
                contentEnglish = "In India, about 50,000 die from snakebites annually — mostly due to lack of treatment.\n\n✅ Right steps:\n• Don't panic, stay calm\n• Don't move the bitten limb\n• Go to nearest government hospital immediately\n• Anti-venom injection is free\n\n❌ Wrong actions:\n• Don't go to quacks\n• Don't cut or suck\n• Don't apply tourniquet\n• Sucking venom = completely wrong\n\n📞 Emergency: Call 108 ambulance",
                category = "health"
            )
        )
    }
}
