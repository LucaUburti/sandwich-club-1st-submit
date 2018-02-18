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

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        if (json.isEmpty()) {
            return null;
        }
        Sandwich selectedSandwich = new Sandwich();

        JSONObject jsonSandwich = new JSONObject(json);

        JSONObject jsonName = jsonSandwich.getJSONObject("name");
        String sandwichName = jsonName.getString("mainName");
        selectedSandwich.setMainName(sandwichName);

        JSONArray jsonAKAs = jsonName.getJSONArray("alsoKnownAs");
        ArrayList<String> sandwichAKAs = new ArrayList<>();
        for (int i = 0; i < jsonAKAs.length(); i++) {
            String currentAKA = jsonAKAs.getString(i);
            sandwichAKAs.add(currentAKA);
        }
        selectedSandwich.setAlsoKnownAs(sandwichAKAs);


        String sandwichOrigin = jsonSandwich.getString("placeOfOrigin");
        selectedSandwich.setPlaceOfOrigin(sandwichOrigin);

        String sandwichDescription = jsonSandwich.getString("description");
        selectedSandwich.setDescription(sandwichDescription);

        String sandwichImage = jsonSandwich.getString("image");
        selectedSandwich.setImage(sandwichImage);


        JSONArray jsonIngredients = jsonSandwich.getJSONArray("ingredients");
        ArrayList<String> sandwichIngredients = new ArrayList<>();
        for (int i = 0; i < jsonIngredients.length(); i++) {
            String currentIngredient = jsonIngredients.getString(i);
            sandwichIngredients.add(currentIngredient);
        }
        selectedSandwich.setIngredients(sandwichIngredients);


        return selectedSandwich;


    }
}
