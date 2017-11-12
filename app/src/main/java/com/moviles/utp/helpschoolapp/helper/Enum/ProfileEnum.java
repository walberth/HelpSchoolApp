package com.moviles.utp.helpschoolapp.helper.Enum;

/**
 * Created by walbe on 11/11/2017.
 */

public enum ProfileEnum {
    REQUESTER_Response("APODERADO", "1"),
    REQUESTER_Pending("APODERADO", "2"),
    ADMINISTRATOR_Response("DIRECTIVO", "3"),
    ADMINISTRATOR_Pending("DIRECTIVO", "4");

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

