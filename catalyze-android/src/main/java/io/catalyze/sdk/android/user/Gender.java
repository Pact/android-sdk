package io.catalyze.sdk.android.user;

/**
 * Provides a reference as to the gender of a {@link io.catalyze.sdk.sdk.android.Person}.
 */
public enum Gender {

    FEMALE("female"), MALE("male"), UNDIFFERENTIATED("undifferentiated");

    private String mJsonText;

    private Gender(String jsonText) {
        mJsonText = jsonText;
    }

    /**
     * Creates this {@link Gender} from the provided string if the provided string is valid.
     * @param value the string representation of the {@link Gender}
     * @return the {@link Gender} the provided string represents or {@code null} if the string is not valid.
     */
    public static Gender fromString(String value) {
        if (value != null && value.length() > 0) {
            for (Gender g : Gender.values()) {
                if (value.equals(g.toString())) {
                    return g;
                }
            }
        }
        throw new IllegalArgumentException("No enum with json text of (" + value + ") found");
    }

    @Override
    public String toString() {
        return mJsonText;
    }
}
