package eu.marcocattaneo.stargazerstest.business.helpers;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JSONMapper {

    /**
     * Convert String json to an object of e specific type
     * @param json
     * @param classType
     * @return
     */
    public static <S> S fromJson(@NonNull String json, @NonNull Type classType) {

        if (json == null || classType == null)
            return null;

        Gson gson = new Gson();

        return gson.fromJson(json, classType);
    }

    /**
     * Return json string from object
     * @param object
     * @return
     */
    public static String toJson(@NonNull Object object) {

        if (object == null)
            return null;

        Gson gson = new Gson();

        return gson.toJson(object);
    }

}