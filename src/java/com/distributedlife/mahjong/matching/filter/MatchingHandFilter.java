package com.distributedlife.mahjong.matching.filter;

import com.distributedlife.mahjong.matching.matcher.Match;
import com.distributedlife.mahjong.reference.hand.Hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchingHandFilter {
    private static final int NOT_FOUND = -1;
    private final List<Hand> handLibrary;

    public MatchingHandFilter(List<Hand> handLibrary) {
        this.handLibrary = handLibrary;
    }

    public List<Match> keepOnlyBest(List<Match> unfilteredMatches) {
        List<Match> filteredMatches = new ArrayList<Match>();

        for (Match match : unfilteredMatches) {
            int position = getIndexOfHand(match.getName(), filteredMatches);
            if (position == NOT_FOUND) {
                filteredMatches.add(match);
            } else {
                if (match.getCount() > filteredMatches.get(position).getCount()) {
                    filteredMatches.remove(position);
                    filteredMatches.add(match);
                }
            }
        }

        return filteredMatches;
    }

    public List<Match> findAllHandsWithAtLeastOneMatch(List<String> tilesInHand) {
        List<Match> allMatches = new ArrayList<Match>();

        for (Hand potentialHand : handLibrary) {
            List<String> tilesLeftInHand = new ArrayList<String>(tilesInHand);
            List<String> matchingTiles = new ArrayList<String>();

            if (potentialHand.getName().equals("Seven Twins")) {
                Match match = handleSevenTwinsHand(tilesInHand);
                if (match != null) {
                    allMatches.add(match);
                }

                continue;
            }

            for (String requiredTile : potentialHand.getRequiredTiles()) {
                if(tilesLeftInHand.contains(requiredTile)) {
                    tilesLeftInHand.remove(requiredTile);
                    matchingTiles.add(requiredTile);
                }
            }

            if (matchingTiles.size() > 0) {
                allMatches.add(new Match(potentialHand.getName(), matchingTiles.size(), matchingTiles, potentialHand.getRequiredTiles()));
            }
        }

        return allMatches;
    }

    private Match handleSevenTwinsHand(List<String> tilesInHand) {
        List<String> sevenTwinsMatchingTiles = new ArrayList<String>();
        for (String tile : tilesInHand) {
            if (sevenTwinsMatchingTiles.contains(tile)) {
                continue;
            }

            if (Collections.frequency(tilesInHand, tile) == 2) {
                sevenTwinsMatchingTiles.add(tile);
                sevenTwinsMatchingTiles.add(tile);
            }
            if (Collections.frequency(tilesInHand, tile) == 3) {
                sevenTwinsMatchingTiles.add(tile);
                sevenTwinsMatchingTiles.add(tile);
            }
            if (Collections.frequency(tilesInHand, tile) == 4) {
                sevenTwinsMatchingTiles.add(tile);
                sevenTwinsMatchingTiles.add(tile);
                sevenTwinsMatchingTiles.add(tile);
                sevenTwinsMatchingTiles.add(tile);
            }
        }

        if (sevenTwinsMatchingTiles.size() > 0) {
            List<String> requiredTiles = new ArrayList<String>(sevenTwinsMatchingTiles);
            for (String tile : tilesInHand) {
                if (sevenTwinsMatchingTiles.size() == 14) {
                    break;
                }

                requiredTiles.add(tile);
            }

            return new Match("Seven Twins", sevenTwinsMatchingTiles.size(), sevenTwinsMatchingTiles, requiredTiles);
        }

        return null;
    }

    private static int getIndexOfHand(String name, List<Match> matches) {
        for (Match match : matches) {
            if (match.getName().equals(name)) {
                return matches.indexOf(match);
            }
        }

        return -1;
    }

    public List<Match> ignoreHandsWithOwnWind(List<Match> unfilteredMatches) {
        List<Match> filteredMatches = new ArrayList<Match>();

        for (Match match : unfilteredMatches) {
            if (match.getRequiredTiles().contains("OwnWind")) {
                continue;
            }

            filteredMatches.add(match);
        }

        return filteredMatches;
    }
}
