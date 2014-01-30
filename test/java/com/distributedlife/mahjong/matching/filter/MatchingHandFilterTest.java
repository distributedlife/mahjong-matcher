package com.distributedlife.mahjong.matching.filter;

import com.distributedlife.mahjong.matching.matcher.Match;
import com.distributedlife.mahjong.reference.hand.Hand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MatchingHandFilterTest {
    @Test
    public void keepOnlyBestShouldFilterOutDuplicatesKeepingHighestCount() {
        MatchingHandFilter matchingHandFilter = new MatchingHandFilter(new ArrayList<Hand>());
        List<Match> unfilteredMatches = new ArrayList<Match>();
        unfilteredMatches.add(new Match("Best", 10, null, null));
        unfilteredMatches.add(new Match("Best", 9, null, null));

        List<Match> matches = matchingHandFilter.keepOnlyBest(unfilteredMatches);

        assertThat(matches.size(), is(1));
        assertThat(matches.get(0).getName(), is("Best"));
        assertThat(matches.get(0).getCount(), is(10));
    }

    @Test
    public void keepOnlyBestShouldPickFirstInCaseOfATie() {
        MatchingHandFilter matchingHandFilter = new MatchingHandFilter(new ArrayList<Hand>());
        List<Match> unfilteredMatches = new ArrayList<Match>();
        unfilteredMatches.add(new Match("Best", 10, null, null));
        unfilteredMatches.add(new Match("Best", 10, null, null));

        List<Match> matches = matchingHandFilter.keepOnlyBest(unfilteredMatches);

        assertThat(matches.size(), is(1));
        assertThat(matches.get(0).getName(), is("Best"));
        assertThat(matches.get(0).getCount(), is(10));
    }

    @Test
    public void findAllHandsWithAtLeastOneMatchShouldReturnAllHandsFromLibraryWhereCountIsOver1() {
        List<Hand> handLibrary = new ArrayList<Hand>();
        List<String> firstHand = new ArrayList<String>();
        firstHand.add("1 Bamboo");
        handLibrary.add(new Hand("First", firstHand));
        List<String> secondHand = new ArrayList<String>();
        secondHand.add("1 Bamboo");
        handLibrary.add(new Hand("Second", secondHand));
        List<String> thirdHand = new ArrayList<String>();
        thirdHand.add("1 Spot");
        handLibrary.add(new Hand("Third", thirdHand));

        List<String> hand = new ArrayList<String>();
        hand.add("1 Bamboo");

        MatchingHandFilter matchingHandFilter = new MatchingHandFilter(handLibrary);
        List<Match> matches = matchingHandFilter.findAllHandsWithAtLeastOneMatch(hand);

        assertThat(matches.size(), is(2));
        assertThat(matches.get(0).getName(), is("First"));
        assertThat(matches.get(0).getCount(), is(1));

        assertThat(matches.get(1).getName(), is("Second"));
        assertThat(matches.get(1).getCount(), is(1));
    }
}
