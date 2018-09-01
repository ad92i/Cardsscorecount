package fr.adrienrogliano.cardsscorecount.games;

import java.util.List;

import fr.adrienrogliano.cardsscorecount.utils.Game;
import fr.adrienrogliano.cardsscorecount.utils.Lobby;
import fr.adrienrogliano.cardsscorecount.utils.Player;

public class Belote extends Game {

    // TODO Ajout de la gestion d'équipe ! en fonction du jeu choisi crée ou des équipe, mettre dans LobbyActivity ou autre je sais pas à réfléchir.

    public static final String gameName = "Belote";
    public static final String gameRules = "";

    public Belote(String partyName) {
        super(partyName);
    }
    public Belote() {
        this("");
    }

    @Override
    public Player victoryPlayer(Lobby lobby) {
        return null;
    }

    @Override
    public List<String> setScoringList(Lobby lobby) {
        return null;
    }

    @Override
    public String getGameName() {
        return Belote.gameName;
    }

    @Override
    public String getGameRules() {
        return Belote.gameRules;
    }
}
