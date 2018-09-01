package fr.adrienrogliano.cardsscorecount.games;


import java.io.Serializable;
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

    // TODO À refaire !
    @Override
    public List<String> setScoringList(Lobby lobby) {
        List<String> list = super.getPlayersName(lobby);

        // Affiche
        for (int i = 0; i < lobby.getTurn(); i++)
            for (int j = 0; j < lobby.getPlayers().size(); j++)
                list.add(lobby.getScorePlayer(lobby.getPlayers().get(j), i));


        return list;

    }

    @Override
    public String getGameName() {
        return Rami.gameName;
    }

    @Override
    public String getGameRules() {
        return Rami.gameRules;
    }


}
