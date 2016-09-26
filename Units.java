package com.example.owner.hanieum_project.data;

import com.example.owner.hanieum_project.data.JSONPopulator;

import org.json.JSONObject;

/**
 * Created by Owner on 2016-08-08.
 */
public class Units implements JSONPopulator {
    private  String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void poupulate(JSONObject data) {
        temperature = data.optString("temperature");

    }
}
