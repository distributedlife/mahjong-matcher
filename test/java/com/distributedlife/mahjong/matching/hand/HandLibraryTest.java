package com.distributedlife.mahjong.matching.hand;

import com.distributedlife.mahjong.reference.hand.Hand;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HandLibraryTest {
    @Test
    @Ignore
    public void shouldLoadFromJson() throws IOException {
        JSONObject jsonObject = new JSONObject(IOUtils.toString(HandLibraryTest.class.getResourceAsStream("/two-hands.json")));

        List<Hand> hands = HandLibrary.loadFromJson(jsonObject);

        assertThat(hands.get(0).getName(), is("Run, Pung and a Pair"));
//        assertThat(hands.get(0).getRequiredTiles(), is(Arrays.asList(
//                "1 Bamboo",
//                "1 Bamboo",
//                "1 Bamboo",
//                "1 Bamboo",
//                "2 Bamboo",
//                "2 Bamboo",
//                "2 Bamboo",
//                "3 Bamboo",
//                "4 Bamboo",
//                "5 Bamboo",
//                "6 Bamboo",
//                "7 Bamboo",
//                "8 Bamboo",
//                "9 Bamboo"
//        )));

        assertThat(hands.get(1).getName(), is("Run, Pung and a Pair"));
//        assertThat(hands.get(1).getRequiredTiles(), is(Arrays.asList(
//                "1 Bamboo",
//                "1 Bamboo",
//                "1 Bamboo",
//                "1 Bamboo",
//                "2 Bamboo",
//                "3 Bamboo",
//                "3 Bamboo",
//                "3 Bamboo",
//                "4 Bamboo",
//                "5 Bamboo",
//                "6 Bamboo",
//                "7 Bamboo",
//                "8 Bamboo",
//                "9 Bamboo"
//        )));
    }

    @Test
    public void shouldLoadAllHands() throws IOException {
        JSONObject jsonObject = new JSONObject(IOUtils.toString(HandLibraryTest.class.getResourceAsStream("/all-hands.json")));

        List<Hand> hands = HandLibrary.loadFromJson(jsonObject);

        assertThat(hands.size(), is(1708));
    }
}
