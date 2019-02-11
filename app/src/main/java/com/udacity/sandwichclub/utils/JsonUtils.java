package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sand = null;
        
        try {
            JSONObject sandObj, nameObj;
            JSONArray alsoArray, ingreArray;
            String mainName, placeOfOrigin, description, image;
            List<String> alsoKnownAs = new ArrayList<>(), ingredients = new ArrayList<>();


            sandObj = new JSONObject(json);
            nameObj = sandObj.getJSONObject("name");

            mainName = nameObj.optString("mainName");
            placeOfOrigin = sandObj.optString("placeOfOrigin");
            description = sandObj.optString("description");
            image = sandObj.optString("image");

            alsoArray = nameObj.getJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoArray.length(); i++) {
                try {
                    alsoKnownAs.add(alsoArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ingreArray = sandObj.getJSONArray("ingredients");
            for (int i = 0; i < ingreArray.length(); i++) {
                try {
                    ingredients.add(ingreArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            sand = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return sand;
    }

}