package com.example.mdb_pokedex_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static java.lang.Integer.parseInt;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);

        int atk = 0, def = 0, hp = 0;
        String[] poke_types = getIntent().getStringArrayExtra("poke_types");
        String attack_text = getIntent().getStringExtra("attack_text");
        if (attack_text != null && !attack_text.equals("")) {
            atk = parseInt(attack_text);
        }
        String defense_text = getIntent().getStringExtra("defense_text");
        if (defense_text != null && !defense_text.equals("")) {
            def = parseInt(defense_text);
        }
        String health_text = getIntent().getStringExtra("health_text");
        if (health_text != null && !health_text.equals("")) {
            hp = parseInt(health_text);
        }

        JSONObject pokemonJsonObject = new JSONObject();

        try {
            pokemonJsonObject = new JSONObject(Utils.readFromFile("poke_data.json", this));
        } catch (JSONException e) {
            Log.e("json reading", "Error reading json from file: " + e.toString());
        }

        ArrayList<Pokemon> pokeNames = new ArrayList<>();

        Iterator<String> keys = pokemonJsonObject.keys();

        while(keys.hasNext()) {
            String currName = keys.next();
            Pokemon pokemon_object = new Pokemon(currName, pokemonJsonObject);
            if (pokemon_object.getPoke_attack() >= atk && pokemon_object.getPoke_defense() >= def
                    && pokemon_object.getPoke_hp() >= hp) {
                if (poke_types == null || poke_types[0] == null) {
                    pokeNames.add(pokemon_object);
                } else {
                    boolean maybe = true;
                    for(int i = 0; i < pokemon_object.getPoke_types().size(); i++) {
                        if(!pokemon_object.getPoke_types().contains(poke_types[i])) {
                            maybe = false;
                        }
                    }
                    if (maybe) {
                        pokeNames.add(pokemon_object);
                    }
                }
            }

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, pokeNames);
        recyclerView.setAdapter(adapter);

    }
}
