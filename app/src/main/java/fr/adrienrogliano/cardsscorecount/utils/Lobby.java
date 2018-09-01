package fr.adrienrogliano.cardsscorecount.utils;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Lobby implements Serializable {

    private List<Player> players;
    private Game game;
    private String partyName;
    private Map<Player, List<String>> scoreStringListMap;


    public Lobby(List<Player> players, Game game){
        this.players = players;
        this.game = game;

        this.scoreStringListMap = new HashMap<>();
        this.partyName = game + " : " + game.getPartyName();
    }

    public List<Player> getPlayers(){
        return players;
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

    public String getPartyName() {
        return partyName;
    }


    public void setScoreMap(HashMap<Player, List<String>> hashMap) {
        this.scoreStringListMap = hashMap;
    }



    public void initializeScore() {
        for (Player player : players) {
            scoreStringListMap.put(player, new ArrayList<String>());

        }
    }

    public void addScorePlayer(Player player, String score) {
        scoreStringListMap.get(player).add(score);
    }

    public int totalPlayerScore(Player player) {
        int total = 0;
        for (String string : scoreStringListMap.get(player)) {
            total += Integer.valueOf(string);
        }
        return total;
    }


    public static Lobby loadLobby(Context context) throws IOException, ClassNotFoundException {
        ObjectInputStream load = new ObjectInputStream(context.openFileInput("lobby"));
        Lobby lobby = (Lobby) load.readObject();

        load.close();
        return lobby;
    }

    public void saveLobby(Context context) throws IOException {
        ObjectOutputStream save = new ObjectOutputStream(context.openFileOutput("lobby", Context.MODE_PRIVATE));
        save.writeObject(this);
        save.close();
    }


}
