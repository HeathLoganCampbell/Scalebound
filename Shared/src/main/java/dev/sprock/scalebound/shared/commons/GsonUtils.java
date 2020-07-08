package dev.sprock.scalebound.shared.commons;

import com.google.gson.Gson;

public class GsonUtils
{
    private static final Gson GSON_PRETTY = new Gson().newBuilder().setPrettyPrinting().create();
    private static final Gson GSON = new Gson().newBuilder().create();

    public static Gson getGsonPretty()
    {
        return GSON_PRETTY;
    }
    public static Gson getGson()
    {
        return GSON;
    }
}
