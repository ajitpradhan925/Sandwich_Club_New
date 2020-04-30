package com.ajitapp.sandwichclubnew.utilities;

import android.content.Context;
import android.widget.Toast;

import com.ajitapp.sandwichclubnew.models.SandWichModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtil {

        final static  String name = "name";
        final static String mainNameObject = "mainName";
        final static String placeOfOriginObject = "placeOfOrigin";
        final static String descriptionObject = "description";
        final static String imageObject = "image";
        final static String alsoKnownAsArray = "alsoKnownAs";
        final static String ingredientsArray = "ingredients";


        public static SandWichModel parseSandwichJson(String json) {

        try {

            JSONObject base = new JSONObject(json);

            JSONObject nameObject = base.getJSONObject(name);

            JSONArray alsoKnownAs  = nameObject.getJSONArray(alsoKnownAsArray);
            JSONArray  ingredients= base.getJSONArray(ingredientsArray);


            /** Instantiate Array Literal for Alt Names */
            ArrayList<String> alsoKnownAsList = new ArrayList<>();

            for (int i = 0; i<alsoKnownAs.length(); i++) {
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }


            /* Instantiate Array Literal for Ingredients */
            ArrayList<String> ingredientList = new ArrayList<>();

            for (int i = 0; i<ingredients.length(); i++) {
                ingredientList.add(ingredients.getString(i));
            }


            /* Get Non-Array Strings from Base Object */
            String mainName = nameObject.getString(mainNameObject);
            String placeOfOrigin = base.getString(placeOfOriginObject);
            String description = base.getString(descriptionObject);
            String image = base.getString(imageObject);

            /* Return All the Strings */
            return new SandWichModel(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
