package com.example.newsapp.Retrofit;

import com.example.newsapp.Models.NewsModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class NewsGsonDeserializer implements JsonDeserializer {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        ArrayList<NewsModel> newsModels = null;
        try{
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("articles");
            newsModels = new ArrayList<>(jsonArray.size());
            for(int i=0;i<jsonArray.size();i++){
                NewsModel model = context.deserialize(jsonArray.get(i),NewsModel.class);
                newsModels.add(model);
            }


        }catch (JsonParseException ex){

        }
        return newsModels;
    }
}
