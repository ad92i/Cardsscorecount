package fr.adrienrogliano.cardsscorecount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rami extends Game {

    private String rules;

    public Rami(String partyName){
        super(partyName);
    }

    public Rami() {
        super();
    }

    @Override
    public Player victoryPlayer(Lobby lobby) {
        Player winner = lobby.getPlayers().get(0);
        int min = lobby.totalPlayerScore(lobby.getPlayers().get(0));

        for (Player player : lobby.getPlayers()) {
            if (min > lobby.totalPlayerScore(player)) {
                min = lobby.totalPlayerScore(player);
                winner = player;

            }

        }

        return winner;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getRules() {
        return rules;
    }

    @Override
    public String getGameName() {
        return "Rami";
    }



}
