package com.distributedlife.mahjong.matching.matcher;

import com.distributedlife.mahjong.matching.filter.MatchingHandFilter;
import com.distributedlife.mahjong.matching.sorter.MatchingHandSorter;
import com.distributedlife.mahjong.reference.data.TileSet;

import java.util.List;

public class MahJongHandMatcher {
    private final MatchingHandSorter sorter;
    private final MatchingHandFilter filter;

    public MahJongHandMatcher(MatchingHandSorter sorter, MatchingHandFilter filter) {
        this.sorter = sorter;
        this.filter = filter;
    }

    public List<Match> getMatches(Long part1, Long part2, Long part3, Long part4) {
        return sorter.sortByMostMatches(
                filter.ignoreHandsWithOwnWind(
                        filter.keepOnlyBest(
                            filter.findAllHandsWithAtLeastOneMatch(part1, part2, part3, part4)
                        )
                )
        );
    }

    public List<Match> getMatchesWithOwnWind(Long part1, Long part2, Long part3, Long part4, TileSet.Winds ownWind) {
        Long ownWindValue = TileSet.getValueForTile(ownWind.toString());
        if ((part1 & ownWindValue) == ownWindValue) {
            part1 -= ownWindValue;
            part1 += TileSet.Tile.OW.v;
        }
        if ((part2 & ownWindValue) == ownWindValue) {
            part2 -= ownWindValue;
            part2 += TileSet.Tile.OW.v;
        }
        if ((part3 & ownWindValue) == ownWindValue) {
            part3 -= ownWindValue;
            part3 += TileSet.Tile.OW.v;
        }
        if ((part4 & ownWindValue) == ownWindValue) {
            part4 -= ownWindValue;
            part4 += TileSet.Tile.OW.v;
        }

        return sorter.sortByMostMatches(
            filter.keepOnlyBest(
                filter.findAllHandsWithAtLeastOneMatch(part1, part2, part3, part4)
            )
        );
    }
}
