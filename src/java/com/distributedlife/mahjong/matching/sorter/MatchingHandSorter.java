package com.distributedlife.mahjong.matching.sorter;

import com.distributedlife.mahjong.matching.matcher.Match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchingHandSorter {
    public List<Match> sortByMostMatches(List<Match> unsortedMatches) {
        List<Match> sortedMatches = new ArrayList<Match>(unsortedMatches);

        Collections.sort(sortedMatches, new MatchComparator());

        return sortedMatches;
    }
}
