package com.ajitapp.sandwichclubnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ajitapp.sandwichclubnew.models.SandWichModel;
import com.ajitapp.sandwichclubnew.utilities.JsonUtil;

import java.util.List;

public class Tab2 extends Fragment {

    /* Instantiate Vars for Tab1 Fragment */
    private TextView ingredients;
    private Intent intent;
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* Grab RootView to Find TextViews by ID*/
        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);

        /* Refactor Detail Activity Code for Fragment to Get Access to Sandwich Model */
        intent = getActivity().getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return getView();
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        SandWichModel sandwich = JsonUtil.parseSandwichJson(json);

        /* Avoid NullException Error then Populate UI */
        if(sandwich != null) {
            ingredients = rootView.findViewById(R.id.ingredients_tv);
            List<String> allIngredients = sandwich.getIngredients();
            String merge2 = makeList(allIngredients);
            ingredients.setText(edgeCase(merge2));
        }

        /* Return RootView to be Inflated in Detail Activity Code */
        return rootView;
    }


    /************************
     *
     * Helper Methods
     *
     ************************/

    /* Refactor closeOnError for Fragment Class */
    private void closeOnError() {
        getActivity().getSupportFragmentManager().popBackStack();
        Toast.makeText(getContext(), R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /* Helper for Populating Text Views that Display Lists */
    private String makeList(List<String> stuff) {
        String merge = "";
        for (String s: stuff) {
            merge += "- " + s + "\n";
        }
        return merge;
    }

    /* Edge Case Helper Method */
    private String edgeCase(String s) {
        s = s.equals("") ? getString(R.string.missing_detail) : s;
        return s;
    }
}