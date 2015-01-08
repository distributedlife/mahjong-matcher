package com.distributedlife.mahjong.matching.performance;

import com.distributedlife.mahjong.matching.filter.MatchingHandFilter;
import com.distributedlife.mahjong.matching.hand.HandLibrary;
import com.distributedlife.mahjong.matching.matcher.MahJongHandMatcher;
import com.distributedlife.mahjong.matching.sorter.MatchingHandSorter;
import com.distributedlife.mahjong.reference.adapter.ArrayOfTilesToBitFieldConverter;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchAgainstAllTheThings {
    @Test
    public void timeToMatchWithOnlyOneTile() {
        MahJongHandMatcher mahJongHandMatcher = new MahJongHandMatcher(
                new MatchingHandSorter(),
                new MatchingHandFilter(new HandLibrary())
        );

        List<String> tilesInHand = Arrays.asList("1 Spot");
        List<Long> handParts = ArrayOfTilesToBitFieldConverter.convertToBitField(tilesInHand);

        List<Long> duration = new ArrayList<Long>();

        for (int i = 0; i < 1; i++) {
            long start = DateTime.now().getMillis();
            mahJongHandMatcher.getMatches(handParts.get(0), handParts.get(1), handParts.get(2), handParts.get(3));
            long finish = DateTime.now().getMillis();

            duration.add(finish - start);
        }

        System.out.println(duration);
    }
}
