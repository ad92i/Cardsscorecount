package fr.adrienrogliano.cardsscorecount.games;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.adrienrogliano.cardsscorecount.utils.Game;
import fr.adrienrogliano.cardsscorecount.utils.Lobby;
import fr.adrienrogliano.cardsscorecount.utils.Player;

public class Rami extends Game implements Serializable {

    public static final String gameName = "Rami";
    public static final String gameRules = "";

    public Rami(String partyName){
        super(partyName);
    }
    public Rami(){
        this("");
    }


    @Override
    public Player victoryPlayer(Lobby lobby) {
        Player win = lobby.getPlayers().get(0);
        int min = lobby.totalPlayerScore(lobby.getPlayers().get(0));

        for (Player player : lobby.getPlayers())
            if (min > lobby.totalPlayerScore(player)) {
                min = lobby.totalPlayerScore(player);
                win = player;
            }

        return win;
    }

    @Override
    public List<String> setScoringList(Lobby lobby) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < lobby.getTurn(); i++)
            for (int j = 0; j < lobby.getPlayers().size(); j++)
                list.add(lobby.getScorePlayer(lobby.getPlayers().get(j), i));

        return list;
    }

    public static String getGameName() {
        return Rami.gameName;
    }

    public static String getGameRules() {
        return Rami.gameRules;
    }

    @Override
    public String toString() {
        return getGameName();
    }
}
