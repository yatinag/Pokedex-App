package com.example.mdb_pokedex_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ProfileActivity extends AppCompatActivity {

    TextView pokeNumber;
    TextView pokeName;
    ImageView pokeImg;
    TextView pokeAtk;
    TextView pokeDef;
    TextView pokeHP;
    TextView pokeSPAtk;
    TextView pokeSPDef;
    TextView pokeSpeed;
    TextView pokeSpecies;
    ChipGroup pokeTypes;
    Button searchWeb;
    String poke_name;
    GridLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pokeNumber = findViewById(R.id.pokeNum);
        pokeName = findViewById(R.id.pokeName);
        pokeImg = findViewById(R.id.pokeImg);
        pokeAtk = findViewById(R.id.pokeAttack);
        pokeDef = findViewById(R.id.pokeDefense);
        pokeHP = findViewById(R.id.pokeHP);
        pokeSPAtk = findViewById(R.id.pokeSPAtk);
        pokeSPDef = findViewById(R.id.pokeSPDef);
        pokeSpeed = findViewById(R.id.pokeSpeed);
        pokeSpecies = findViewById(R.id.pokeSpecies);
        pokeTypes = findViewById(R.id.pokeTypes);
        searchWeb = findViewById(R.id.searchWeb);
        grid = findViewById(R.id.mainGrid);


        poke_name = getIntent().getStringExtra("poke_name");

        JSONObject pokemonJsonObject = new JSONObject();

        try {
            pokemonJsonObject = new JSONObject(Utils.readFromFile("poke_data.json", this));
        } catch (JSONException e) {
            Log.e("json reading", "Error reading json from file: " + e.toString());
        }

        Pokemon pokemon_object = new Pokemon(poke_name, pokemonJsonObject);

        pokeNumber.setText("#" + pokemon_object.getPoke_num());
        System.out.println(pokemon_object.getPoke_image());
        Glide.with(this).load(pokemon_object.getPoke_image()).into(pokeImg);
        pokeName.setText(pokemon_object.getPoke_name());
        pokeAtk.setText("Attack: " + pokemon_object.getPoke_attack());
        pokeDef.setText("Defense: " + pokemon_object.getPoke_defense());
        pokeHP.setText("HP: " + pokemon_object.getPoke_hp());
        pokeSPAtk.setText("Sp. Atk: " + pokemon_object.getPoke_sp_attack());
        pokeSPDef.setText("Sp. Def: " + pokemon_object.getPoke_sp_defense());
        pokeSpeed.setText("Speed: " + pokemon_object.getPoke_speed());
        pokeSpecies.setText(pokemon_object.getPoke_species());
        for (String type : pokemon_object.getPoke_types()) {
            Chip chip = new Chip(this);
            chip.setText(type);
            chip.setElevation(15);

            pokeTypes.addView(chip);
        }

        searchWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = poke_name;
                String escapedQuery = null;
                try {
                    escapedQuery = URLEncoder.encode(query, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //TODO: set background gradient according to pokemon type
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFF616261,0xFF131313});
        gd.setCornerRadius(0f);
        grid.setBackground(gd);

    }
}
