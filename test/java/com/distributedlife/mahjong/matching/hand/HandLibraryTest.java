package com.distributedlife.mahjong.matching.hand;

import com.distributedlife.mahjong.reference.hand.Hand;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.distributedlife.mahjong.reference.data.TileSet.Tile;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HandLibraryTest {
    @Test
    public void shouldLoadFromJson() throws IOException {
        HandLibrary handLibrary = new HandLibrary(Arrays.asList("/two-hands.json"));
        List<Hand> hands = handLibrary.loadFromJson("/two-hands.json");

        assertThat(hands.get(0).getName(), is("Yin Yang"));
        assertThat(hands.get(0).getPart1(), is(
                Tile.B1.v + Tile.B2.v + Tile.B3.v + Tile.B4.v + Tile.B5.v +
                        Tile.S5.v + Tile.S6.v + Tile.S7.v + Tile.S8.v + Tile.S9.v
        ));
        assertThat(hands.get(0).getPart2(), is(
            Tile.B1.v + Tile.B5.v +
            Tile.S5.v + Tile.S9.v
        ));
        assertThat(hands.get(0).getPart3(), is(0L));
        assertThat(hands.get(0).getPart4(), is(0L));


        assertThat(hands.get(1).getName(), is("Yin Yang"));
        assertThat(hands.get(1).getPart1(), is(
            Tile.B5.v + Tile.B6.v + Tile.B7.v + Tile.B8.v + Tile.B9.v +
            Tile.S1.v + Tile.S2.v + Tile.S3.v + Tile.S4.v + Tile.S5.v
        ));
        assertThat(hands.get(1).getPart2(), is(
            Tile.B5.v + Tile.B9.v +
            Tile.S1.v + Tile.S5.v
        ));
        assertThat(hands.get(1).getPart3(), is(0L));
        assertThat(hands.get(1).getPart4(), is(0L));
    }
}