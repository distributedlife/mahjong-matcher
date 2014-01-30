package com.distributedlife.mahjong.matching.matcher;

import com.distributedlife.mahjong.matching.filter.MatchingHandFilter;
import com.distributedlife.mahjong.matching.sorter.MatchingHandSorter;
import com.distributedlife.mahjong.reference.data.TileSet;

import java.util.ArrayList;
import java.util.List;

public class MahJongHandMatcher {
    private final MatchingHandSorter sorter;
    private final MatchingHandFilter filter;

    public MahJongHandMatcher(MatchingHandSorter sorter, MatchingHandFilter filter) {
        this.sorter = sorter;
        this.filter = filter;
    }

    public List<Match> getMatches(List<String> tilesInHand) {
        return sorter.sortByMostMatches(
                filter.ignoreHandsWithOwnWind(
                        filter.keepOnlyBest(
                            filter.findAllHandsWithAtLeastOneMatch(tilesInHand)
                        )
                )
        );
    }

    public List<Match> getMatchesWithOwnWind(List<String> tilesInHand, TileSet.Winds ownWind) {
        List<String> tilesInHandWithOwnWind = new ArrayList<String>();
        for (String tile : tilesInHand) {
            if (tile.equals(ownWind.toString())) {
                tilesInHandWithOwnWind.add(TileSet.Winds.OwnWind.toString());
            } else {
                tilesInHandWithOwnWind.add(tile);
            }
        }

        return sorter.sortByMostMatches(
            filter.keepOnlyBest(
                filter.findAllHandsWithAtLeastOneMatch(tilesInHandWithOwnWind)
            )
        );
    }
}
