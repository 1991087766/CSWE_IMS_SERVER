package com.db_server.info;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NAVY on 2017/6/12.
 */
public class Person_delInfo {
    @SerializedName("information")
    private JsonArray delInformation;

    public JsonArray getDelInformation() {
        return delInformation;
    }
}
