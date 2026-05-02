package com.satyasetu.app.data.model;

/**
 * Model for Government Schemes (Sarkari Yojana)
 * Provides curated information about Indian government schemes
 * that users can browse and learn about.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\"\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 62\u00020\u0001:\u00016B\u0091\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0012J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0011H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\u0095\u0001\u00100\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u00c6\u0001J\u0013\u00101\u001a\u00020\u00112\b\u00102\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00103\u001a\u000204H\u00d6\u0001J\t\u00105\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0014R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0014R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u001eR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0014R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0014\u00a8\u00067"}, d2 = {"Lcom/satyasetu/app/data/model/GovernmentScheme;", "", "id", "", "nameHindi", "nameEnglish", "descriptionHindi", "descriptionEnglish", "eligibilityHindi", "eligibilityEnglish", "benefitHindi", "benefitEnglish", "howToApplyHindi", "howToApplyEnglish", "websiteUrl", "category", "isActive", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", "getBenefitEnglish", "()Ljava/lang/String;", "getBenefitHindi", "getCategory", "getDescriptionEnglish", "getDescriptionHindi", "getEligibilityEnglish", "getEligibilityHindi", "getHowToApplyEnglish", "getHowToApplyHindi", "getId", "()Z", "getNameEnglish", "getNameHindi", "getWebsiteUrl", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "Companion", "app_debug"})
public final class GovernmentScheme {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nameHindi = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nameEnglish = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String descriptionHindi = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String descriptionEnglish = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String eligibilityHindi = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String eligibilityEnglish = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String benefitHindi = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String benefitEnglish = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String howToApplyHindi = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String howToApplyEnglish = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String websiteUrl = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String category = null;
    private final boolean isActive = false;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CATEGORY_FARMING = "farming";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CATEGORY_HEALTH = "health";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CATEGORY_EDUCATION = "education";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CATEGORY_HOUSING = "housing";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CATEGORY_FINANCIAL = "financial";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CATEGORY_WOMEN = "women";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CATEGORY_GENERAL = "general";
    @org.jetbrains.annotations.NotNull()
    public static final com.satyasetu.app.data.model.GovernmentScheme.Companion Companion = null;
    
    public GovernmentScheme(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String nameHindi, @org.jetbrains.annotations.NotNull()
    java.lang.String nameEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String descriptionHindi, @org.jetbrains.annotations.NotNull()
    java.lang.String descriptionEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String eligibilityHindi, @org.jetbrains.annotations.NotNull()
    java.lang.String eligibilityEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String benefitHindi, @org.jetbrains.annotations.NotNull()
    java.lang.String benefitEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String howToApplyHindi, @org.jetbrains.annotations.NotNull()
    java.lang.String howToApplyEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String websiteUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String category, boolean isActive) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNameHindi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNameEnglish() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescriptionHindi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescriptionEnglish() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEligibilityHindi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEligibilityEnglish() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBenefitHindi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBenefitEnglish() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHowToApplyHindi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHowToApplyEnglish() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getWebsiteUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCategory() {
        return null;
    }
    
    public final boolean isActive() {
        return false;
    }
    
    public GovernmentScheme() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    public final boolean component14() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.satyasetu.app.data.model.GovernmentScheme copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String nameHindi, @org.jetbrains.annotations.NotNull()
    java.lang.String nameEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String descriptionHindi, @org.jetbrains.annotations.NotNull()
    java.lang.String descriptionEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String eligibilityHindi, @org.jetbrains.annotations.NotNull()
    java.lang.String eligibilityEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String benefitHindi, @org.jetbrains.annotations.NotNull()
    java.lang.String benefitEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String howToApplyHindi, @org.jetbrains.annotations.NotNull()
    java.lang.String howToApplyEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String websiteUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String category, boolean isActive) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004J\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/satyasetu/app/data/model/GovernmentScheme$Companion;", "", "()V", "CATEGORY_EDUCATION", "", "CATEGORY_FARMING", "CATEGORY_FINANCIAL", "CATEGORY_GENERAL", "CATEGORY_HEALTH", "CATEGORY_HOUSING", "CATEGORY_WOMEN", "getCategoryDisplayNameEnglish", "category", "getCategoryDisplayNameHindi", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getCategoryDisplayNameHindi(@org.jetbrains.annotations.NotNull()
        java.lang.String category) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getCategoryDisplayNameEnglish(@org.jetbrains.annotations.NotNull()
        java.lang.String category) {
            return null;
        }
    }
}