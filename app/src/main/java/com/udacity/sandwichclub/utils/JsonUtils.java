package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/*
{
  "name": {
    "mainName": "Bosna",
    "alsoKnownAs": [
      "Bosner"
    ]
  },
  "placeOfOrigin": "Austria",
  "description": "Bosna is a spicy Austrian fast food dish, said to have originated in either Salzburg or Linz. It is now popular all over western Austria and southern Bavaria.",
  "image": "https://upload.wikimedia.org/wikipedia/commons/c/ca/Bosna_mit_2_Bratw%C3%BCrsten.jpg",
  "ingredients": [
    "White bread",
    "Bratwurst",
    "Onions",
    "Tomato ketchup",
    "Mustard",
    "Curry powder"
  ]
}*/

public class JsonUtils {
    private static final String JSON_NAME_KEY = "name";
    private static final String JSON_MAINNAME_KEY = "mainName";
    private static final String JSON_AKA_KEY = "alsoKnownAs";
    private static final String JSON_ORIGIN_KEY = "placeOfOrigin";
    private static final String JSON_DESC_KEY = "description";
    private static final String JSON_IMG_KEY = "image";
    private static final String JSON_INGREDIENTS_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        if (json.isEmpty()) {
            return null;
        }


        Sandwich selectedSandwich = new Sandwich();

        JSONObject jsonSandwich = new JSONObject(json);

        JSONObject jsonName = jsonSandwich.getJSONObject(JSON_NAME_KEY);
        String sandwichName = jsonName.getString(JSON_MAINNAME_KEY);
        selectedSandwich.setMainName(sandwichName);

        JSONArray jsonAKAs = jsonName.getJSONArray(JSON_AKA_KEY);
        ArrayList<String> sandwichAKAs = new ArrayList<>();
        for (int i = 0; i < jsonAKAs.length(); i++) {
            String currentAKA = jsonAKAs.getString(i);
            sandwichAKAs.add(currentAKA);
        }
        selectedSandwich.setAlsoKnownAs(sandwichAKAs);


        String sandwichOrigin = jsonSandwich.getString(JSON_ORIGIN_KEY);
        selectedSandwich.setPlaceOfOrigin(sandwichOrigin);

        String sandwichDescription = jsonSandwich.getString(JSON_DESC_KEY);
        selectedSandwich.setDescription(sandwichDescription);

        String sandwichImage = jsonSandwich.getString(JSON_IMG_KEY);
        selectedSandwich.setImage(sandwichImage);


        JSONArray jsonIngredients = jsonSandwich.getJSONArray(JSON_INGREDIENTS_KEY);
        ArrayList<String> sandwichIngredients = new ArrayList<>();
        for (int i = 0; i < jsonIngredients.length(); i++) {
            String currentIngredient = jsonIngredients.getString(i);
            sandwichIngredients.add(currentIngredient);
        }
        selectedSandwich.setIngredients(sandwichIngredients);


        return selectedSandwich;


    }
}
