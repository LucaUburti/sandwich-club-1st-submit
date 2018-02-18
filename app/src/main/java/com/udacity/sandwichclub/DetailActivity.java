package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.linear_layout) LinearLayout linearLayout;
    @BindView(R.id.aka_header_tv) TextView akaHeaderTv;
    @BindView(R.id.also_known_tv) TextView akaTv;
    @BindView(R.id.ingredients_tv) TextView ingredientsTv;
    @BindView(R.id.origin_header_tv) TextView originHeaderTv;
    @BindView(R.id.description_tv) TextView descriptionTv;
    @BindView(R.id.image_iv)ImageView ingredientsIv;
    @BindView(R.id.origin_tv) TextView originTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {



        String formattedAkas = TextUtils.join(", ", sandwich.getAlsoKnownAs());

        if (formattedAkas.isEmpty()) {
            linearLayout.removeView(akaHeaderTv);
            linearLayout.removeView(akaTv);
        } else {
            akaTv.setText(formattedAkas);
        }

        String formattedIngredients = TextUtils.join(", ", sandwich.getIngredients());
        ingredientsTv.setText(formattedIngredients);

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            linearLayout.removeView(originHeaderTv);
            linearLayout.removeView(originTv);
        } else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }
        descriptionTv.setText(sandwich.getDescription());


    }
}
