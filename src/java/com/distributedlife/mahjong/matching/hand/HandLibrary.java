package com.distributedlife.mahjong.matching.hand;

import com.distributedlife.mahjong.reference.hand.Hand;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HandLibrary {
    public static List<Hand> loadFromJson(JSONObject root) {
        List<Hand> hands = new ArrayList<Hand>();

        JSONArray handsJson = root.getJSONArray("hands");
        for (int i = 0; i < handsJson.length(); i++) {
            JSONObject handJson = handsJson.getJSONObject(i);

            List<String> requiredTiles = new ArrayList<String>();
            JSONArray requiredTilesAsJson = handJson.getJSONArray("requiredTiles");
            for (int j = 0; j < requiredTilesAsJson.length(); j++) {
                requiredTiles.add(requiredTilesAsJson.getString(j));
            }

            hands.add(new Hand(handJson.getString("name"), requiredTiles));
        }

        return hands;
    }
}
