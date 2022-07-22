package me.strafe.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class JsonUtil {

    public static Gson gson = new Gson();
    public static Gson prettyGson = new GsonBuilder().create();
    public static JsonParser jsonParser = new JsonParser();
}
