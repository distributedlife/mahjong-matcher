package com.distributedlife.mahjong.matching.hand;

import com.distributedlife.mahjong.reference.hand.Hand;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HandLibrary {
    private List<String> handsAsResourceFiles;

    public HandLibrary() {
        handsAsResourceFiles = new ArrayList<String>();
        handsAsResourceFiles.add("/data.csv");
//        addSplitFiles("/data-%d.json", 136);
    }

    private void addSplitFiles(String filename, int numberOfSplits) {
        for (int i = 0; i <= numberOfSplits; i++) {
            handsAsResourceFiles.add(String.format(filename, i));
        }
    }

    public HandLibrary(List<String> handsAsResourceFiles) {
        this.handsAsResourceFiles = handsAsResourceFiles;
    }

    public List<Hand> loadFromJson(String resourceName) {
        List<Hand> hands = new ArrayList<Hand>();


        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resourceName)));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                String name = split[0];
                Long p1 = Long.valueOf(split[1]);
                Long p2 = Long.valueOf(split[2]);
                Long p3 = Long.valueOf(split[3]);
                Long p4 = Long.valueOf(split[4]);

                hands.add(new Hand(name, p1, p2, p3, p4));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return hands;

//        JSONObject root;
//        try {
//            root = new JSONObject(IOUtils.toString(HandLibrary.class.getResourceAsStream(resourceName)));
//        } catch (IOException e) {
//            throw new RuntimeException("Hand Library could not found. Please reinstall.", e);
//        }
//
//        String name = root.getString("name");
//
//        JSONArray combinationsJson = root.getJSONArray("combinations");
//        for(int j = 0; j < combinationsJson.length(); j++) {
//            JSONObject combinationJson = combinationsJson.getJSONObject(j);
//
//            long part1 = 0;
//            long part2 = 0;
//            long part3 = 0;
//            long part4 = 0;
//            if (combinationJson.has("1")) {
//                part1 = combinationJson.getLong("1");
//            }
//            if (combinationJson.has("2")) {
//                part2 = combinationJson.getLong("2");
//            }
//            if (combinationJson.has("3")) {
//                part3 = combinationJson.getLong("3");
//            }
//            if (combinationJson.has("4")) {
//                part4 = combinationJson.getLong("4");
//            }
//
//            hands.add(new Hand(name, part1, part2, part3, part4));
//        }
//
//        return hands;
    }

    public List<String> getHandSources() {
        return handsAsResourceFiles;
    }
}
