package com.ajitapp.sandwichclubnew;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ajitapp.sandwichclubnew.models.SandWichModel;
import com.ajitapp.sandwichclubnew.utilities.JsonUtil;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {
    /* Instantiate Vars for Tab1 Fragment */
    private TextView nameView;
    private TextView origin;
    private TextView aka;
    private TextView descView;
    private Intent intent;
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    public Tab1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
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


        /* Use RootView to Grab Text Views */
        nameView = rootView.findViewById(R.id.name_tv);
        origin = rootView.findViewById(R.id.origin_tv);
        aka = rootView.findViewById(R.id.also_known_tv);
        descView = rootView.findViewById(R.id.description_tv);

        /* Avoid NullException Error then Populate UI */
        if(sandwich != null) {
            origin.setText(edgeCase(sandwich.getPlaceOfOrigin()));
            descView.setText(edgeCase(sandwich.getDescription()));
            nameView.setText(edgeCase(sandwich.getMainName()));
            /* handle attributes populated with array items */
            //other names for sandwich
            List<String> other_names = sandwich.getAlsoKnownAs();
            String merge = makeList(other_names);
            aka.setText(edgeCase(merge));
        }

        return rootView;
    }

    private String makeList(List<String> stuff) {
        String merge = "";
        for (String s: stuff) {
            merge += "- " + s + "\n";
        }
        return merge;
    }

    private String edgeCase(String s) {
        s = s.equals("") ? getString(R.string.missing_detail) : s;
        return s;
    }

    private void closeOnError() {
        getActivity().getSupportFragmentManager().popBackStack();
        Toast.makeText(getContext(), R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
