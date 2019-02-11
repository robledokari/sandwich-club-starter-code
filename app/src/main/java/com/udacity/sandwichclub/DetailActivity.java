package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

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
        Sandwich sw = JsonUtils.parseSandwichJson(json);
        if (sw == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sw);
        Picasso.with(this)
                .load(sw.getImage())
                .into(ingredientsIv);

        setTitle(sw.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sw) {

        TextView alsoKnown = findViewById(R.id.also_known_tv);
        TextView origin = findViewById(R.id.origin_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);

        StringBuilder aux = new StringBuilder();
        for (int i = 0; i < sw.getAlsoKnownAs().size(); i++) {
            aux.append(sw.getAlsoKnownAs().get(i));
            if (i != sw.getAlsoKnownAs().size() - 1)
                aux.append(", ");
        }
        alsoKnown.setText(aux.toString());

        origin.setText(sw.getPlaceOfOrigin());
        description.setText(sw.getDescription());

        aux = new StringBuilder();

        for (int i = 0; i < sw.getIngredients().size(); i++) {
            aux.append(sw.getIngredients().get(i));
            if (i != sw.getIngredients().size() - 1)
                aux.append(", ");
        }
        ingredients.setText(aux.toString());

    }

}