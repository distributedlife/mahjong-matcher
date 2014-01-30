package com.distributedlife.mahjong.matching.matcher;

import java.util.List;

public class Match {
    private final String name;
    private final Integer count;
    private final List<String> matchingTiles;
    private final List<String> requiredTiles;

    public Match(String name, int count, List<String> matchingTiles, List<String> requiredTiles) {
        this.name = name;
        this.count = count;
        this.matchingTiles = matchingTiles;
        this.requiredTiles = requiredTiles;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    public List<String> getMatchingTiles() {
        return matchingTiles;
    }

    public List<String> getRequiredTiles() {
        return requiredTiles;
    }
}
