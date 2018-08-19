package fr.adrienrogliano.cardsscorecount.games;

import java.util.List;

import fr.adrienrogliano.cardsscorecount.utils.Lobby;
import fr.adrienrogliano.cardsscorecount.utils.Player;

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
