package com.distributedlife.mahjong.matching.matcher;

import com.distributedlife.mahjong.matching.filter.MatchingHandFilter;
import com.distributedlife.mahjong.matching.sorter.MatchingHandSorter;
import com.distributedlife.mahjong.reference.data.TileSet;
import com.distributedlife.mahjong.reference.hand.Hand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.distributedlife.mahjong.reference.data.TileSet.Tile;
import static com.distributedlife.mahjong.reference.data.TileSet.Winds;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MahJongHandMatcherTest {
    @Test
    public void shouldMatchEntriesInTheHandLibrary() {
        List<Hand> handLibrary = new ArrayList<Hand>();
        handLibrary.add(new Hand("Run, Pung and a Pair", runPungAndAPairBamboo()));

        MahJongHandMatcher mahJongHandMatcher = new MahJongHandMatcher(new MatchingHandSorter(), new MatchingHandFilter(handLibrary));
        List<Match> matches = mahJongHandMatcher.getMatches(
                Tile.B1.v,
                0L,
                0L,
                0L
        );
        assertThat(matches.get(0).getName(), is("Run, Pung and a Pair"));
        assertThat(matches.get(0).getCount(), is(1));
        assertThat(matches.size(), is(1));
    }

    @Test
    public void shouldPickTheBestMatchForAHand() {
        List<Hand> handLibrary = new ArrayList<Hand>();
        handLibrary.add(new Hand("Run, Pung and a Pair", runPungAndAPairBamboo()));
        handLibrary.add(new Hand("Run, Pung and a Pair", runPungAndAPairSpot()));

        MahJongHandMatcher mahJongHandMatcher = new MahJongHandMatcher(new MatchingHandSorter(), new MatchingHandFilter(handLibrary));
        List<Match> matches = mahJongHandMatcher.getMatches(
                Tile.B1.v + Tile.S2.v,
                Tile.S2.v,
                0L,
                0L
        );
        assertThat(matches.get(0).getName(), is("Run, Pung and a Pair"));
        assertThat(matches.get(0).getCount(), is(2));
        assertThat(matches.size(), is(1));
    }

    @Test
    public void shouldMatchEachDifferentHandOrderedByMatchCount() {
        List<Hand> handLibrary = new ArrayList<Hand>();
        handLibrary.add(new Hand("Run, Pung and a Pair", runPungAndAPairSpot()));
        handLibrary.add(new Hand("Gates of Heaven", gatesOfHeaven()));

        MahJongHandMatcher mahJongHandMatcher = new MahJongHandMatcher(new MatchingHandSorter(), new MatchingHandFilter(handLibrary));
        List<Match> matches = mahJongHandMatcher.getMatches(
                Tile.S2.v + Tile.S8.v,
                Tile.S8.v,
                0L,
                0L
        );
        assertThat(matches.size(), is(2));

        assertThat(matches.get(0).getName(), is("Gates of Heaven"));
        assertThat(matches.get(0).getCount(), is(3));
        assertThat(matches.get(1).getName(), is("Run, Pung and a Pair"));
        assertThat(matches.get(1).getCount(), is(2));
    }

    @Test
    public void shouldConvertWindIntoOwnWindAndMatchAgain() {
        List<Hand> handLibrary = new ArrayList<Hand>();
        handLibrary.add(new Hand("Red Lantern", redLantern()));

        MahJongHandMatcher mahJongHandMatcher = new MahJongHandMatcher(new MatchingHandSorter(), new MatchingHandFilter(handLibrary));
        List<Match> matchesWithOwnWind = mahJongHandMatcher.getMatchesWithOwnWind(
                Tile.S2.v + Tile.S8.v + Tile.WW.v,
                0L,
                0L,
                0L,
                Winds.West
        );

        assertThat(matchesWithOwnWind.size(), is(1));

        assertThat(matchesWithOwnWind.get(0).getName(), is("Red Lantern"));
        assertThat(matchesWithOwnWind.get(0).getCount(), is(3));
    }

    @Test
    public void shouldReturnASevenPairsHandIfAtLeastOnePairExists() {
        List<Hand> handLibrary = new ArrayList<Hand>();
        handLibrary.add(new Hand("Seven Twins", null));

        MahJongHandMatcher mahJongHandMatcher = new MahJongHandMatcher(new MatchingHandSorter(), new MatchingHandFilter(handLibrary));
        List<Match> matchesWithOwnWind = mahJongHandMatcher.getMatches(
                Tile.S2.v + Tile.S8.v,
                Tile.S2.v,
                0L,
                0L
        );

        assertThat(matchesWithOwnWind.size(), is(1));

        assertThat(matchesWithOwnWind.get(0).getName(), is("Seven Twins"));
        assertThat(matchesWithOwnWind.get(0).getCount(), is(2));
    }

    @Test
    public void shouldReturnASevenPairsHandIfAtPungExists() {
        List<String> hand = new ArrayList<String>();
        hand.add("2 Spot");
        hand.add("2 Spot");
        hand.add("2 Spot");

        List<Hand> handLibrary = new ArrayList<Hand>();
        handLibrary.add(new Hand("Seven Twins", null));

        MahJongHandMatcher mahJongHandMatcher = new MahJongHandMatcher(new MatchingHandSorter(), new MatchingHandFilter(handLibrary));
        List<Match> matchesWithOwnWind = mahJongHandMatcher.getMatches(
                Tile.S2.v,
                Tile.S2.v,
                Tile.S2.v,
                0L
        );

        assertThat(matchesWithOwnWind.size(), is(1));

        assertThat(matchesWithOwnWind.get(0).getName(), is("Seven Twins"));
        assertThat(matchesWithOwnWind.get(0).getCount(), is(3));
    }

    @Test
    public void shouldReturnASevenPairsHandIfAtKongExists() {
        List<Hand> handLibrary = new ArrayList<Hand>();
        handLibrary.add(new Hand("Seven Twins", null));

        MahJongHandMatcher mahJongHandMatcher = new MahJongHandMatcher(new MatchingHandSorter(), new MatchingHandFilter(handLibrary));
        List<Match> matchesWithOwnWind = mahJongHandMatcher.getMatches(
                Tile.S2.v,
                Tile.S2.v,
                Tile.S2.v,
                Tile.S2.v
        );

        assertThat(matchesWithOwnWind.size(), is(1));

        assertThat(matchesWithOwnWind.get(0).getName(), is("Seven Twins"));
        assertThat(matchesWithOwnWind.get(0).getCount(), is(4));
    }

    @Test
    public void shouldNotReturnASevenPairsHandIfNoPairsExist() {
        List<Hand> handLibrary = new ArrayList<Hand>();
        handLibrary.add(new Hand("Seven Twins", null));

        MahJongHandMatcher mahJongHandMatcher = new MahJongHandMatcher(new MatchingHandSorter(), new MatchingHandFilter(handLibrary));
        List<Match> matchesWithOwnWind = mahJongHandMatcher.getMatchesWithOwnWind(
                Tile.S2.v + Tile.S8.v + Tile.WW.v,
                0L,
                0L,
                0L,
                TileSet.Winds.West);

        assertThat(matchesWithOwnWind.size(), is(0));
    }

    private List<String> runPungAndAPairBamboo() {
        ArrayList<String> hand = new ArrayList<String>();
        hand.add("1 Bamboo");
        hand.add("2 Bamboo");
        hand.add("3 Bamboo");
        hand.add("4 Bamboo");
        hand.add("5 Bamboo");
        hand.add("6 Bamboo");
        hand.add("7 Bamboo");
        hand.add("8 Bamboo");
        hand.add("9 Bamboo");

        hand.add("1 Bamboo");
        hand.add("1 Bamboo");
        hand.add("1 Bamboo");

        hand.add("2 Bamboo");
        hand.add("2 Bamboo");

        return hand;
    }

    private List<String> runPungAndAPairSpot() {
        ArrayList<String> hand = new ArrayList<String>();
        hand.add("1 Spot");
        hand.add("2 Spot");
        hand.add("3 Spot");
        hand.add("4 Spot");
        hand.add("5 Spot");
        hand.add("6 Spot");
        hand.add("7 Spot");
        hand.add("8 Spot");
        hand.add("9 Spot");

        hand.add("1 Spot");
        hand.add("1 Spot");
        hand.add("1 Spot");

        hand.add("2 Spot");
        hand.add("2 Spot");

        return hand;
    }

    private List<String> gatesOfHeaven() {
        ArrayList<String> hand = new ArrayList<String>();
        hand.add("2 Spot");
        hand.add("3 Spot");
        hand.add("4 Spot");
        hand.add("5 Spot");
        hand.add("6 Spot");
        hand.add("7 Spot");
        hand.add("8 Spot");

        hand.add("8 Spot");

        hand.add("1 Spot");
        hand.add("1 Spot");
        hand.add("1 Spot");

        hand.add("9 Spot");
        hand.add("9 Spot");
        hand.add("9 Spot");

        return hand;
    }

    private List<String> redLantern() {
        ArrayList<String> hand = new ArrayList<String>();
        hand.add("1 Spot");
        hand.add("2 Spot");
        hand.add("3 Spot");
        hand.add("4 Spot");
        hand.add("5 Spot");
        hand.add("6 Spot");
        hand.add("7 Spot");
        hand.add("8 Spot");

        hand.add("1 Spot");

        hand.add("Red");
        hand.add("Red");
        hand.add("Red");

        hand.add("OwnWind");
        hand.add("OwnWind");
        hand.add("OwnWind");

        return hand;
    }
}
