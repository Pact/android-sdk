package io.catalyze.sdk.external;

import com.google.common.base.Strings;

/**
 * Created by marius on 6/18/13.
 */
public enum Gender {

    FEMALE("female"), MALE("male"), UNKNOWN("unknown");

    private String mJsonText;

    private Gender(String jsonText) {
        mJsonText = jsonText;
    }

    @Override
    public String toString() {
        return mJsonText;
    }

    public static Gender fromString(String value) {
        if (!Strings.isNullOrEmpty(value)) {
            for (Gender g : Gender.values()) {
                if (value.equals(g.toString())) {
                    return g;
                }
            }
        }
        throw new IllegalArgumentException("No enum with json text of (" + value + ") found");
    }
}
