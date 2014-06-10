package io.catalyze.sdk.android.user;

public class Language implements Comparable<Language> {

    private String language;
    private String languageMode;

    public Language() { }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageMode() {
        return languageMode;
    }

    public void setLanguageMode(String languageMode) {
        this.languageMode = languageMode;
    }

    @Override
    public int compareTo(Language other) {
        String fullString = getLanguage() + getLanguageMode();
        String otherFullString = other.getLanguage() + other.getLanguageMode();
        return fullString.compareTo(otherFullString);
    }

}
