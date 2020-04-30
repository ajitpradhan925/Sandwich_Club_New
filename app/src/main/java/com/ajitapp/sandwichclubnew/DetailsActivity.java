package com.ajitapp.sandwichclubnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ajitapp.sandwichclubnew.Adapter.PagerAdapter;
import com.ajitapp.sandwichclubnew.models.SandWichModel;
import com.ajitapp.sandwichclubnew.utilities.JsonUtil;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ImageView ingredientIV;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ingredientIV = (ImageView) findViewById(R.id.image_tv);


        intent = getIntent();
        if(intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if(position == DEFAULT_POSITION) {
            // Extra position not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        SandWichModel sandwich = JsonUtil.parseSandwichJson(json);

        if(sandwich == null) {
            closeOnError();
            return;
        }
        inflateTabs(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.sandwich)
                .error(R.drawable.sandwich)
                .into(ingredientIV);

        setTitle(sandwich.getMainName());
    }

    private void inflateTabs(SandWichModel sandwich) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout.addTab(tabLayout.newTab().setText("Background"));
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());



        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }
    private void closeOnError() {
        finish();
        Context context = DetailsActivity.this;

        Toast.makeText(context, R.string.detail_error_message, Toast.LENGTH_LONG).show();

    }
}
