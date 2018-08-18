package fr.adrienrogliano.cardsscorecount;

import java.util.List;

public abstract class Game {
    private String partyName;

    public Game(String partyName){
        this.partyName = partyName;
    }


    public abstract Player victoryPlayer(Lobby lobby);
    public abstract List<String> setScoringList(Lobby lobby);

    public abstract String getGameName();
    public abstract String getGameRules();


    public String getPartyName() {
        return partyName;
    }

    @Override
    public String toString() {
        return getGameName();
    }
}
