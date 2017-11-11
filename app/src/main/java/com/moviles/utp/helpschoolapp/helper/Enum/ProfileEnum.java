package com.moviles.utp.helpschoolapp.helper.Enum;

/**
 * Created by walbe on 11/11/2017.
 */

public enum ProfileEnum {
    REQUESTER ("APODERADO", "1"),
    ADMINISTRATOR ("DIRECTIVO", "3");

    private final String type, id;

    ProfileEnum(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}

