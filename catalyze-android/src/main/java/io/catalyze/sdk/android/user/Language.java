package io.catalyze.sdk.android.user;

/**
 * A language on the Catalyze API has two components, the name of the language (language) and the
 * level of fluency (languageMode). Language mode is commonly from the following list
 *
 * ESGN, Expressed signed
 * ESP, Expressed spoken
 * EWR, Expressed written
 * RSGN, Received signed
 * RSP, Received spoken
 * RWR, Received written
 */
public class Language implements Comparable<Language> {

    private String language;
    private String languageMode;

    public Language() { }

    /**
     * @return the name of the language.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Set the name of the language.
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the language mode.
     */
    public String getLanguageMode() {
        return languageMode;
    }

    /**
     * Set the language mode.
     * @param languageMode
     */
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
