package com.distributedlife.mahjong.matching.filter;

import com.distributedlife.mahjong.matching.hand.HandLibrary;
import com.distributedlife.mahjong.matching.hand.HandSourceReader;
import com.distributedlife.mahjong.matching.matcher.Match;
import com.distributedlife.mahjong.reference.adapter.ArrayOfTilesToBitFieldConverter;
import com.distributedlife.mahjong.reference.data.TileSet;
import com.distributedlife.mahjong.reference.hand.Hand;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.distributedlife.mahjong.reference.data.TileSet.BIT_TILES;
import static com.distributedlife.mahjong.reference.data.TileSet.Tile;

public class MatchingHandFilter {
    private static final int NOT_FOUND = -1;
    private HandLibrary handLibrary;

    public MatchingHandFilter(HandLibrary handLibrary) {
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

    public List<Match> findAllHandsWithAtLeastOneMatch(Long part1, Long part2, Long part3, Long part4) {
        List<Match> allMatches = new ArrayList<Match>();

        for (String source : handLibrary.getHandSources()) {
            //                HandSourceReader reader = new HandSourceReader(new FileInputStream(getClass().getResource(source).getFile()));
            HandSourceReader reader = new HandSourceReader(null, new File(getClass().getResource(source).getFile()));

            String line;
            while(!(line = reader.readLine()).equals("")) {
                Hand potentialHand = reader.getHandFromLine(line);

                if (potentialHand.getName().equals("Seven Twins")) {
                    Match match = handleSevenTwinsHand(part1, part2, part3, part4);
                    if (match != null) {
                        allMatches.add(match);
                    }
                    continue;
                }

                if (!potentialHand.isPartialMatch(part1)) {
                    continue;
                }

                List<String> matchingTiles = ArrayOfTilesToBitFieldConverter.convertFromBitField(
                        potentialHand.getPart1() & part1,
                        potentialHand.getPart2() & part2,
                        potentialHand.getPart3() & part3,
                        potentialHand.getPart4() & part4
                );

                Match currentBestMatch = getCurrentBestMatchForPotentialHand(allMatches, potentialHand);
                if (currentBestMatch == null) {
                    addMatchToAllMatches(allMatches, potentialHand, matchingTiles);
                } else {
                    if (matchingTiles.size() > currentBestMatch.getMatchingTiles().size()) {
                        allMatches.remove(currentBestMatch);
                        addMatchToAllMatches(allMatches, potentialHand, matchingTiles);
                    }
                }
            }

            reader.close();
        }

        return allMatches;
    }

    private Match getCurrentBestMatchForPotentialHand(List<Match> allMatches, Hand potentialHand) {
        Match currentBestMatch = null;
        for(Match match : allMatches) {
            if (!match.getName().equals(potentialHand.getName())) {
                continue;
            }

            currentBestMatch = match;
            break;
        }
        return currentBestMatch;
    }

    private void addMatchToAllMatches(List<Match> allMatches, Hand potentialHand, List<String> matchingTiles) {
        Match betterMatch = new Match(
                potentialHand.getName(),
                matchingTiles.size(),
                matchingTiles,
                ArrayOfTilesToBitFieldConverter.convertFromBitField(
                        potentialHand.getPart1(),
                        potentialHand.getPart2(),
                        potentialHand.getPart3(),
                        potentialHand.getPart4()
                )
        );
        allMatches.add(betterMatch );
    }

    private Match handleSevenTwinsHand(Long part1, Long part2, Long part3, Long part4) {
        Long remainingParts1 = part1;
        Long remainingParts2 = part2;
        Long remainingParts3 = part3;
        Long remainingParts4 = part4;

        List<String> sevenTwinsMatchingTiles = new ArrayList<String>();
        for (Tile tile : BIT_TILES) {
            if ((remainingParts4 & tile.v) == tile.v) {
                sevenTwinsMatchingTiles.add(TileSet.getNameForBitTile(tile));
                sevenTwinsMatchingTiles.add(TileSet.getNameForBitTile(tile));
                sevenTwinsMatchingTiles.add(TileSet.getNameForBitTile(tile));
                sevenTwinsMatchingTiles.add(TileSet.getNameForBitTile(tile));
                remainingParts1 -= tile.v;
                remainingParts2 -= tile.v;
                remainingParts3 -= tile.v;
                remainingParts4 -= tile.v;
                continue;
            }
            if ((remainingParts3 & tile.v) == tile.v) {
                sevenTwinsMatchingTiles.add(TileSet.getNameForBitTile(tile));
                sevenTwinsMatchingTiles.add(TileSet.getNameForBitTile(tile));
                sevenTwinsMatchingTiles.add(TileSet.getNameForBitTile(tile));
                remainingParts1 -= tile.v;
                remainingParts2 -= tile.v;
                remainingParts3 -= tile.v;
                continue;
            }
            if ((remainingParts2 & tile.v) == tile.v) {
                sevenTwinsMatchingTiles.add(TileSet.getNameForBitTile(tile));
                sevenTwinsMatchingTiles.add(TileSet.getNameForBitTile(tile));
                remainingParts1 -= tile.v;
                remainingParts2 -= tile.v;
            }
        }

        if (sevenTwinsMatchingTiles.size() > 0) {
            List<String> requiredTiles = new ArrayList<String>(sevenTwinsMatchingTiles);
            for (Tile tile : BIT_TILES) {
                if (sevenTwinsMatchingTiles.size() == 14) {
                    break;
                }
                if ((remainingParts1 & tile.v) == tile.v) {
                    requiredTiles.add(TileSet.getNameForBitTile(tile));
                    remainingParts1 -= tile.v;
                }
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
