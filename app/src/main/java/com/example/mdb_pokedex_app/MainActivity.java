package com.example.mdb_pokedex_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    ChipGroup typeList;
    AutoCompleteTextView selectPoke;
    Button submitBtn;
    TextView attackText;
    TextView defenseText;
    TextView healthText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeList = findViewById(R.id.typeList);
        selectPoke = findViewById(R.id.selectPoke);
        submitBtn = findViewById(R.id.submitBtn);
        attackText = findViewById(R.id.attackText);
        defenseText = findViewById(R.id.defenseText);
        healthText = findViewById(R.id.healthText);

        JSONObject pokemonJsonObject = new JSONObject();

        try {
            pokemonJsonObject = new JSONObject(Utils.readFromFile("poke_data.json", this));
        } catch (JSONException e) {
            Log.e("json reading", "Error reading json from file: " + e.toString());
        }


        ArrayList<String> cleanPokeNames = new ArrayList<>();

        final ArrayList<String> uglyPokeNames = new ArrayList<>();
        ArrayList<String> pokeTypes = new ArrayList<>();
        Iterator<String> keys = pokemonJsonObject.keys();

        while(keys.hasNext()) {
            String currName = keys.next();
            cleanPokeNames.add(currName.replaceAll("\\( ", "\\(").replaceAll(" \\)", "\\)").replaceAll(("  "), " "));
            uglyPokeNames.add(currName);
            JSONArray arrTypes = new JSONArray();
            try {
                JSONObject tempPokeObject = (JSONObject) pokemonJsonObject.get(currName);
                arrTypes = (JSONArray) tempPokeObject.get("Type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < 2; j++) {
                String type = "";
                try {
                    type = (String) arrTypes.get(j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!pokeTypes.contains(type)) {
                    if (!type.equals("") && !type.equals(" ")) {
                        pokeTypes.add(type);
                    }
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, uglyPokeNames);
        selectPoke.setAdapter(adapter);

        selectPoke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                  String sel_poke_name = parent.getItemAtPosition(position).toString();
                  selectPoke.setText("");
                  intent.putExtra("poke_name", sel_poke_name);
                  startActivity(intent);
              }
          });

        for (String type : pokeTypes) {
            Chip chip = new Chip(this);
            chip.setText(type);
            chip.setCheckable(true);
            chip.setElevation(15);

            typeList.addView(chip);
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int chipsCount = typeList.getChildCount();
                int j = 0, chipsCheckCount = 0;
                while (j < chipsCount) {
                    Chip chip = (Chip) typeList.getChildAt(j);
                    if (chip.isChecked() ) {
                        chipsCheckCount++;
                    }
                    j++;
                }
                if (chipsCheckCount > 2) {
                    System.out.println(chipsCheckCount);
                    Toast toast = Toast.makeText(MainActivity.this,"Please select <= 2 Types", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    int i = 0;
                    j = 0;
                    String[] pokeTypes = new String[2];

                    while (i < chipsCount) {
                        Chip chip = (Chip) typeList.getChildAt(i);
                        if (chip.isChecked() ) {
                            pokeTypes[j] = (chip.getText().toString());
                            j++;
                        }
                        i++;
                    }
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    intent.putExtra("poke_types", pokeTypes);
                    intent.putExtra("attack_text", attackText.getText().toString());
                    intent.putExtra("defense_text", defenseText.getText().toString());
                    intent.putExtra("health_text", healthText.getText().toString());
                    attackText.setText("");
                    defenseText.setText("");
                    healthText.setText("");
                    startActivity(intent);
                }
            }
        });
    }
}
