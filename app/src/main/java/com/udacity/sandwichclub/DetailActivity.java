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

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
/*SUGGESTION
To learn more, here, you can also try to use error() and placeholder() provided by Picasso to avoid the potential crash due to empty or null image URL values. Before the error placeholder is shown, Picasso will retry your request for three times.

You could try to use these two methods as shown in the sample code below (from Picasso documentation):

Picasso.with(context)
    .load(url)
    .placeholder(R.drawable.user_placeholder)
    .error(R.drawable.user_placeholder_error)
    .into(imageView);
Since the quality of the data maintained in this movie database is really good, using Picasso without error() might not cause any problem. However, when it comes to some other APIs (unfortunately, Spotify is one of them), the chance of fighting against some strange values could get higher. So that's why I strongly suggest you to use these two methods in your future projects.

Try it yourself! :)
*/

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

        LinearLayout linearLayout = findViewById(R.id.linear_layout);

        TextView akaHeaderTv = findViewById(R.id.aka_header_tv);
        TextView akaTv = findViewById(R.id.also_known_tv);
        String formattedAkas = TextUtils.join(", ", sandwich.getAlsoKnownAs());

        if (formattedAkas.isEmpty()) {
            linearLayout.removeView(akaHeaderTv);
            linearLayout.removeView(akaTv);
        } else {
            akaTv.setText(formattedAkas);
        }

        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        String formattedIngredients = TextUtils.join(", ", sandwich.getIngredients());
        ingredientsTv.setText(formattedIngredients);

        TextView originHeaderTv = findViewById(R.id.origin_header_tv);
        TextView originTv = findViewById(R.id.origin_tv);
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            linearLayout.removeView(originHeaderTv);
            linearLayout.removeView(originTv);
        } else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }
        TextView descriptionTv = findViewById(R.id.description_tv);
        descriptionTv.setText(sandwich.getDescription());


    }
}
