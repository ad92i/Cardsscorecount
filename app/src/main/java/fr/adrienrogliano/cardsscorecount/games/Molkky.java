package fr.adrienrogliano.cardsscorecount.games;

import java.util.List;

import fr.adrienrogliano.cardsscorecount.utils.Game;
import fr.adrienrogliano.cardsscorecount.utils.Lobby;
import fr.adrienrogliano.cardsscorecount.utils.Player;

public class Molkky extends Game {


    public static final String gameName = "Mölkky";
    public static final String gameRules = "";


    public Molkky(String partyName) {
        super(partyName);
    }
    public Molkky() {
        super("");
    }

    @Override
    public Player victoryPlayer(Lobby lobby) {
        return null;
    }

    @Override
    public List<String> setScoringList(Lobby lobby) {
        return null;
    }

    public static String getGameName() {
        return Molkky.gameName;
    }

    public static String getGameRules() {
        return Molkky.gameRules;
    }
}
