package com.example.mdb_pokedex_app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Pokemon {
    String poke_name;
    int poke_num;
    int poke_attack;
    int poke_defense;
    int poke_hp;
    int poke_sp_attack;
    int poke_sp_defense;
    int poke_speed;
    String poke_name_dirty;
    String poke_species;
    String poke_image;
    ArrayList<String> poke_types;

    public Pokemon(String poke_name, JSONObject pokemonJsonObject) {
        JSONObject tempPokeObject;
        poke_types = new ArrayList<>();
        JSONArray arrTypes = new JSONArray();
        String tempNum = "";
        try {
            tempPokeObject = (JSONObject) pokemonJsonObject.get(poke_name);
            arrTypes = (JSONArray) tempPokeObject.get("Type");
            poke_num = Integer.parseInt((String) tempPokeObject.get("#"));
            tempNum = (String) tempPokeObject.get("#");
            poke_attack = Integer.parseInt((String) tempPokeObject.get("Attack"));
            poke_defense = Integer.parseInt((String) tempPokeObject.get("Defense"));
            poke_hp = Integer.parseInt((String) tempPokeObject.get("HP"));
            poke_sp_attack = Integer.parseInt((String) tempPokeObject.get("Sp. Atk"));
            poke_sp_defense = Integer.parseInt((String) tempPokeObject.get("Sp. Def"));
            poke_speed = Integer.parseInt((String) tempPokeObject.get("Speed"));
            poke_species = (String) tempPokeObject.get("Species");
            poke_name_dirty = poke_name;
            this.poke_name = poke_name.replaceAll("\\( ", "\\(").replaceAll(" \\)", "\\)")
                    .replaceAll(("  "), " ");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < arrTypes.length(); i++) {
            try {
                poke_types.add(arrTypes.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        poke_image = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/" + tempNum + ".png";
    }

    public int getPoke_attack() {
        return poke_attack;
    }

    public ArrayList<String> getPoke_types() {
        return poke_types;
    }

    public int getPoke_defense() {
        return poke_defense;
    }

    public int getPoke_num() {
        return poke_num;
    }

    public int getPoke_hp() {
        return poke_hp;
    }

    public int getPoke_sp_attack() {
        return poke_sp_attack;
    }

    public int getPoke_sp_defense() {
        return poke_sp_defense;
    }

    public int getPoke_speed() {
        return poke_speed;
    }

    public String getPoke_image() {
        return poke_image;
    }

    public String getPoke_name() {
        return poke_name;
    }

    public String getPoke_species() {
        if (!poke_species.equals("")){
            return ("Species: " + poke_species);
        }
        return ("Species not found");
    }

    public String getPoke_name_dirty() {
        return poke_name_dirty;
    }
}
