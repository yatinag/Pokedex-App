package com.example.mdb_pokedex_app;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
    public static String readFromFile(String fileName, Context c) {

        String ret = "";

        try {

            AssetManager assetManager = c.getAssets();
            InputStream inputStream = assetManager.open(fileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("file utils", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("file utils", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
