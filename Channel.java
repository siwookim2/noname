package com.example.owner.hanieum_project.data;

import org.json.JSONObject;

/**
 * Created by Owner on 2016-08-08.
 */
public class Channel implements JSONPopulator {
    private Units units;
    private Item item;

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public void poupulate(JSONObject data) {

        units = new Units();
        units.poupulate(data.optJSONObject("units"));

        item = new Item();
        item.poupulate(data.optJSONObject("item"));

    }
}
