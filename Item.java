package com.example.owner.hanieum_project.data;

import org.json.JSONObject;

/**
 * Created by Owner on 2016-08-08.
 */
public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition(){
        return condition;
    }

    @Override
    public void poupulate(JSONObject data) {
        condition = new Condition();
        condition.poupulate(data.optJSONObject("condition"));

    }
}
