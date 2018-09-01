package fr.adrienrogliano.cardsscorecount.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public abstract class Game implements Serializable {
    private String partyName;

    public Game(String partyName){
        this.partyName = partyName;
    }


    public List<String> getPlayersName(Lobby lobby) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < lobby.getPlayers().size(); i++)
            list.add(lobby.getPlayers().get(i).toString());
        return list;
    }

    public List<String> getTotalscore(Lobby lobby) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < lobby.getPlayers().size(); i++)
            list.add(String.valueOf(lobby.totalPlayerScore(lobby.getPlayers().get(i))));

        return list;

    }

    public List<String> getCompleteScoreList(Lobby lobby) {
        List<String> list = new ArrayList<>();

        list.addAll(getPlayersName(lobby));
        list.addAll(setScoringList(lobby));


        return list;
    }

    public abstract Player victoryPlayer(Lobby lobby);
    public abstract List<String> setScoringList(Lobby lobby);


    public String getPartyName() {
        return partyName;
    }

}
