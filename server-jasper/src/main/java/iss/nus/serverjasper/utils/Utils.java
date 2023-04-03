package iss.nus.serverjasper.utils;

import iss.nus.serverjasper.models.FormData;
import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Utils {
    
    public static JsonObject toJson(FormData data) {

        return Json.createObjectBuilder()
                    .add("username", data.getUsername())
                    .add("comment", data.getComment())
                    .add("description", data.getDescription())
                    .build();
    }

    
}
