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
            String name = handJson.getString("name");

            JSONArray combinationsJson = handJson.getJSONArray("combinations");
            for(int j = 0; j < combinationsJson.length(); j++) {
                JSONObject combinationJson = combinationsJson.getJSONObject(j);

                long part1 = 0;
                long part2 = 0;
                long part3 = 0;
                long part4 = 0;
                if (combinationJson.has("1")) {
                    part1 = combinationJson.getLong("1");
                }
                if (combinationJson.has("2")) {
                    part2 = combinationJson.getLong("2");
                }
                if (combinationJson.has("3")) {
                    part3 = combinationJson.getLong("3");
                }
                if (combinationJson.has("4")) {
                    part4 = combinationJson.getLong("4");
                }

                hands.add(new Hand(name, part1, part2, part3, part4));
            }
        }

        return hands;
    }
}
