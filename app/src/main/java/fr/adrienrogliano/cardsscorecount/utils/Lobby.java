package fr.adrienrogliano.cardsscorecount.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.adrienrogliano.cardsscorecount.games.Game;


public class Lobby implements Serializable {

    private List<Player> players;
    private Game game;
    private String partyName;
    private Map<Player, List<String>> scoreStringListMap;


    public Lobby(List<Player> players, Game game){
        this.players = players;
        this.game = game;

        this.scoreStringListMap = new HashMap<>();
        this.partyName = game.getGameName() + " : " + game.getPartyName();
    }

    public List<Player> getPlayers(){
        return players;
    }

    public List<String> getScoreListPlayer(Player player) {
        return scoreStringListMap.get(player);
    }

    public String getScorePlayer(Player player, int position) {
        return scoreStringListMap.get(player).get(position);
    }

    public Game getGame() {
        return game;
    }

    public int getTurn() {
        return scoreStringListMap.get(players.get(0)).size();
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyName() {
        return partyName;
    }




    public int totalPlayerScore(Player player) {
        int total = 0;
        for (String string : scoreStringListMap.get(player)) {
            total += Integer.valueOf(string);
        }
        return total;
    }

    public void initializeScore() {
        for (Player player : players) {
            scoreStringListMap.put(player, new ArrayList<String>());

        }
    }
    public void addScore(Player player, String score) {
        scoreStringListMap.get(player).add(score);
    }


}
